package ca.axoplasm.Octoscribe.use_case.createSubtitledVideo;

import ca.axoplasm.Octoscribe.use_case.videoToAudio.VideoToAudioOutputData;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CreateSubtitledVideoOutputDataTest {

    @Test
    void testConstructorAndGetters_SuccessCase() {
        File expectedFile = new File("output.mp4");
        Boolean expectedUseCaseFailed = false;

        CreateSubtitledVideoOutputData outputData = new CreateSubtitledVideoOutputData(expectedFile, expectedUseCaseFailed);

        assertEquals(expectedFile, outputData.getFile());
        assertEquals(expectedUseCaseFailed, outputData.getUseCaseFailed());
    }
}