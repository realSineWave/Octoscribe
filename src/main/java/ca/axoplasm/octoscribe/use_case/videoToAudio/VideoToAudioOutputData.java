package ca.axoplasm.octoscribe.use_case.videoToAudio;

import java.io.File;

public class VideoToAudioOutputData {
    private final File file;
    private final Boolean useCaseFailed;

    public VideoToAudioOutputData(File file, Boolean useCaseFailed) {
        this.file = file;
        this.useCaseFailed = useCaseFailed;
    }

    public File getFile() {
        return file;
    }

    public Boolean getUseCaseFailed() {
        return useCaseFailed;
    }
}
