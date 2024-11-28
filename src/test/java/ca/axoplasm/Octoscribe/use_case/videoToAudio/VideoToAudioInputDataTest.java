package ca.axoplasm.Octoscribe.use_case.videoToAudio;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class VideoToAudioInputDataTest {

    @Test
    void testConstructorWithValidFile() {
        File testFile = new File("testVideo.mp4");
        VideoToAudioInputData inputData = new VideoToAudioInputData(testFile);

        assertNotNull(inputData);
        assertEquals(testFile, inputData.getVideoFile());
    }

    @Test
    void testGetVideoFile() {
        File testFile = new File("anotherVideo.mp4");
        VideoToAudioInputData inputData = new VideoToAudioInputData(testFile);

        assertNotNull(inputData.getVideoFile());
        assertEquals("anotherVideo.mp4", inputData.getVideoFile().getName());
    }

}