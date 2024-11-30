package ca.axoplasm.Octoscribe.use_case.videoToAudio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VideoToAudioOutputDataTest {

    @Test
    void testConstructorAndGetters_SuccessCase() {
        String expectedFilename = "output_audio.mp3";
        Boolean expectedUseCaseFailed = false;

        VideoToAudioOutputData outputData = new VideoToAudioOutputData(expectedFilename, expectedUseCaseFailed);

        assertEquals(expectedFilename, outputData.getFileName(), "Filename should match the input value.");
        assertEquals(expectedUseCaseFailed, outputData.getUseCaseFailed(), "UseCaseFailed should be false.");
    }
}