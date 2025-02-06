package ca.axoplasm.octoscribe.use_case.translateTranscript;

import ca.axoplasm.octoscribe.entity.SegmentedTranscription;

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
