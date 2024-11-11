package data_access;

import entity.Segment;
import entity.SegmentedTranscription;
import use_case.audioToTranscript.AudioToTranscriptFileSaveInterface;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AudioToTranscriptFileSaveObject implements AudioToTranscriptFileSaveInterface {

    @Override
    public void save(SegmentedTranscription segmentedTranscription) {
        int i = 0;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("subtitles.txt"));

            for (Segment segment: segmentedTranscription.getSegments()) {
                writer.write(i + "\n");
                writer.write(segment.getStartTime() + " --> " + segment.getEndTime() + "\n");
                writer.write(segment.getText() + "\n\n");
                i++;
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
