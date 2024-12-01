package ca.axoplasm.Octoscribe.use_case.translateTranscript;

import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;

import jakarta.json.JsonObject;

import java.io.IOException;
import java.util.List;

public interface TranslateTranscriptDataAccessInterface {
    /**
     * Translate the transcript into another language into json
     */
    default JsonObject getTranslateJson(String text, String language) throws IOException {
        throw new UnsupportedOperationException("Not implemented or wrong object");
    }

    /**
     * Get the segment to the segment translation
     */
    default Segment TransSegment(Segment segment, String language) throws IOException{
        throw new UnsupportedOperationException("Not implemented or wrong object");
    }

    /**
     * Get a list of segments output
     */
    default List<Segment> TransSegmentList(List<Segment> segments, String language) throws IOException {
        throw new UnsupportedOperationException("Not implemented or wrong object");
    }

    /**
     * Get new translated SegmentedTranscipt results from the initial one that one shoule be the
     * final ultimate pro max ultra and super solution to interface.
     * also i used the combination of the segment to reduce the use of the api.
     */
    default SegmentedTranscription TransSegmentedTranscription(SegmentedTranscription transcription, String language) throws IOException {
        throw new UnsupportedOperationException("Not implemented or wrong object");
    }







}
