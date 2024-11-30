package ca.axoplasm.Octoscribe.use_case.createSubtitledVideo;

public class CreateSubtitledVideoOutputData {
    private final String fileName;
    private final Boolean useCaseFailed;

    public CreateSubtitledVideoOutputData(String fileName, Boolean useCaseFailed) {
        this.fileName = fileName;
        this.useCaseFailed = useCaseFailed;
    }

    public String getFileName() {
        return fileName;
    }

    public Boolean getUseCaseFailed() {
        return useCaseFailed;
    }
}
