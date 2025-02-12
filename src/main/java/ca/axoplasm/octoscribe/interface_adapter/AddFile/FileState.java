package ca.axoplasm.octoscribe.interface_adapter.AddFile;

import java.io.File;

public class FileState {
    private File file;
    private FileOptions options;
    private Status status;

    ;
    public FileState(File file, FileOptions options) {
        this.file = file;
        this.options = options;
        this.status = Status.PENDING;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public FileOptions getOptions() {
        return options;
    }

    public String getFileName() {
        return file.getName();
    }

    public File getFile() {
        return file;
    }

    public enum Status {
        PENDING,
        COMPLETE,
        FAILED
    }
}
