package entity;

public class Segment {
    private final float startTime;
    private final float endTime;
    private String text = null;

    public Segment(float startTime, float endTime, String text) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.text = text;
    }

    public float getStartTime() {
        return startTime;
    }

    public float getEndTime() {
        return endTime;
    }

    public String getText() {
        return text;
    }
}