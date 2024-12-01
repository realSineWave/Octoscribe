package ca.axoplasm.Octoscribe.entitiesTest;

import ca.axoplasm.Octoscribe.entity.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransFactoryTest {


    @Test
    void testRiskyMethodCreateSegmentThrowsException() {
        TranscriptionFactory service = new TranscriptionFactory();
//        SegmentFactory segmentFactory = new SegmentFactory();
        Duration startDuration = Duration.ofNanos(0);
        Duration endDuration = Duration.ofNanos(1000000000);
        assertThrows(
                UnsupportedOperationException.class,
                () -> service.createSegment(startDuration, endDuration, "xiba")
        );
    }

    @Test
    void testRiskyMethodThrowsException() {
        TranscriptionFactory service = new TranscriptionFactory();
        SegmentFactory segmentFactory = new SegmentFactory();
        Duration startDuration = Duration.ofNanos(0);
        Duration endDuration = Duration.ofNanos(1000000000);
        Segment segment = segmentFactory.createSegment(startDuration, endDuration, "xiba");
        List<Segment> segmentList = new ArrayList<>(Collections.singletonList(segment));
        assertThrows(
                UnsupportedOperationException.class,
                () -> service.createSegmented("en", "xiba", segmentList)
        );
    }

    @Test
    void testTransThrowsNoException() {
        TranscriptionFactory service = new TranscriptionFactory();
        Transcription transcription = service.createTranscription("en", "goodbye");
        assertEquals("en", transcription.getLanguageCode());
        assertEquals("goodbye", transcription.getText());
        assertNotEquals(null, transcription.getLanguageLocale());
    }

}
