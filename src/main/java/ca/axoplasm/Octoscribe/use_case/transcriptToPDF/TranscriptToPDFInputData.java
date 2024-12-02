package ca.axoplasm.Octoscribe.use_case.transcriptToPDF;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;

import java.io.File;

public class TranscriptToPDFInputData {

    private final SegmentedTranscription segmentedTranscription;
    private final File file;

    public TranscriptToPDFInputData(SegmentedTranscription segmentedTranscription, File file) {
        this.segmentedTranscription = segmentedTranscription;
        this.file = file;
    }

    public SegmentedTranscription getSegmentedTranscription() {return segmentedTranscription;}

    public File getFile() {
        return file;
    }
}
