package usecase.translateTranscript;

import ca.axoplasm.Octoscribe.data_access.AudioToTranscriptFileSaveObject;
import ca.axoplasm.Octoscribe.data_access.DataAccessObject;
import ca.axoplasm.Octoscribe.data_access.TranslateTranscriptFileSaveObject;
import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TranslateTranscriptInteractorTest {

    @Test
    public void successTest() {

        TranslateTranscriptDataAccessInterface dataAccessObject = new DataAccessObject();
        TranslateTranscriptFileSaveObject fileSaveObject = new TranslateTranscriptFileSaveObject();

        Segment segment_1 = new Segment(Duration.ofNanos(10), Duration.ofNanos(12), "Hello");
        Segment segment_2 = new Segment(Duration.ofNanos(10), Duration.ofNanos(12), "Bye");
        List<Segment> segmentList = new ArrayList<Segment>();
        segmentList.add(segment_1);
        segmentList.add(segment_2);

        SegmentedTranscription temp =
                new SegmentedTranscription("en", "test", segmentList);

        TranslateTranscriptInputData inputData =
                new TranslateTranscriptInputData(temp , "en");

        TranslateTranscriptOutputBoundary successPresenter = new TranslateTranscriptOutputBoundary() {
            @Override
            public void prepareSuccessView(TranslateTranscriptOutputData data) {
                assertEquals("Translated_", fileSaveObject.getName().substring(0, "Translated_".indexOf("_")));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("failure is not expected");
            }
        };

        TranslateTranscriptInputBoundary interactor =
                new TranslateTranscriptInteractor(dataAccessObject, fileSaveObject, successPresenter);

        interactor.execute(inputData);

    }
}
