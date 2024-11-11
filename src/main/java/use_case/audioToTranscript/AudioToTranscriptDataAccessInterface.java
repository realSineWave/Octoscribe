package use_case.audioToTranscript;

import entity.Segment;
import entity.SegmentedTranscription;

import javax.json.JsonObject;
import java.util.List;

/**
 * The interface of the DAO for the Transcribe Use Case.
 */
public interface AudioToTranscriptDataAccessInterface {

//    /**
//     * save the new file.
//     */
//    void save();

//    /**
//     * Checkout the original language
//     */
//    void checkOriginLanguage();

    /**
     * Get the translated result in format of JSObject
     */
    JsonObject getTranscriptJson();

    /**
     * Output segments
     *
     * @return segmented results
     */
    List<Segment> toSegments(JsonObject jsonObject);


    SegmentedTranscription getSegmentedTranscription();
}