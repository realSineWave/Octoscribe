package ca.axoplasm.Octoscribe.use_case.createSubtitledVideo;

import java.io.File;

public class CreateSubtitledVideoOutputData {
    private final File file;
    private final Boolean useCaseFailed;

    public CreateSubtitledVideoOutputData(File file, Boolean useCaseFailed) {
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