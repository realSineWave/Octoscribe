package ca.axoplasm.Octoscribe.data_access;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.use_case.transcriptToPDF.TranscriptToPDFsaveInterface;

import java.io.File;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.IOException;
import java.util.List;

import ca.axoplasm.Octoscribe.entity.Segment;

public class TranscriptToPDFSaveObject implements TranscriptToPDFsaveInterface {

    /**
     * Generates the file in format of PDF.
     * The file name is output.pdf by default.
     * @param segmentedTranscription
     * @return the output pdf file.
     */
    @Override
    public File save(SegmentedTranscription segmentedTranscription){

        String outputPdfFile = "output.pdf";

        try {
            createPdfFromSegmentedTranscription(segmentedTranscription, outputPdfFile);
            System.out.println("PDF created successfully: " + outputPdfFile);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return new File(outputPdfFile);
    }

    /**
     * Generates the PDF from segmentedTranscription.
     * It is the helper function of the above one(save()).
     * @param segmentedTranscription the segmentedTranscription waiting to be saved.
     * @param pdfFilePath the location of the pdf you want to save, by default it's the folder the application is.
     * @throws IOException output io exception.
     */
    public static void createPdfFromSegmentedTranscription(SegmentedTranscription segmentedTranscription, String pdfFilePath)
            throws IOException {
        PdfWriter writer = new PdfWriter(pdfFilePath);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        List<Segment> segmentList = segmentedTranscription.getSegments();
        for (Segment segment : segmentList) {
            document.add(new Paragraph(segment.getText()).setMarginBottom(0));
        }
        document.close();
    }

}
