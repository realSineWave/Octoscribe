package use_case.translateTranscript;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;


public class TranslateTranscriptInputData {
    private final SegmentedTranscription segmentedTranscription;
    private final String targetLanguage;

    public TranslateTranscriptInputData(SegmentedTranscription segmentedTranscription, String targetLanguage){
        this.segmentedTranscription = segmentedTranscription;
        this.targetLanguage = targetLanguage;
    }

    public String getTargetLanguage(){
        return this.targetLanguage;
    }

    public SegmentedTranscription getSegmentedTranscription(){
        return this.segmentedTranscription;
    }
}
