package usecase.transcriptToPDF;

import  ca.axoplasm.Octoscribe.data_access.TranscriptToPDFSaveObject;
import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.use_case.transcriptToPDF.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class transcriptToPDFInteractorTest {

    @Test
   public void successTest() {
       TranscriptToPDFsaveInterface PDFSaveObject = new TranscriptToPDFSaveObject();

       Segment segment_1 = new Segment(10, 12, "Hello");
       Segment segment_2 = new Segment(11, 23, "Bye");
       List<Segment> segmentList = new ArrayList<Segment>();
       segmentList.add(segment_1);
       segmentList.add(segment_2);

       SegmentedTranscription segmentedTranscription =
               new SegmentedTranscription("en", "test", segmentList);

       TranscriptToPDFInputData inputData = new TranscriptToPDFInputData(segmentedTranscription);

       TranscriptToPDFOutputBoundary successPresenter = new TranscriptToPDFOutputBoundary() {
           @Override
           public void prepareSuccessView(TranscriptToPDFOutputData outputData) {
               String extension = "";

               int i = outputData.getFile().getName().lastIndexOf('.');
               if (i > 0) {
                   extension = outputData.getFile().getName().substring(i+1);
               }
                assertEquals("pdf", extension); //check if the output file is pdf or not
           }

           @Override
           public void prepareFailView(String errorMessage) {
               fail("failure is not expected");
           }
       };

       TranscriptToPDFInputBoundary interactor =
               new TranscriptToPDFInteractor(PDFSaveObject, successPresenter);

       interactor.execute(inputData);
   }

}
