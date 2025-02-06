package ca.axoplasm.octoscribe.entity;

import java.time.Duration;

public class Segment {
    private final Duration startTime;
    private final Duration endTime;
    private String text = null;

    public Segment(Duration startTime, Duration endTime, String text) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.text = text;
    }

    public Duration getStartTime() {
        return startTime;
    }

    public Duration getEndTime() {
        return endTime;
    }

    public String getText() {
        return text;
    }
}
