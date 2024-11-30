package ca.axoplasm.Octoscribe.use_case.createSubtitledVideo;

import ca.axoplasm.Octoscribe.use_case.videoToAudio.VideoToAudioInputData;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CreateSubtitledVideoInputDataTest {

    @Test
    void testConstructorWithValidFile() {
        File video = new File("testVideo.mp4");
        File subtitle = new File("testVideo.srt");
        CreateSubtitledVideoInputData inputData = new CreateSubtitledVideoInputData(video, subtitle);

        assertNotNull(inputData);
        assertEquals(video, inputData.getVideoFile());
        assertEquals(subtitle, inputData.getSubtitleFile());
    }

}