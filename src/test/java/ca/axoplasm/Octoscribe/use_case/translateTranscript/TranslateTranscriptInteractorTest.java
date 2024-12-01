package ca.axoplasm.Octoscribe.use_case.translateTranscript;

import ca.axoplasm.Octoscribe.data_access.DataAccessObject;
import ca.axoplasm.Octoscribe.data_access.TranslateTranscriptFileSaveObject;
import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentFactory;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscriptionFactory;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TranslateTranscriptInteractorTest {

    @Test
    public void successTest() {

        TranslateTranscriptDataAccessInterface dao = new DataAccessObject();
        TranslateTranscriptFileSaveObject fileSaveObject = new TranslateTranscriptFileSaveObject();
        SegmentedTranscriptionFactory segmentedTranscriptionFactory = new SegmentedTranscriptionFactory();
        SegmentFactory segmentFactory = new SegmentFactory();
        Duration startDuration = Duration.ofNanos(0);
        Duration endDuration = Duration.ofNanos(1000000000);

        Segment segment = segmentFactory.createSegment(startDuration, endDuration, "안녕 세상");
        List<Segment> lis = new ArrayList<>(Arrays.asList(segment));
        SegmentedTranscription temp = segmentedTranscriptionFactory.createSegmented("ko",
                "안녕 세상", lis); // need to initiale; replace this with having sample list of segments

        TranslateTranscriptInputData inputData =
                new TranslateTranscriptInputData(temp , "en");

        TranslateTranscriptInputBoundary interactor = new TranslateTranscriptInteractor(dao, fileSaveObject);

        TranslateTranscriptOutputData output = interactor.execute(inputData);
        assertEquals("Translated_", fileSaveObject.getName().substring(0, "Translated_".indexOf("_")+1));
        assertEquals(1, output.getSegmentedTranscription().getSegments().toArray().length, "Wrong" +
                " Amout of Segments");
        assertEquals("Translated_", output.getFileName().substring(0, "Translated_".indexOf("_")+1)
                , "Wrong FileName!");
        assertEquals(true, output.getStatus(), "Wrong Status");
    }
}
