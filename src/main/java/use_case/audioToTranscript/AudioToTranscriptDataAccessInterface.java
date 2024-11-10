package use_case.audioToTranscript;

import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;
import ca.axoplasm.Octoscribe.entity.Transcription;
import netscape.javascript.JSObject;

import javax.json.JsonObject;
import java.io.File;

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
    JsonObject getTranscript(File audio);

    /**
     * Output segments
     * @return segmented results
     */
    Segment toSegment(File audio);

    /**
     * @param audio the audio file
     * @return the Segmented transcription.
     */
    SegmentedTranscription toSegmentedTranscription(File audio);


}
