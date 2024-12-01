package ca.axoplasm.Octoscribe.use_case.audioToTranscript;

import ca.axoplasm.Octoscribe.data_access.AudioToTranscriptFileSaveObject;
import ca.axoplasm.Octoscribe.data_access.DataAccessObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;


public class AudioToTranscriptInteractorTest {

    @Test
    public void successTest() {

        AudioToTranscriptDataAccessInterface dataAccessObject = new DataAccessObject();
        AudioToTranscriptFileSaveInterface fileSaveObject = new AudioToTranscriptFileSaveObject();

        String path = "src/test/resources/testAudio.mp3";
        File sampleAudioFile = new File(path);

        AudioToTranscriptInputData inputData  =
                new AudioToTranscriptInputData(sampleAudioFile, "whisper-small", "en");

        assertEquals("whisper-small", inputData.getModel(), "Wrong Model");
        assertEquals("en", inputData.getLanguage(), "Wrong Language");
        AudioToTranscriptInputBoundary interactor =
                new AudioToTranscriptInteractor(dataAccessObject, (AudioToTranscriptFileSaveObject) fileSaveObject);

        AudioToTranscriptOutputData output = interactor.execute(inputData);
        assertNotEquals(false, output.getStatus());
        assertNotEquals(null, output.getFileName());
        assertNotEquals(0, output.getSegmentedTranscription().getSegments().toArray().length);
        assertEquals("testAudio.srt", fileSaveObject.getName());
    }
}
