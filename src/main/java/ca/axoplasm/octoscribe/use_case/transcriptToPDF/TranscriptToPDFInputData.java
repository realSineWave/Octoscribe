package ca.axoplasm.octoscribe.use_case.transcriptToPDF;

import ca.axoplasm.octoscribe.entity.SegmentedTranscription;

public class TranscriptToPDFInputData {

    private final SegmentedTranscription segmentedTranscription;

    public TranscriptToPDFInputData(SegmentedTranscription segmentedTranscription) {
        this.segmentedTranscription = segmentedTranscription;
    }

    public SegmentedTranscription getSegmentedTranscription() {return segmentedTranscription;}
}
