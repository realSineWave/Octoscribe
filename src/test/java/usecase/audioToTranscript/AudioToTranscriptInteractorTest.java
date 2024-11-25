package usecase.audioToTranscript;

import ca.axoplasm.Octoscribe.data_access.AudioToTranscriptFileSaveObject;
import ca.axoplasm.Octoscribe.data_access.DataAccessObject;
import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentFactory;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;


public class AudioToTranscriptInteractorTest {

    @Test
    public void successTest() {

        AudioToTranscriptDataAccessInterface dataAccessObject = new DataAccessObject();
        AudioToTranscriptFileSaveInterface fileSaveObject = new AudioToTranscriptFileSaveObject();

        String path = "need to add a sample audio file for test/ replace this with path of it";
        File sampleAudioFile = new File(path);

        AudioToTranscriptInputData inputData  =
                new AudioToTranscriptInputData(sampleAudioFile, "whisper-small", "en");

        AudioToTranscriptOutputBoundary successPresenter = new AudioToTranscriptOutputBoundary() {
            @Override
            public void prepareSuccessView(AudioToTranscriptOutputData data) {
                assertEquals("subtitles.txt", fileSaveObject.getName());
                // add more cases when we have the sample audio.
            }
            @Override
            public void prepareFailView(String errorMessage) {
                fail("failure is not expected");
            }

        };

        AudioToTranscriptInputBoundary interactor =
                new AudioToTranscriptInteractor
                        (dataAccessObject, (AudioToTranscriptFileSaveObject) fileSaveObject, successPresenter);

        interactor.execute(inputData);
    }
}
