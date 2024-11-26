package ca.axoplasm.Octoscribe.use_case.videoToAudio;

import ca.axoplasm.Octoscribe.data_access.MediaConvertObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class VideoToAudioInteractorTest {

    @BeforeEach
    void setUp() {
        VideoToAudioMediaConvertInterface mci = new VideoToAudioMediaConvertInterface() {
            @Override
            public String audioToVideo(File file) {
                return "";
            }

            @Override
            public String getFileName() {
                return "";
            }
        };
    }

    @Test
    void testConstructor() {

    }
}