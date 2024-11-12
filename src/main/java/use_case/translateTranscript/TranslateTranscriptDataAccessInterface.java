package use_case.translateTranscript;

import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;

import javax.json.JsonObject;
import java.util.List;

public interface TranslateTranscriptDataAccessInterface {
    /**
     * Translate the transcript into another language into json
     */
    default JsonObject getTranslateJson(String text, String language){
        throw new UnsupportedOperationException("Not implemented or wrong object");
    }

    /**
     * Get the segment to the segment translation
     */
    default Segment TransSegment(Segment segment, String targetLanguage){
        throw new UnsupportedOperationException("Not implemented or wrong object");
    }

    /**
     * Get a list of segments output
     */
    default List<Segment> TransSegmentList(List<Segment> segments, String language){
        throw new UnsupportedOperationException("Not implemented or wrong object");
    }

    /**
     * Get new translated SegmentedTranscipt results from the initial one that one shoule be the
     * final ultimate pro max ultra and super solution to interface.
     */
    default SegmentedTranscription TransSegmentedTranscription(SegmentedTranscription transcription, String language){
        throw new UnsupportedOperationException("Not implemented or wrong object");
    }







}
