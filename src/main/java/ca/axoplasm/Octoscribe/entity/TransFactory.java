package ca.axoplasm.Octoscribe.entity;

import java.time.Duration;
import java.util.List;

public interface TransFactory {
    /**
     * Creates a new Segmented transcrption.
     * @param language That shows the new
     * @param text That contains the text in the file.
     * @param segment that includes the segemented text file.
     * @return Segmented transcription.
     */
    default SegmentedTranscription createSegmented(String language, String text, List<Segment> segment){
        throw new UnsupportedOperationException("Not implemented yet or not a SegmentedTranscript");
    }

    /**
     * Create new segment
     * @param startTime Start time of the segment.
     * @param endTime End time of that segment.
     * @param text text in that segment
     * @return the segment.
     */
    default Segment createSegment(Duration startTime, Duration endTime, String text){
        throw new UnsupportedOperationException("Not implemented yet or not a Segment");
    }

    /**
     * Creates a transcription
     * @param language the language of that transcription
     * @param text the text file should be in the
     * @return a transcription.
     */
    default Transcription createTranscription(String language, String text){
        throw new UnsupportedOperationException("Not Implemented yet or not a Transcription.");
    }

}
