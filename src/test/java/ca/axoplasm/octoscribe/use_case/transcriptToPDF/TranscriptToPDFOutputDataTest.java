package ca.axoplasm.octoscribe.use_case.transcriptToPDF;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TranscriptToPDFOutputDataTest {

    @Test
    public void testTranscriptToPDFOutputData() {
        File file = new File("src/test/resources/output.pdf");
        TranscriptToPDFOutputData outputData = new TranscriptToPDFOutputData(file, true);

        assertEquals(outputData.getStatus(), true);
    }
}
