package ca.axoplasm.Octoscribe.use_case.createSubtitledVideo;

import ca.axoplasm.Octoscribe.use_case.videoToAudio.VideoToAudioOutputData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateSubtitledVideoOutputDataTest {

    @Test
    void testConstructorAndGetters_SuccessCase() {
        String expectedFilename = "output.mp4";
        Boolean expectedUseCaseFailed = false;

        CreateSubtitledVideoOutputData outputData = new CreateSubtitledVideoOutputData(expectedFilename, expectedUseCaseFailed);

        assertEquals(expectedFilename, outputData.getFileName());
        assertEquals(expectedUseCaseFailed, outputData.getUseCaseFailed());
    }
}