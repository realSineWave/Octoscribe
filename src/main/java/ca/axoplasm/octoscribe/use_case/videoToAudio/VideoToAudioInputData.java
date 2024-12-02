package ca.axoplasm.octoscribe.use_case.videoToAudio;

import java.io.File;

public class VideoToAudioInputData {
    private final File videoFile;

    public VideoToAudioInputData(File videoFile) {
        this.videoFile = videoFile;
    }

    public File getVideoFile() {
        return videoFile;
    }
}
