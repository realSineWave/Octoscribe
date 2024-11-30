package ca.axoplasm.Octoscribe.use_case.createSubtitledVideo;

import ca.axoplasm.Octoscribe.data_access.MediaConvertObject;
import ca.axoplasm.Octoscribe.use_case.videoToAudio.*;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CreateSubtitledVideoInteractorTest {
    @Test
    public void successtest() {
        File video = new File("src/test/resources/TestVideo.mp4");
        File subtitle = new File("src/test/resources/TestVideo.srt");
        CreateSubtitledVideoInputData input = new CreateSubtitledVideoInputData(video, subtitle);
        MediaConvertObject mci = new MediaConvertObject();

        CreateSubtitledVideoOutputBoundary boundary = new CreateSubtitledVideoOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateSubtitledVideoOutputData data) {
                File sVideo = new File("src/test/resources/subtitledTestVideo.mp4");
                try {
                    assertEquals("src/test/resources/subtitledTestVideo.mp4", data.getFileName());
                    assertFalse(data.getUseCaseFailed());
                    sVideo.delete();
                } catch (Exception e) {
                    sVideo.delete();
                    assert false;
                }
            }

            @Override
            public void prepareFailureView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        CreateSubtitledVideoInputBoundary inputBoundary = new CreateSubtitledVideoInteractor(mci, boundary);
        inputBoundary.execute(input);
    }

    @Test
    public void failuretest() {
        File video = new File("src/test/resources/notreal.mp4");
        File subtitle = new File("src/test/resources/alsonotreal.srt");
        CreateSubtitledVideoInputData input = new CreateSubtitledVideoInputData(video, subtitle);
        MediaConvertObject mci = new MediaConvertObject();

        CreateSubtitledVideoOutputBoundary boundary = new CreateSubtitledVideoOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateSubtitledVideoOutputData data) {
                try {
                    fail("Use case success is unexpected.");
                } catch (Exception e) {
                    File audio = new File("src/test/resources/subtitledTestVideo.mp4");
                    audio.delete();
                }
            }

            @Override
            public void prepareFailureView(String errorMessage) {
                assertEquals("Video Conversion Failed", errorMessage);
            }
        };

        CreateSubtitledVideoInputBoundary inputBoundary = new CreateSubtitledVideoInteractor(mci, boundary);
        inputBoundary.execute(input);
    }

    @Test
    public void integrationTest() {
        File video = new File("src/test/resources/TestVideo.mp4");
        File subtitle = new File("src/test/resources/TestVideo.srt");
        CreateSubtitledVideoInputData input = new CreateSubtitledVideoInputData(video, subtitle);
        MediaConvertObject mci = new MediaConvertObject();

        CreateSubtitledVideoOutputBoundary boundary = new CreateSubtitledVideoOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateSubtitledVideoOutputData data) {
                File svideo = new File("src/test/resources/subtitledTestVideo.mp4");
                try {
                    assert svideo.exists();
                    svideo.delete();
                } catch (Exception e) {
                    svideo.delete();
                    assert false;
                }
            }

            @Override
            public void prepareFailureView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        CreateSubtitledVideoInputBoundary inputBoundary = new CreateSubtitledVideoInteractor(mci, boundary);
        inputBoundary.execute(input);
    }
}