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
    private String OpenAiapiKey;
    private String language = null;
    private String whisperApiUrl;
    private File file = null;
    private final SegmentFactory segmentFactory = new SegmentFactory();
    private final SegmentedTranscriptionFactory segmentedTranscriptionFactory = new SegmentedTranscriptionFactory();
    private String DeepLApiKey;
    private String DeepLUrl;

    /**
     * Constructor of the DAO, using overloading. If you gonna use the DAO for translation, add on the parameter called
     * target languge.
     *
     * @param targetLanguage the language you gonna translate to.
     */
    public DataAccessObject(String targetLanguage){
        this.OpenAiapiKey = "sk-proj-uGI-ofIHwn18Y3PSlfZHDfs3wIfdzqmWWN2VJaTzl15gtBsDzTTtzb-uWRJz34f55i3yVA80SdT3BlbkFJ" +
                "PP4fS9xLckhEMcmPrcKEfF9Yti_l0AUqYhxJJwutUvmqAXnl_WBdS20G1_nm1qjpaYuNs8cAQA";
        this.whisperApiUrl = "https://api.openai.com/v1/audio/transcriptions";
        this.DeepLUrl = "https://api-free.deepl.com/v2/translate";
        this.DeepLApiKey = "119441ee-8da3-4d15-9373-f117f5eca6fa:fx";
        this.language = targetLanguage;
    }

    /**
     * Constructor of the DAO, using overloading. This one if for transcription use age.
     * @param file the audio file.
     */
    public DataAccessObject(File file){
        this.OpenAiapiKey = "sk-proj-uGI-ofIHwn18Y3PSlfZHDfs3wIfdzqmWWN2VJaTzl15gtBsDzTTtzb-uWRJz34f55i3yVA80SdT3BlbkFJ" +
                "PP4fS9xLckhEMcmPrcKEfF9Yti_l0AUqYhxJJwutUvmqAXnl_WBdS20G1_nm1qjpaYuNs8cAQA";
        this.whisperApiUrl = "https://api.openai.com/v1/audio/transcriptions";
        this.DeepLUrl = "https://api-free.deepl.com/v2/translate";
        this.DeepLApiKey = "119441ee-8da3-4d15-9373-f117f5eca6fa:fx";
        this.file = file;
    }

    /**
     * Constructor of the DAO, using overloading. This one if audio directly to translated results.
     * @param file the audio file.
     */
    public DataAccessObject(File file, String language){
        this.OpenAiapiKey = "sk-proj-uGI-ofIHwn18Y3PSlfZHDfs3wIfdzqmWWN2VJaTzl15gtBsDzTTtzb-uWRJz34f55i3yVA80SdT3BlbkFJ" +
                "PP4fS9xLckhEMcmPrcKEfF9Yti_l0AUqYhxJJwutUvmqAXnl_WBdS20G1_nm1qjpaYuNs8cAQA";
        this.whisperApiUrl = "https://api.openai.com/v1/audio/transcriptions";
        this.DeepLUrl = "https://api-free.deepl.com/v2/translate";
        this.DeepLApiKey = "119441ee-8da3-4d15-9373-f117f5eca6fa:fx";
        this.file = file;
    }


    @Override
    public JsonObject getTranscriptedJson() {
        RequestBody fileBody = RequestBody.create(this.file, MediaType.parse("audio/wav"));
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", this.file.getName(), fileBody)
                .addFormDataPart("model", "whisper-small")
                .addFormDataPart("response_format", "verbose_json")
                .build();

        // Build
        Request request = new Request.Builder()
                .url(this.whisperApiUrl)
                .addHeader("Authorization", "Bearer " + this.OpenAiapiKey)
                .post(requestBody)
                .build();

        try (Response response = this.client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Request failed: " + response.message());
                return null;
            }

            try (JsonReader jsonReader = Json.createReader(new StringReader(response.body().string()))) {
                return jsonReader.readObject();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

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
     * This one is to get the translated SegmentedTranscription.
     *
     * @return The segmented transcription.
     */
    @Override
    public SegmentedTranscription getSegmentedTranscription() {
        JsonObject jsonObject = getTranscriptedJson();
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

    @Override
    public JsonObject getTranslateJson(String text) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        jsonArrayBuilder.add(text);
        jsonObjectBuilder.add("text", jsonArrayBuilder);
        jsonObjectBuilder.add("target_lang", this.language);
        JsonObject jsonPayload = jsonObjectBuilder.build();

        RequestBody requestBody = RequestBody.create(
                jsonPayload.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(this.DeepLUrl)
                .addHeader("Authorization", "DeepL-Auth-Key " + this.DeepLApiKey)
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build();


        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Request failed: " + response.message());
                return null;
            }

            String responseBody = response.body().string();
            JsonReader jsonReader = Json.createReader(new StringReader(responseBody));
            return jsonReader.readObject();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Segment TransSegment(Segment segment) {
        JsonObject jsonObject = getTranslateJson(segment.getText());
        StringBuilder lastString = new StringBuilder();
        for (JsonValue each : jsonObject.getJsonArray("translations")){
            JsonObject jsonText = each.asJsonObject();
            String textTranslated = jsonText.getString("text");
            lastString.append(textTranslated);
        }
        return this.segmentFactory.createSegment(segment.getStartTime(),segment.getEndTime(), lastString.toString());
    }

    @Override
    public List<Segment> TransSegmentList(List<Segment> segments) {
        List<Segment> segments1 = new ArrayList<Segment>();
        for(Segment seg : segments) {
            segments1.add(TransSegment(seg));
        }
        return segments1;
    }

    /**
     * This one is to get the translated SegmentedTranscription.
     *
     * @param transcription Segmented transcription.
     * @return The segmented transcription.
     */
    @Override
    public SegmentedTranscription TransSegmentedTranscription(SegmentedTranscription transcription) {
        List<Segment> segments = TransSegmentList(transcription.getSegments());
        StringBuilder allText = new StringBuilder();
        for (Segment segment: segments) {
            allText.append(segment.getText());
        }
        return this.segmentedTranscriptionFactory.createSegmented(this.language, allText.toString(), segments);
    }


    /**
     * It is the ultimate all in one function, get the translated SegTrans from audio, requires to use the third
     * constructor.
     * @return the translated segmentedtranciption.
     */
    public SegmentedTranscription AudioDirectlyToTranslatedTranscription() {
        SegmentedTranscription transcription = getSegmentedTranscription();
        return TransSegmentedTranscription(transcription);
    }

}










