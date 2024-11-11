package entity;

import java.util.List;

public class SegmentedTranscription extends Transcription {
    private final List<Segment> segments;

    public SegmentedTranscription(String language, String text, List<Segment> segment) {
        super(language, text);
        this.segments = segment;
    }

    public List<Segment> getSegments() {
        return segments;
    }
}
