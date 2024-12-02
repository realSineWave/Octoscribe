package ca.axoplasm.Octoscribe.use_case.translateTranscript;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;

import java.io.File;


public class TranslateTranscriptInputData {
    private final SegmentedTranscription segmentedTranscription;
    private final String targetLanguage;
    private final File file;

    public TranslateTranscriptInputData(SegmentedTranscription segmentedTranscription, String targetLanguage, File file) {
        this.segmentedTranscription = segmentedTranscription;
        this.targetLanguage = targetLanguage;
        this.file = file;
    }

    public String getTargetLanguage() {
        return this.targetLanguage;
    }

    public SegmentedTranscription getSegmentedTranscription() {
        return this.segmentedTranscription;
    }

    public File getFile() {
        return this.file;
    }
}
