package ca.axoplasm.Octoscribe.use_case.videoToAudio;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class VideoToAudioOutputDataTest {

    @Test
    void testConstructorAndGetters_SuccessCase() {
        File expectedFile = new File("output.mp3");
        Boolean expectedUseCaseFailed = false;

        VideoToAudioOutputData outputData = new VideoToAudioOutputData(expectedFile, expectedUseCaseFailed);

        assertEquals(expectedFile, outputData.getFile(), "File should match the input value.");
        assertEquals(expectedUseCaseFailed, outputData.getUseCaseFailed(), "UseCaseFailed should be false.");
    }
}