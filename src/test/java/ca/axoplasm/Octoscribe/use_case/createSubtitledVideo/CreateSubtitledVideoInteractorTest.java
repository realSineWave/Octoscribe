package ca.axoplasm.Octoscribe.use_case.createSubtitledVideo;

import ca.axoplasm.Octoscribe.data_access.MediaConvertObject;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CreateSubtitledVideoInteractorTest {
    @Test
    public void successTest() {
        File video = new File("src/test/resources/TestVideo.mp4");
        File subtitle = new File("src/test/resources/TestVideo.srt");
        CreateSubtitledVideoInputData input = new CreateSubtitledVideoInputData(video, subtitle);
        MediaConvertObject mci = new MediaConvertObject();

        CreateSubtitledVideoInputBoundary inputBoundary = new CreateSubtitledVideoInteractor(mci);
        CreateSubtitledVideoOutputData output = inputBoundary.execute(input);
        assertFalse(output.getUseCaseFailed());

        if (!output.getUseCaseFailed()) {
            File sVideo = new File("src/test/resources/subtitledTestVideo.mp4");
            try {
                assertEquals(sVideo.getAbsolutePath(), output.getFile().getAbsolutePath());
                sVideo.delete();
            } catch (Exception e) {
                sVideo.delete();
                assert false;
            }
        }
    }

    @Test
    public void failureTest() {
        File video = new File("src/test/resources/notreal.mp4");
        File subtitle = new File("src/test/resources/alsonotreal.srt");
        CreateSubtitledVideoInputData input = new CreateSubtitledVideoInputData(video, subtitle);
        MediaConvertObject mci = new MediaConvertObject();

        CreateSubtitledVideoInputBoundary inputBoundary = new CreateSubtitledVideoInteractor(mci);
        try {
            inputBoundary.execute(input);
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    public void integrationTest() {
        File video = new File("src/test/resources/TestVideo.mp4");
        File subtitle = new File("src/test/resources/TestVideo.srt");
        CreateSubtitledVideoInputData input = new CreateSubtitledVideoInputData(video, subtitle);
        MediaConvertObject mci = new MediaConvertObject();

        CreateSubtitledVideoInputBoundary inputBoundary = new CreateSubtitledVideoInteractor(mci);
        CreateSubtitledVideoOutputData output = inputBoundary.execute(input);
        assertFalse(output.getUseCaseFailed());

        if (!output.getUseCaseFailed()) {
            File svideo = new File("src/test/resources/subtitledTestVideo.mp4");
            try {
                assert svideo.exists();
                svideo.delete();
            } catch (Exception e) {
                svideo.delete();
                assert false;
            }
        }
    }
}