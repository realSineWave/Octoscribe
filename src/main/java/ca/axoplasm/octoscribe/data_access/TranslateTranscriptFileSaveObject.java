package ca.axoplasm.octoscribe.data_access;

import ca.axoplasm.octoscribe.entity.Segment;
import ca.axoplasm.octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.octoscribe.use_case.translateTranscript.TranslateTranscriptFileSaveInterface;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TranslateTranscriptFileSaveObject implements TranslateTranscriptFileSaveInterface {
    private String name;

    /**
     * Initialization of that DAO. Name of that file is at the time the object is created.
     */
    public TranslateTranscriptFileSaveObject() {
        Date date = new Date();
        StringBuilder s = new StringBuilder();
        s.append("Translated_transcript_at_");
        s.append(date);
        this.name = s.toString();
    }

    /**
     * Saves the segmentedTranscription to a srt file.
     * Updates the exact time of the file is created.
     * @param segmentedTranscription the segtedTrans needs to take in for saving operation.
     */
    @Override
    public void save(SegmentedTranscription segmentedTranscription) {
        int i = 0;
        Date date = new Date();
        StringBuilder fileName = new StringBuilder();
        fileName.append("Translated_Transcript_at_");
        fileName.append(date);
        File file = new File(fileName + ".srt");
        int counter = 0;

        try {
            while (file.exists()) {
                counter++;
                file = new File(fileName + "(" + counter + ")" + ".srt");
            }

            if (counter == 0){
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".srt"));
                writeFile(segmentedTranscription, i, writer);
                this.name = fileName + ".srt";
            }
            else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + "(" + counter + ")" + ".srt"));
                writeFile(segmentedTranscription, i, writer);
                this.name = fileName + "(" + counter + ")" + ".srt";
            }


        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    /**
     * gets the name of the saved object.
     * @return
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Helper function about writing the file.
     * @param segmentedTranscription segmented file you takes in for writing.
     * @param i the trials of wrting that file.
     * @param writer writes the file BufferedWriter.
     * @throws IOException If there is something wrong regarding the file output.
     */
    private void writeFile(SegmentedTranscription segmentedTranscription, int i, BufferedWriter writer) throws IOException {
        for (Segment segment: segmentedTranscription.getSegments()) {
            writer.write(i + "\n");
            writer.write(durationToString(segment.getStartTime()) + " --> " + durationToString(segment.getEndTime()) + "\n");
            writer.write(segment.getText() + "\n\n");
            i++;
        }
        writer.close();
    }

    /**
     * Converts the duration to a string.
     * Drops the nanosecond part.
     * @param duration The duration needs to be converted.
     * @return Return the string of that duration.
     */
    private String durationToString(Duration duration) {
        Integer nanos = duration.toNanosPart();
        int sec = duration.toSecondsPart();
        int hours = duration.toHoursPart();
        int min = duration.toMinutesPart();
        if (nanos == 0) {
            return String.format("%02d", hours) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec) + ",000";
        }
        return String.format("%02d", hours) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec) + "," + nanos.toString().substring(0, 3);
    }
}
