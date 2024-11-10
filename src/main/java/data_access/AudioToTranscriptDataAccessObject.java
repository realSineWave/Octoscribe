package data_access;

import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentFactory;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscriptionFactory;
import use_case.audioToTranscript.AudioToTranscriptDataAccessInterface;
import okhttp3.*;
import java.io.File;
import java.io.IOException;
import javax.json.*;
import java.io.StringReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AudioToTranscriptDataAccessObject implements AudioToTranscriptDataAccessInterface {
    private final OkHttpClient client = new OkHttpClient();
    private String apiKey;
    private String language = null;
    private String apiUrl;
    private final SegmentFactory segmentFactory = new SegmentFactory();
    private final SegmentedTranscriptionFactory segmentedTranscriptionFactory = new SegmentedTranscriptionFactory();

    public void AudioToTranscriptOutputData(){
        this.apiKey = System.getenv("API_KEY");
        this.apiUrl = "https://api.openai.com/v1/audio/transcriptions";
    }


    @Override
    public JsonObject getSegmentedTranscript(File audio) {
        RequestBody fileBody = RequestBody.create(audio, MediaType.parse("audio/wav"));
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", audio.getName(), fileBody)
                .addFormDataPart("model", "whisper-small")
                .addFormDataPart("response_format", "verbose_json")
                .build();

        // Build
        Request request = new Request.Builder()
                .url(this.apiUrl)
                .addHeader("Authorization", "Bearer " + this.apiKey)
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
        List<Segment> result = new ArrayList<Segment>();
        JsonArray ray = jsonObject.getJsonArray("segments");
        for (JsonValue each : ray){
            JsonObject asobj = each.asJsonObject();
            long start = asobj.getJsonNumber("start").longValue();
            Duration startTime = Duration.ofSeconds(start);
            long end = asobj.getJsonNumber("end").longValue();
            Duration endTime = Duration.ofSeconds(end);
            String text = asobj.getString("text");
            Segment parts = this.segmentFactory.createSegment(startTime, endTime, text);
            result.add(parts);
        }
        return result;
    }


    @Override
    public SegmentedTranscription toSegmentedTranscription(File file) {
        JsonObject jsonObject = getSegmentedTranscript(file);
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
}
