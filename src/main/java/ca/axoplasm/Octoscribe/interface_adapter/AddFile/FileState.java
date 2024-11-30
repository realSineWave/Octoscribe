package ca.axoplasm.Octoscribe.interface_adapter.AddFile;

import java.io.File;

public class FileState {
    private File file;
    private FileOptions options;
    private boolean isComplete;

    public void FileState(File file, FileOptions options, boolean isComplete) {
        this.file = file;
        this.options = options;
        this.isComplete = isComplete;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
