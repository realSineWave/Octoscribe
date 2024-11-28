package ca.axoplasm.Octoscribe.use_case.transcriptToPDF;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;

public class TranscriptToPDFInputData {

    private final SegmentedTranscription segmentedTranscription;

    public TranscriptToPDFInputData(SegmentedTranscription segmentedTranscription) {
        this.segmentedTranscription = segmentedTranscription;
    }

    public SegmentedTranscription getSegmentedTranscription() {return segmentedTranscription;}
}
