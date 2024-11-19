package ca.axoplasm.Octoscribe.use_case.audioToTranscript;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;

public class AudioToTranscriptOutputData {

    private final SegmentedTranscription segmentedTranscription;
    private final String name;
    private final boolean status;

    public AudioToTranscriptOutputData(SegmentedTranscription segmentedTranscription, String name, Boolean status){
        this.segmentedTranscription = segmentedTranscription;
        this.name = name;
        this.status = status;
    }

    public String getFileName() {
        return this.name;
    }

    public SegmentedTranscription getSegmentedTranscription(){
        return this.segmentedTranscription;
    }

    public Boolean getStatus(){
        return this.status;
    }
}
