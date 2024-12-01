package ca.axoplasm.Octoscribe.use_case.transcriptToPDF;

import  ca.axoplasm.Octoscribe.data_access.TranscriptToPDFSaveObject;
import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.use_case.transcriptToPDF.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class transcriptToPDFInteractorTest {

    @Test
   public void successTest() {
       TranscriptToPDFsaveInterface PDFSaveObject = new TranscriptToPDFSaveObject();

        Segment segment_1 = new Segment(Duration.ofNanos(10), Duration.ofNanos(12), "Hello");
        Segment segment_2 = new Segment(Duration.ofNanos(10), Duration.ofNanos(12), "Bye");
       List<Segment> segmentList = new ArrayList<Segment>();
       segmentList.add(segment_1);
       segmentList.add(segment_2);

       SegmentedTranscription segmentedTranscription =
               new SegmentedTranscription("en", "test", segmentList);

       TranscriptToPDFInputData inputData = new TranscriptToPDFInputData(segmentedTranscription);

       TranscriptToPDFInputBoundary interactor = new TranscriptToPDFInteractor(PDFSaveObject);
       TranscriptToPDFOutputData output = interactor.execute(inputData);
        String extension = "";

        int i = output.getFile().getName().lastIndexOf('.');
        if (i > 0) {
            extension = output.getFile().getName().substring(i+1);
        }
        assertEquals("pdf", extension); //check if the output file is pdf or not
   }

}
