package ca.axoplasm.octoscribe.use_case.transcriptToPDF;

import ca.axoplasm.octoscribe.entity.SegmentedTranscription;

import java.io.File;

public class TranscriptToPDFInteractor implements TranscriptToPDFInputBoundary {

    private final TranscriptToPDFSaveInterface saveObject;

    public TranscriptToPDFInteractor(TranscriptToPDFSaveInterface saveObject) {
        this.saveObject = saveObject;
    }

    @Override
    public TranscriptToPDFOutputData execute(TranscriptToPDFInputData inputData) {
        final SegmentedTranscription transcript = inputData.getSegmentedTranscription();
        File outputPDF = saveObject.save(transcript, inputData.getFile().toPath());

        return new TranscriptToPDFOutputData(outputPDF, true);
    }
}
