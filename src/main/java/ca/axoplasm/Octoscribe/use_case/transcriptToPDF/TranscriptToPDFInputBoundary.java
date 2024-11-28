package ca.axoplasm.Octoscribe.use_case.transcriptToPDF;

import ca.axoplasm.Octoscribe.use_case.translateTranscript.TranslateTranscriptInputData;

public interface TranscriptToPDFInputBoundary {

    void execute(TranslateTranscriptInputData inputData);
}
