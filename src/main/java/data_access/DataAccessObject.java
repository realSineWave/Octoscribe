package data_access;

import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentFactory;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscriptionFactory;
import use_case.audioToTranscript.AudioToTranscriptDataAccessInterface;
import okhttp3.*;
import use_case.translateTranscript.TranslateTranscriptDataAccessInterface;

import java.io.File;
import java.io.IOException;
import javax.json.*;
import java.io.StringReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DataAccessObject implements AudioToTranscriptDataAccessInterface, TranslateTranscriptDataAccessInterface {
    private final OkHttpClient client = new OkHttpClient();
    private String OpenAiapiKey;
    private String language = null;
    private String whisperApiUrl;
    private final SegmentFactory segmentFactory = new SegmentFactory();
    private final SegmentedTranscriptionFactory segmentedTranscriptionFactory = new SegmentedTranscriptionFactory();
    private String DeepLApiKey;
    private String DeepLUrl;

    public void AudioToTranscriptOutputData(){
        this.OpenAiapiKey = "sk-proj-uGI-ofIHwn18Y3PSlfZHDfs3wIfdzqmWWN2VJaTzl15gtBsDzTTtzb-uWRJz34f55i3yVA80SdT3BlbkFJ" +
                "PP4fS9xLckhEMcmPrcKEfF9Yti_l0AUqYhxJJwutUvmqAXnl_WBdS20G1_nm1qjpaYuNs8cAQA";
        this.whisperApiUrl = "https://api.openai.com/v1/audio/transcriptions";
        this.DeepLUrl = "https://api-free.deepl.com/v2/translate";
        this.DeepLApiKey = "119441ee-8da3-4d15-9373-f117f5eca6fa:fx";
    }


    @Override
    public JsonObject getTranscriptedJson(File audio) {
        RequestBody fileBody = RequestBody.create(audio, MediaType.parse("audio/wav"));
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", audio.getName(), fileBody)
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


    @Override
    public SegmentedTranscription getSegmentedTranscription(File file) {
        JsonObject jsonObject = getTranscriptedJson(file);
        List<Segment> lists = toSegments(jsonObject);
        String text = jsonObject.getString("text");
        String language = null;
        if (!jsonObject.containsKey("language")) {
            language = "Unknown";
        } else {
            language = jsonObject.getString("language");
        }
        return this.segmentedTranscriptionFactory.createSegmented(language, text, lists);
    }

    @Override
    public JsonObject getTranslateJson(String text, String language) {
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
    public Segment TransSegment(Segment segment, String targetLanguage) {
        JsonObject jsonObject = getTranslateJson(segment.getText(), targetLanguage);
        StringBuilder lastString = new StringBuilder();
        for (JsonValue each : jsonObject.getJsonArray("translations")){
            JsonObject jsonText = each.asJsonObject();
            String textTranslated = jsonText.getString("text");
            lastString.append(textTranslated);
        }
        return this.segmentFactory.createSegment(segment.getStartTime(),segment.getEndTime(), lastString.toString());
    }

    @Override
    public List<Segment> TransSegmentList(List<Segment> segments, String language) {
        List<Segment> segments1 = new ArrayList<Segment>();
        for(Segment seg : segments) {
            segments1.add(TransSegment(seg, language));
        }
        return segments1;
    }

    @Override
    public SegmentedTranscription TransSegmentedTranscription(SegmentedTranscription transcription, String language) {
        List<Segment> segments = TransSegmentList(transcription.getSegments(), language);
        StringBuilder allText = new StringBuilder();
        for (Segment segment: segments) {
            allText.append(segment.getText());
        }
        return this.segmentedTranscriptionFactory.createSegmented(language, allText.toString(), segments);
    }

}










