package entity;

import java.util.List;

public class SegmentedTranscriptionFactory implements TransFactory{
    @Override
    public SegmentedTranscription createSegmented(String language, String text, List<Segment> segment) {
        return new SegmentedTranscription(language, text, segment);
    }
}
