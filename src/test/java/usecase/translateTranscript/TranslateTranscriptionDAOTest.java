package usecase.translateTranscript;

import ca.axoplasm.Octoscribe.data_access.DataAccessObject;
import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentFactory;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscriptionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class TranslateTranscriptionDAOTest {
    @Test
    void toCorrectLanguageTest(){
        SegmentFactory factory = new SegmentFactory();
        DataAccessObject dao = new DataAccessObject();
        Duration startDuration = Duration.ofNanos(0);
        Duration endDuration = Duration.ofNanos(1000000000);
        Segment segment = factory.createSegment(startDuration, endDuration, "안녕 세상");
        Segment result = dao.TransSegment(segment, "en");
        assertEquals(segment.getStartTime(), result.getStartTime(),
                "Different Start time in initial segments and results.");
        assertEquals(segment.getEndTime(), result.getEndTime(),
                "Different End time in initial segments and results.");
    }

    @Test
    void toSegmentedPartsLanguageTest(){
        SegmentFactory factory = new SegmentFactory();
        DataAccessObject dao = new DataAccessObject();
        Duration startDuration = Duration.ofNanos(0);
        Duration endDuration = Duration.ofNanos(1000000000);
        Duration secondStartDuration = Duration.ofNanos(1000000001);
        Duration secondEndDuration = Duration.ofNanos(2000000000);
        Segment segment1 = factory.createSegment(startDuration, endDuration, "안녕 세상");
        Segment segment2 = factory.createSegment(secondStartDuration, secondEndDuration, "안녕");
        List<Segment> lis = new ArrayList<>(Arrays.asList(segment1, segment2));
        List<Segment> reslis = dao.TransSegmentList(lis, "en");
        assertEquals(reslis.getFirst().getStartTime(), lis.getFirst().getStartTime(),
                "Error in start time of method " +
                "TransSegmentList in DAo");
        assertEquals(reslis.get(0).getEndTime(), lis.get(0).getEndTime(), "Error in start time of method " +
                "TransSegmentList in DAo");
        assertEquals(reslis.get(1).getStartTime(), lis.get(1).getStartTime(), "Error in start time of method "+
                "TransSegmentList in DAo");
        assertEquals(reslis.get(1).getEndTime(), lis.get(1).getEndTime(), "Error in start time of method " +
                "TransSegmentList in DAo");
    }

    @Test
    void SegmentedTranscriptionTest(){
        SegmentFactory factory = new SegmentFactory();
        SegmentedTranscriptionFactory segmentedTranscriptionFactory = new SegmentedTranscriptionFactory();
        DataAccessObject dao = new DataAccessObject();
        Duration startDuration = Duration.ofNanos(0);
        Duration endDuration = Duration.ofNanos(1000000000);
        Duration secondStartDuration = Duration.ofNanos(1000000001);
        Duration secondEndDuration = Duration.ofNanos(2000000000);
        Segment segment1 = factory.createSegment(startDuration, endDuration, "안녕 세상.");
        Segment segment2 = factory.createSegment(secondStartDuration, secondEndDuration, "안녕.");
        List<Segment> lis = new ArrayList<>(Arrays.asList(segment1, segment2));
        SegmentedTranscription segs = segmentedTranscriptionFactory.createSegmented("ko",
                "안녕 세상.안녕.", lis);
//        SegmentedTranscription preres =
//        segmentedTranscriptionFactory.createSegmented("ko", "hello world.hello", )
        SegmentedTranscription res = dao.TransSegmentedTranscription(segs, "en");
        assertEquals(res.getSegments().getFirst().getStartTime(), segs.getSegments().getFirst().getStartTime(),
                "Error in start time of method " +
                "TransSegmentList in DAO");
        assertEquals(res.getSegments().get(0).getEndTime(), segs.getSegments().get(0).getEndTime(),
                "Error in start time of method " +
                "TransSegmentList in DAO");
        assertEquals(res.getSegments().get(1).getStartTime(), segs.getSegments().get(1).getStartTime(),
                "Error in start time of method " +
                "TransSegmentList in DAO");
        assertEquals(res.getSegments().get(1).getEndTime(), segs.getSegments().get(1).getEndTime(),
                "Error in start time of method " +
                "TransSegmentList in DAO");
    }
}
