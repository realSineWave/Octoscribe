package usecase.audioToTranscript;

import ca.axoplasm.Octoscribe.data_access.DataAccessObject;
import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import jakarta.json.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AudioToTranscriptDAOTest {
    @Test
    void getTranscriptedJson(){
        DataAccessObject dao = new DataAccessObject();
        File file = new File("src/test/java/usecase/audioToTranscript/testAudio.mp3");
        SegmentedTranscription segmentedTranscription = dao.getSegmentedTranscription(file);
        assertEquals(segmentedTranscription.getText(), "Good morning. Nice to meet you.");
        assertEquals(segmentedTranscription.getSegments().getFirst().getStartTime(), Duration.ofNanos(0));
    }

    @Test
    void testToSegmentTranscribe(){
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder(); // Store things in the segments part of object
        JsonObjectBuilder eachone = Json.createObjectBuilder(); // Things inside the Jsonarray
        eachone.add("start", 0.0);
        eachone.add("end", 3.0);
        eachone.add("text", "Hello World");
        jsonArrayBuilder.add(eachone);
        jsonObjectBuilder.add("segments", jsonArrayBuilder);
        JsonObject hahaha = jsonObjectBuilder.build();
        DataAccessObject dao = new DataAccessObject();
        List<Segment> lis = dao.toSegments(hahaha);
        assertEquals(lis.get(0).getEndTime(), Duration.ofSeconds(3), "Wrong End time");
    }
}
