package ca.axoplasm.Octoscribe.use_case.transcriptToPDF;

import ca.axoplasm.Octoscribe.data_access.TranslateTranscriptFileSaveObject;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptFileSaveInterface;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptInputData;
import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptOutputBoundary;

import java.io.File;

public class TranscriptToPDFInteractor implements TranscriptToPDFInputBoundary {

    private final TranscriptToPDFsaveInterface saveObject;
    private final TranscriptToPDFOutputBoundary outputBoundary;

    public TranscriptToPDFInteractor(TranscriptToPDFsaveInterface saveObject,
                                     TranscriptToPDFOutputBoundary outputBoundary) {
        this.saveObject = saveObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(TranscriptToPDFInputData inputData) {
        final SegmentedTranscription transcript = inputData.getSegmentedTranscription();
            File outputPDF = saveObject.save(transcript);

            TranscriptToPDFOutputData outputData = new TranscriptToPDFOutputData(outputPDF, true);

            outputBoundary.prepareSuccessView(outputData);
    }
}
