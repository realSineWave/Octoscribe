package ca.axoplasm.Octoscribe.entity;

import java.time.Duration;

public class SegmentFactory implements TransFactory {
    @Override
    public Segment createSegment(Duration startTime, Duration endTime, String text) {
        return new Segment(startTime, endTime, text);
    }
}
