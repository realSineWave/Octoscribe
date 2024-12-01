package ca.axoplasm.Octoscribe.use_case.audioToTranscript;

import ca.axoplasm.Octoscribe.data_access.AudioToTranscriptFileSaveObject;
import ca.axoplasm.Octoscribe.data_access.DataAccessObject;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
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

        assertEquals(output.getSegmentedTranscription(),
                assertInstanceOf(SegmentedTranscription.class, output.getSegmentedTranscription()));

        File file = new File("src/test/resources/testAudio.srt");
        file.delete();

    }
}
