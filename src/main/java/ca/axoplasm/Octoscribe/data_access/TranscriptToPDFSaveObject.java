package ca.axoplasm.Octoscribe.data_access;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.use_case.transcriptToPDF.TranscriptToPDFsaveInterface;

import java.io.File;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import ca.axoplasm.Octoscribe.entity.Segment;

public class TranscriptToPDFSaveObject implements TranscriptToPDFsaveInterface {

    @Override
    public File save(SegmentedTranscription segmentedTranscription){

        String outputPdfFile = "output.pdf";

        try {
            createPdfFromText(segmentedTranscription, outputPdfFile);
            System.out.println("PDF created successfully: " + outputPdfFile);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return new File(outputPdfFile);
    }

    public static void createPdfFromText(SegmentedTranscription segmentedTranscription, String pdfFilePath)
            throws IOException {
        PdfWriter writer = new PdfWriter(pdfFilePath);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        List<Segment> segmentList = segmentedTranscription.getSegments();
        for (Segment segment : segmentList) {
            document.add(new Paragraph(segment.getText()));
        }
        document.close();
    }

}
