package ca.axoplasm.octoscribe.use_case.createSubtitledVideo;

import java.io.File;

public class CreateSubtitledVideoInputData {
    private final File videoFile;
    private final File subtitleFile;

    public CreateSubtitledVideoInputData(File videoFile, File subtitleFile) {
        this.videoFile = videoFile;
        this.subtitleFile = subtitleFile;
    }

    public File getVideoFile() {
        return videoFile;
    }

    public File getSubtitleFile() {
        return subtitleFile;
    }
}
