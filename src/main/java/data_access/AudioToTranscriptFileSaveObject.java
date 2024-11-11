package data_access;

import entity.Segment;
import entity.SegmentedTranscription;
import use_case.audioToTranscript.AudioToTranscriptFileSaveInterface;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AudioToTranscriptFileSaveObject implements AudioToTranscriptFileSaveInterface {

    @Override
    public void save(SegmentedTranscription segmentedTranscription) {
        int i = 0;
        String name = "subtitles";
        File file = new File(name + ".txt");
        int counter = 0;

        try {
            while (file.exists()) {
                counter++;
                file = new File(name + "(" + counter + ")" + ".txt");
            }

            if (counter == 0){
                BufferedWriter writer = new BufferedWriter(new FileWriter(name + ".txt"));
                writeFile(segmentedTranscription, i, writer);
            }
            else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(name + "(" + counter + ")" + ".txt"));
                writeFile(segmentedTranscription, i, writer);
            }

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private void writeFile(SegmentedTranscription segmentedTranscription, int i, BufferedWriter writer) throws IOException {
        for (Segment segment: segmentedTranscription.getSegments()) {
            writer.write(i + "\n");
            writer.write(segment.getStartTime() + " --> " + segment.getEndTime() + "\n");
            writer.write(segment.getText() + "\n\n");
            i++;
        }
        writer.close();
    }
}
