package ca.axoplasm.Octoscribe.data_access;

import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentFactory;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscriptionFactory;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptDataAccessInterface;
import okhttp3.*;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptDataAccessInterface;

import java.io.File;
import java.io.IOException;
import jakarta.json.*;
import java.io.StringReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DataAccessObject implements AudioToTranscriptDataAccessInterface, TranslateTranscriptDataAccessInterface {
    private final OkHttpClient client = new OkHttpClient();
    private String whisperAPIKey;
    private String whisperAPIEndpoint;
    private final SegmentFactory segmentFactory = new SegmentFactory();
    private final SegmentedTranscriptionFactory segmentedTranscriptionFactory = new SegmentedTranscriptionFactory();
    private String deeplAPIKey;
    private String deeplAPIEndpoint;

    /**
     * Constructor of the DAO, stores the Endpoint and API key for them.
     *
     */
    public DataAccessObject(String whisperAPIEndpoint, String whisperAPIKey, String deeplAPIKey) {
        this.deeplAPIEndpoint = "https://api-free.deepl.com/v2/translate";
        this.whisperAPIEndpoint = whisperAPIEndpoint;
        this.whisperAPIKey = whisperAPIKey;
        this.deeplAPIKey = deeplAPIKey;
    }

    /**
     * Send the file to Whisper API by okhttp3, and return the get the json object of the transcription.
     *
     * @param file can be any widely applied fire format. Recommendation: .mp3 (The one i did the test for)
     * @return the JsonObject, for later manipulation.
     */
    @Override
    public JsonObject getTranscribedJson(File file) throws IOException {
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(file, null))
                .addFormDataPart("timestamp_granularities[]", "segment")
                .addFormDataPart("model", "whisper-1")
                .addFormDataPart("response_format", "verbose_json")
                .build();

        Request request = new Request.Builder()
                .url(this.whisperAPIEndpoint)
                .addHeader("Authorization", "Bearer " + this.whisperAPIKey)
                .post(requestBody)
                .build();

        try (Response response = this.client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Request failed");
            }

            try (JsonReader jsonReader = Json.createReader(new StringReader(response.body().string()))) {
                return jsonReader.readObject();
            }
        } catch (IOException e) {
            throw new IOException("Request failed");
        }
    }

    /**
     * Converts a given JSON object into a list of {@link Segment} objects.
     *
     * <p>This method reads a JSON object containing a "segments" array, where each element is a JSON
     * object with "start", "end", and "text" properties. The method parses these properties to
     * construct {@link Segment} objects, which represent segments of time with associated text content.</p>
     *
     * <p>The "start" and "end" properties in the JSON are parsed as floating-point numbers representing
     * seconds. These are converted to {@link Duration} objects, with precision down to nanoseconds.</p>
     *
     * @param jsonObject the input JSON object containing the "segments" array
     * @return a {@link List} of {@link Segment} objects parsed from the input JSON object
     */

    @Override
    public List<Segment> toSegments(JsonObject jsonObject) {
        List<Segment> result = new ArrayList<>();
        JsonArray ray = jsonObject.getJsonArray("segments");
        for (JsonValue each : ray){
            JsonObject asobj = each.asJsonObject();
            float start = asobj.getJsonNumber("start").numberValue().floatValue();
            long startsecondsPart = (long) start;
            long startnanosPart = (long) ((start - startsecondsPart) * 1_000_000_000);
            Duration startTime = Duration.ofSeconds(startsecondsPart, startnanosPart);
            float end = asobj.getJsonNumber("end").numberValue().floatValue();
            long endsecondsPart = (long) end;
            long endnanosPart = (long) ((end - endsecondsPart) * 1_000_000_000);
            Duration endTime = Duration.ofSeconds(endsecondsPart, endnanosPart);
            String text = asobj.getString("text");
            Segment parts = this.segmentFactory.createSegment(startTime, endTime, text);
            result.add(parts);
        }
        return result;
    }

    /**
     * Make the file to the transcription.
     * With the help of the helper function this.toSegments(), and this.getTranscribedJson()
     *
     * @param file The file you take in for translation.
     * @return the segmentedTranscription.
     */
    @Override
    public SegmentedTranscription getSegmentedTranscription(File file) throws IOException {
        JsonObject jsonObject = getTranscribedJson(file);
        List<Segment> lists = toSegments(jsonObject);
        String text = jsonObject.getString("text");
        String detectedLanguage;
        if (!jsonObject.containsKey("language")) {
            detectedLanguage = "Unknown";
        } else {
            detectedLanguage = jsonObject.getString("language");
        }
        return this.segmentedTranscriptionFactory.createSegmented(detectedLanguage, text, lists);
    }

    /**
     * Takes in text (Expected from the text of the segments.), and desired language. And finally output the JsonObject
     * through sending the segments to DeepL.
     * ISO file for language code(2 letters): <a href="https://www.iso.org/standard/74575.html">...</a>
     * It's the helper function of TransSegment()
     *
     * @param text Takes in the string for transformation.
     * @param language the desired language, makes sure that it follows ISO 639. (2023)
     * @return JsonObject of the translated results. Null if there is something wrong.
     */
    @Override
    public JsonObject getTranslateJson(String text, String language) throws IOException {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        jsonArrayBuilder.add(text);
        jsonObjectBuilder.add("text", jsonArrayBuilder);
        jsonObjectBuilder.add("target_lang", language);
        JsonObject jsonPayload = jsonObjectBuilder.build();

        RequestBody requestBody = RequestBody.create(
                jsonPayload.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(this.deeplAPIEndpoint)
                .addHeader("Authorization", "DeepL-Auth-Key " + this.deeplAPIKey)
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build();


        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Request failed");
            }

            String responseBody = response.body().string();
            JsonReader jsonReader = Json.createReader(new StringReader(responseBody));
            return jsonReader.readObject();

        } catch (IOException e) {
            throw new IOException("Request failed");
        }
    }

    /**
     * It's the helper function of TransSegmentList().
     * Translate the Segments to the translated segments.
     * ISO file for language code(2 letters): <a href="https://www.iso.org/standard/74575.html">...</a>
     * @param segment the segment takes in for translation.
     * @param language the desired language.
     * @return a translated segment.
     */
    @Override
    public Segment TransSegment(Segment segment, String language) throws IOException {
        JsonObject jsonObject = getTranslateJson(segment.getText(), language);
        StringBuilder lastString = new StringBuilder();
        for (JsonValue each : jsonObject.getJsonArray("translations")){
            JsonObject jsonText = each.asJsonObject();
            String textTranslated = jsonText.getString("text");
            lastString.append(textTranslated);
        }
        return this.segmentFactory.createSegment(segment.getStartTime(),segment.getEndTime(), lastString.toString());
    }

    /**
     * It's the helper function of TransSegmentedTranscription()
     * Translate the list of segments. Uses helper function TransSegment()
     * ISO file for language code(2 letters): <a href="https://www.iso.org/standard/74575.html">...</a>
     * @param segments the list of segment takes in for translation.
     * @param language the desired language for translation.
     * @return the translated list of segments.
     */
    @Override
    public List<Segment> TransSegmentList(List<Segment> segments, String language) throws IOException {
        List<Segment> segments1 = new ArrayList<>();
        for(Segment seg : segments) {
            segments1.add(this.TransSegment(seg, language));
        }
        return segments1;
    }

    /**
     * This one is to get the translated SegmentedTranscription.
     * ISO file for language code(2 letters): <a href="https://www.iso.org/standard/74575.html">...</a>
     * @param transcription Segmented transcription.
     * @param language the desired language.
     * @return The segmented transcription.
     */
    @Override
    public SegmentedTranscription TransSegmentedTranscription(SegmentedTranscription transcription, String language) throws IOException {
        List<Segment> segments = TransSegmentList(transcription.getSegments(), language);
        StringBuilder allText = new StringBuilder();
        for (Segment segment: segments) {
            allText.append(segment.getText());
        }
        return this.segmentedTranscriptionFactory.createSegmented(language, allText.toString(), segments);
    }
}










