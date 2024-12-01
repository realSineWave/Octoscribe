package ca.axoplasm.Octoscribe.use_case.transcriptToPDF;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;

import java.io.File;

public class TranscriptToPDFInteractor implements TranscriptToPDFInputBoundary {

    private final TranscriptToPDFsaveInterface saveObject;

    public TranscriptToPDFInteractor(TranscriptToPDFsaveInterface saveObject) {
        this.saveObject = saveObject;
    }

    @Override
    public TranscriptToPDFOutputData execute(TranscriptToPDFInputData inputData) {
        final SegmentedTranscription transcript = inputData.getSegmentedTranscription();
            File outputPDF = saveObject.save(transcript);

            return new TranscriptToPDFOutputData(outputPDF, true);
    }
}
