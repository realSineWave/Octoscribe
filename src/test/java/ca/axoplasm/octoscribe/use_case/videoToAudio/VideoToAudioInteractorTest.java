package ca.axoplasm.octoscribe.use_case.videoToAudio;

import ca.axoplasm.octoscribe.data_access.MediaConvertObject;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class VideoToAudioInteractorTest {

    @Test
    public void successtest() {
        File video = new File("src/test/resources/TestVideo.mp4");
        VideoToAudioInputData input = new VideoToAudioInputData(video);
        MediaConvertObject mci = new MediaConvertObject();

        VideoToAudioInputBoundary inputBoundary = new VideoToAudioInteractor(mci);
        VideoToAudioOutputData output = inputBoundary.execute(input);

        File audio = new File("src/test/resources/TestVideo.mp3");
        try {
            assertEquals(audio.getAbsolutePath(), output.getFile().getAbsolutePath());
            assertFalse(output.getUseCaseFailed());
            audio.delete();
        } catch (Exception e) {
            audio.delete();
            assert false;
        }
    }

    @Test
    public void failuretest() {
        File video = new File("src/test/resources/notreal.mp4");
        VideoToAudioInputData input = new VideoToAudioInputData(video);
        MediaConvertObject mci = new MediaConvertObject();

        VideoToAudioInputBoundary inputBoundary = new VideoToAudioInteractor(mci);
        try {
            inputBoundary.execute(input);
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    public void integrationTest() {
        File video = new File("src/test/resources/TestVideo.mp4");
        VideoToAudioInputData input = new VideoToAudioInputData(video);
        MediaConvertObject mci = new MediaConvertObject();

        VideoToAudioInputBoundary inputBoundary = new VideoToAudioInteractor(mci);
        VideoToAudioOutputData output = inputBoundary.execute(input);

        File audio = new File("src/test/resources/TestVideo.mp3");
        try {
            assert audio.exists();
            audio.delete();
        } catch (Exception e) {
            audio.delete();
            assert false;
        }
    }
}