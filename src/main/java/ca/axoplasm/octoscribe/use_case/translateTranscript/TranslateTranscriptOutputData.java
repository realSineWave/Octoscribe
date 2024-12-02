package ca.axoplasm.octoscribe.use_case.translateTranscript;

import ca.axoplasm.octoscribe.entity.SegmentedTranscription;

public class TranslateTranscriptOutputData {
    private final String fileName;
    private final SegmentedTranscription segmentedTranscription;
    private final boolean status;

    public TranslateTranscriptOutputData(SegmentedTranscription segmentedTranscription, String fileName,
                                         Boolean status){
        this.fileName = fileName;
        this.status = status;
        this.segmentedTranscription = segmentedTranscription;
    }

    public String getFileName() {
        return fileName;
    }

    public SegmentedTranscription getSegmentedTranscription(){
        return this.segmentedTranscription;
    }

    public Boolean getStatus(){
        return this.status;
    }
}
