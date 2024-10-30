package ca.axoplasm.Octoscribe.entity;

public class Segment {
    private int startTime = 0;
    private int endTime = 0;
    private String text = null;

    public Segment(int startTime, int endTime, String text) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.text = text;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public String getText() {
        return text;
    }
}
