package ca.axoplasm.Octoscribe.use_case.audioToTranscript;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;

public class AudioToTranscriptOutputData {

    private final SegmentedTranscription segmentedTranscription;
    private final String name;

    public AudioToTranscriptOutputData(SegmentedTranscription segmentedTranscription, String name){
        this.segmentedTranscription = segmentedTranscription;
        this.name = name;
    }

    public String getFileName() {
        return this.name;
    }

    public SegmentedTranscription getSegmentedTranscription(){
        return this.segmentedTranscription;
    }
}
