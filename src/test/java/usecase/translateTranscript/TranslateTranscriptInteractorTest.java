package usecase.translateTranscript;

import ca.axoplasm.Octoscribe.data_access.AudioToTranscriptFileSaveObject;
import ca.axoplasm.Octoscribe.data_access.DataAccessObject;
import ca.axoplasm.Octoscribe.data_access.TranslateTranscriptFileSaveObject;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TranslateTranscriptInteractorTest {

    @Test
    public void successTest() {

        TranslateTranscriptDataAccessInterface dataAccessObject = new DataAccessObject();
        TranslateTranscriptFileSaveObject fileSaveObject = new TranslateTranscriptFileSaveObject();

        SegmentedTranscription temp; // need to initiale; replace this with having sample list of segments

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
