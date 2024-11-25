package ca.axoplasm.Octoscribe.use_case.videoToAudio;

public class VideoToAudioOutputData {
    private final String filename;
    private final Boolean useCaseFailed;

    public VideoToAudioOutputData(String filename, Boolean useCaseFailed) {
        this.filename = filename;
        this.useCaseFailed = useCaseFailed;
    }

    public String getFilename() {
        return filename;
    }

    public Boolean getUseCaseFailed() {
        return useCaseFailed;
    }
}
