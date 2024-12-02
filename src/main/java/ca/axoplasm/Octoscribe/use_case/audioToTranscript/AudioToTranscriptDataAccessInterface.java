package ca.axoplasm.Octoscribe.use_case.audioToTranscript;

import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import jakarta.json.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The interface of the DAO for the Transcribe Use Case.
 */
public interface AudioToTranscriptDataAccessInterface {

    /**
     * Get the translated result in format of JSObject
     */
    JsonObject getTranscribedJson(File file) throws IOException;

    /**
     * Output segments
     *
     * @return segmented results
     */
    List<Segment> toSegments(JsonObject jsonObject);


    SegmentedTranscription getSegmentedTranscription(File file) throws IOException;
}
