package data_access;

import entity.Segment;
import entity.SegmentFactory;
import entity.SegmentedTranscription;
import entity.SegmentedTranscriptionFactory;
import use_case.audioToTranscript.AudioToTranscriptDataAccessInterface;
import okhttp3.*;

import javax.json.*;
import java.util.ArrayList;
import java.util.List;

public class DataAccessObject implements AudioToTranscriptDataAccessInterface {
    private final OkHttpClient client = new OkHttpClient();
    private String whisperApiKey;
    private String language = null;
    private String apiUrl;
    private final SegmentFactory segmentFactory = new SegmentFactory();
    private final SegmentedTranscriptionFactory segmentedTranscriptionFactory = new SegmentedTranscriptionFactory();

    public void AudioToTranscriptOutputData(){
        this.whisperApiKey = System.getenv("API_KEY");
        this.apiUrl = "https://api.openai.com/v1/audio/transcriptions";
    }


    @Override
    public JsonObject getTranscriptJson() {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        return factory.createObjectBuilder()
                .add("firstName", "John")
                .add("lastName", "Smith")
                .add("text", "The beach was a popular spot on a hot summer day. People were swimming in the ocean, building sandcastles, and playing beach volleyball.")
                .add("segments", factory.createArrayBuilder()
                        .add(factory.createObjectBuilder()
                                .add("st" +
                                        "art", 0.0)
                                .add("end", 3.319999933242798)
                                .add("text", "The beach was a popular spot on a hot summer day."))
                        .add(factory.createObjectBuilder()
                                .add("start", 3.319999933242799)
                                .add("end", 8.470000267028809)
                                .add("text", "People were swimming in the ocean, building sandcastles, and playing beach volleyball.")))
                .build();
    }

    @Override
    public List<Segment> toSegments(JsonObject jsonObject) {
        List<Segment> result = new ArrayList<Segment>();
        JsonArray ray = jsonObject.getJsonArray("segments");
        for (JsonValue each : ray){
            JsonObject asobj = each.asJsonObject();
            float start = asobj.getJsonNumber("start").numberValue().floatValue();
            float end = asobj.getJsonNumber("end").numberValue().floatValue();
            String text = asobj.getString("text");
            Segment parts = this.segmentFactory.createSegment(start, end, text);
            result.add(parts);
        }
        return result;
    }


    @Override
    public SegmentedTranscription getSegmentedTranscription() {
        JsonObject jsonObject = getTranscriptJson();
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