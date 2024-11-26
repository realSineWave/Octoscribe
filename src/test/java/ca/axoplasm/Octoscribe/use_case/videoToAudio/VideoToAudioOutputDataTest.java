package ca.axoplasm.Octoscribe.use_case.videoToAudio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VideoToAudioOutputDataTest {

    @Test
    void testConstructorAndGetters_SuccessCase() {
        // Arrange
        String expectedFilename = "output_audio.mp3";
        Boolean expectedUseCaseFailed = false;

        // Act
        VideoToAudioOutputData outputData = new VideoToAudioOutputData(expectedFilename, expectedUseCaseFailed);

        // Assert
        assertEquals(expectedFilename, outputData.getFilename(), "Filename should match the input value.");
        assertEquals(expectedUseCaseFailed, outputData.getUseCaseFailed(), "UseCaseFailed should be false.");
    }

    @Test
    void testConstructorAndGetters_FailureCase() {
        // Arrange
        String expectedFilename = "error_output.mp3";
        Boolean expectedUseCaseFailed = true;

        // Act
        VideoToAudioOutputData outputData = new VideoToAudioOutputData(expectedFilename, expectedUseCaseFailed);

        // Assert
        assertEquals(expectedFilename, outputData.getFilename());
        assertEquals(expectedUseCaseFailed, outputData.getUseCaseFailed());
    }
}