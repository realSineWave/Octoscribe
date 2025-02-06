package ca.axoplasm.octoscribe.use_case.translateTranscript;

import ca.axoplasm.octoscribe.entity.SegmentedTranscription;

import java.io.File;

public class TranslateTranscriptOutputData {
    private final String fileName;
    private final SegmentedTranscription segmentedTranscription;
    private final boolean status;
    private final File transcript;

    public TranslateTranscriptOutputData(SegmentedTranscription segmentedTranscription, String fileName,
                                         Boolean status, File transcript) {
        this.fileName = fileName;
        this.status = status;
        this.segmentedTranscription = segmentedTranscription;
        this.transcript = transcript;
    }

    public String getFileName() {
        return fileName;
    }

    public File getTranscript() {
        return transcript;
    }

    public SegmentedTranscription getSegmentedTranscription() {
        return this.segmentedTranscription;
    }

    public Boolean getStatus() {
        return this.status;
    }
}
