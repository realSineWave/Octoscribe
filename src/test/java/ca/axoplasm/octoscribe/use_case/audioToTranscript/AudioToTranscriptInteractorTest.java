package ca.axoplasm.octoscribe.use_case.audioToTranscript;

import ca.axoplasm.octoscribe.data_access.AudioToTranscriptFileSaveObject;
import ca.axoplasm.octoscribe.data_access.DataAccessObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;


public class AudioToTranscriptInteractorTest {

    @Test
    public void successTest() {

        AudioToTranscriptDataAccessInterface dataAccessObject = new DataAccessObject();
        AudioToTranscriptFileSaveInterface fileSaveObject = new AudioToTranscriptFileSaveObject();

        String path = "need to add a sample audio file for test/ replace this with path of it";
        File sampleAudioFile = new File(path);

        AudioToTranscriptInputData inputData  =
                new AudioToTranscriptInputData(sampleAudioFile, "whisper-small", "en");

        AudioToTranscriptInputBoundary interactor =
                new AudioToTranscriptInteractor(dataAccessObject, (AudioToTranscriptFileSaveObject) fileSaveObject);

        try {
            AudioToTranscriptOutputData output = interactor.execute(inputData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("subtitles.txt", fileSaveObject.getName());
    }
}
