package entity;

import java.time.Duration;

public class SegmentFactory implements TransFactory {
    @Override
    public Segment createSegment(float startTime, float endTime, String text) {
        return new Segment(startTime, endTime, text);
    }
}
