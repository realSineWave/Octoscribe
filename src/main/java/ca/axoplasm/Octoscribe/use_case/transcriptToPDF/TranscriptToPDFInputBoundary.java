package ca.axoplasm.Octoscribe.use_case.transcriptToPDF;

import java.io.File;

public interface TranscriptToPDFInputBoundary {
    TranscriptToPDFOutputData execute(TranscriptToPDFInputData inputData);
}
