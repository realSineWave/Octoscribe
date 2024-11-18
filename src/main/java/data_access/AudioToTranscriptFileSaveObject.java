package data_access;

import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import use_case.audioToTranscript.AudioToTranscriptFileSaveInterface;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

public class AudioToTranscriptFileSaveObject implements AudioToTranscriptFileSaveInterface {
    private String name = "subtitles.txt";

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
                this.name = name + ".txt";
            }
            else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(name + "(" + counter + ")" + ".txt"));
                writeFile(segmentedTranscription, i, writer);
                this.name = name + "(" + counter + ")" + ".txt";
            }


        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    private void writeFile(SegmentedTranscription segmentedTranscription, int i, BufferedWriter writer) throws IOException {
        for (Segment segment: segmentedTranscription.getSegments()) {
            writer.write(i + "\n");
            writer.write(durationToString(segment.getStartTime()) + " --> " + durationToString(segment.getEndTime()) + "\n");
            writer.write(segment.getText() + "\n\n");
            i++;
        }
        writer.close();
    }

    private String durationToString(Duration duration) {
        String str = duration.toString();
        String time = str.replaceAll("[[^\\d.]]", "");
        float seconds = Float.parseFloat(time);
        int nonnanoseconds = (int) seconds;
        Float nanos = seconds - nonnanoseconds;
        int minutes = nonnanoseconds/60;
        String rsec = String.format("%02d", nonnanoseconds%60);
        String hours = String.format("%02d", minutes/60);
        String rmin = String.format("%02d", minutes%60);
        if (nanos == 0.0) {
            return hours + ":" + rmin + ":" + rsec + ",000";
        }
        return hours + ":" + rmin + ":" + rsec + "," + nanos.toString().substring(2, 5);
    }
}

