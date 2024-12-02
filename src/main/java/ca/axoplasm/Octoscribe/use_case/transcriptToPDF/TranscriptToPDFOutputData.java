package ca.axoplasm.Octoscribe.use_case.transcriptToPDF;

import java.io.File;

public class TranscriptToPDFOutputData {

    private final File file;
    private final boolean status;

    public TranscriptToPDFOutputData
            (File file, boolean status) {

        this.file = file;
        this.status = status;

    }

    public File getFile() {
        return file;
    }

    public Boolean getStatus() {
        return this.status;
    }

}
