package ca.axoplasm.Octoscribe.use_case.videoToAudio;

import ca.axoplasm.Octoscribe.data_access.MediaConvertObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class VideoToAudioInteractorTest {

    @Test
    public void successtest() {
        File video = new File("src/test/resources/TestVideo.mp4");
        VideoToAudioInputData input = new VideoToAudioInputData(video);
        MediaConvertObject mci = new MediaConvertObject();

        VideoToAudioOutputBoundary boundary = new VideoToAudioOutputBoundary() {
            @Override
            public void prepareSuccessView(VideoToAudioOutputData data) {
                File audio = new File("src/test/resources/TestVideo.mp3");
                try {
                    assertEquals("src/test/resources/TestVideo.mp3", data.getFileName());
                    assertFalse(data.getUseCaseFailed());
                    audio.delete();
                } catch (Exception e) {
                    audio.delete();
                    assert false;
                }
            }

            @Override
            public void prepareFailureView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        VideoToAudioInputBoundary inputBoundary = new VideoToAudioInteractor(mci, boundary);
        inputBoundary.execute(input);
    }

    @Test
    public void failuretest() {
        File video = new File("src/test/resources/notreal.mp4");
        VideoToAudioInputData input = new VideoToAudioInputData(video);
        MediaConvertObject mci = new MediaConvertObject();

        VideoToAudioOutputBoundary boundary = new VideoToAudioOutputBoundary() {
            @Override
            public void prepareSuccessView(VideoToAudioOutputData data) {
                try {
                    fail("Use case success is unexpected.");
                } catch (Exception e) {
                    File audio = new File("src/test/resources/TestVideo.mp3");
                    audio.delete();
                }
            }

            @Override
            public void prepareFailureView(String errorMessage) {
                assertEquals("Video Conversion Failed", errorMessage);
            }
        };

        VideoToAudioInputBoundary inputBoundary = new VideoToAudioInteractor(mci, boundary);
        inputBoundary.execute(input);
    }

    @Test
    public void integrationTest() {
        File video = new File("src/test/resources/TestVideo.mp4");
        VideoToAudioInputData input = new VideoToAudioInputData(video);
        MediaConvertObject mci = new MediaConvertObject();

        VideoToAudioOutputBoundary boundary = new VideoToAudioOutputBoundary() {
            @Override
            public void prepareSuccessView(VideoToAudioOutputData data) {
                File audio = new File("src/test/resources/TestVideo.mp3");
                try {
                    assert audio.exists();
                    audio.delete();
                } catch (Exception e) {
                    audio.delete();
                    assert false;
                }
            }

            @Override
            public void prepareFailureView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        VideoToAudioInputBoundary inputBoundary = new VideoToAudioInteractor(mci, boundary);
        inputBoundary.execute(input);
    }
}