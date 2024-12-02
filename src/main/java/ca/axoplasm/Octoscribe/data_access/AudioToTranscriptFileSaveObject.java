package ca.axoplasm.Octoscribe.data_access;

import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.use_case.audioToTranscript.AudioToTranscriptFileSaveInterface;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;

public class AudioToTranscriptFileSaveObject implements AudioToTranscriptFileSaveInterface {
    private String name = "subtitles.srt";
    private File trancript = null;

    /**
     * Save the segmentedTranscription to a srt file. It's the main method we gonna use for saving the received
     * segmentedTranscription.
     *
     * @param segmentedTranscription The object we want to save.
     * @param path the path we want to store that file to.
     */
    @Override
    public void save(SegmentedTranscription segmentedTranscription, Path path) {
        int i = 0;
        String p = path.toString();
        File file = new File(p.substring(0, p.lastIndexOf(".")) + ".srt");
        int counter = 0;

        try {
            while (file.exists()) {
                counter++;
                file = new File(p.substring(0, p.lastIndexOf(".")) + "(" + counter + ")" + ".srt");
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writeFile(segmentedTranscription, i, writer);
            this.name = file.getName();
            this.trancript = file;


        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Get the name of the subtitle file (srt file)
     * @return the name of the .srt file.
     */
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public File getTranscript() {
        return this.trancript;
    }

    private void writeFile(SegmentedTranscription segmentedTranscription, int i, BufferedWriter writer)
            throws IOException {
        for (Segment segment : segmentedTranscription.getSegments()) {
            writer.write(i + "\n");
            writer.write(durationToString(segment.getStartTime()) + " --> " +
                    durationToString(segment.getEndTime()) + "\n");
            writer.write(segment.getText() + "\n\n");
            i++;
        }
        writer.close();
    }

    /**
     * Convert the duration to a string, to be able to be written in the file name.
     *
     * @param duration the duration object to take in, usually in format of Duration.Nano(),
     * @return the string of that duration. Drop the nano part of the duration.
     */
    private String durationToString(Duration duration) {
        Integer nanos = duration.toNanosPart();
        int sec = duration.toSecondsPart();
        int hours = duration.toHoursPart();
        int min = duration.toMinutesPart();
        if (nanos == 0) {
            return String.format("%02d", hours) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec)
                    + ",000";
        }
        return String.format("%02d", hours) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec) +
                "," + nanos.toString().substring(0, 3);
    }
}

