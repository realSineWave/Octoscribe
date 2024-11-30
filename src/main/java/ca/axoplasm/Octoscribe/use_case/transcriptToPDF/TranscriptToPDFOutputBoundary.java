package ca.axoplasm.Octoscribe.use_case.transcriptToPDF;

import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptOutputData;

public interface TranscriptToPDFOutputBoundary {

    void prepareSuccessView(TranscriptToPDFOutputData outputData);

    void prepareFailView(String errorMessage);
}
