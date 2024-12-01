package ca.axoplasm.Octoscribe.entitiesTest;

import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentFactory;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscriptionFactory;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SegmentedTranscriptionFactoryTest {
    @Test
    void correctAmountOfSegmentsTest(){
        SegmentedTranscriptionFactory transFactory = new SegmentedTranscriptionFactory();
        SegmentFactory segmentFactory = new SegmentFactory();
        Segment s1 = segmentFactory.createSegment(Duration.ofNanos(1_000_000_000),
                Duration.ofNanos(1_100_000_000), "Nice ");
        Segment s2 = segmentFactory.createSegment(Duration.ofNanos(1_100_000_000),
                Duration.ofNanos(1_200_000_000), "to ");
        Segment s3 = segmentFactory.createSegment(Duration.ofNanos(1_200_000_000),
                Duration.ofNanos(1_300_000_000), "meet ");
        Segment s4 = segmentFactory.createSegment(Duration.ofNanos(1_400_000_000),
                Duration.ofNanos(1_500_000_000), "you.");
        List<Segment> lis = new ArrayList<>(Arrays.asList(s1,s2,s3,s4));
        SegmentedTranscription segs = transFactory.createSegmented("en", "Nice to meet you.", lis);
        assertEquals(4, segs.getSegments().toArray().length, "Wrong amount of segments");
    }


}
