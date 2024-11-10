package data_access;

import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import use_case.audioToTranscript.AudioToTranscriptDataAccessInterface;
import okhttp3.*;
import java.io.File;
import java.io.IOException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class AudioToTranscriptDataAccessObject implements AudioToTranscriptDataAccessInterface {
    private final OkHttpClient client = new OkHttpClient();
    private String API_KEY;
    private String language = null;
    private String apiUrl;

    public void AudioToTranscriptOutputData(String filename, File file){
        this.API_KEY = System.getenv("API_KEY");
        this.apiUrl = "https://api.openai.com/v1/audio/transcriptions";
    }


    @Override
    public JsonObject getTranscript(File audio) {
        RequestBody fileBody = RequestBody.create(audio, MediaType.parse("audio/wav"));
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", audio.getName(), fileBody)
                .addFormDataPart("model", "whisper-small")
                .build();

        // Build
        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", "Bearer " + this.API_KEY)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
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
    public Segment toSegment(File audio) {
        return null;
    }

    @Override
    public SegmentedTranscription toSegmentedTranscription(File audio) {
        return null;
    }
}
