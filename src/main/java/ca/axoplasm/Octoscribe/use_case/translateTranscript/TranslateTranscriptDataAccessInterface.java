package ca.axoplasm.Octoscribe.use_case.translateTranscript;

import ca.axoplasm.Octoscribe.entity.Segment;
import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;

import jakarta.json.JsonObject;
import java.util.List;

public interface TranslateTranscriptDataAccessInterface {
    /**
     * Translate the transcript into another language into json
     */
     JsonObject getTranslateJson(String text, String language);

    /**
     * Get the segment to the segment translation
     */
    Segment TransSegment(Segment segment, String language);

    /**
     * Get a list of segments output
     */
    List<Segment> TransSegmentList(List<Segment> segments, String language);

    /**
     * Get new translated SegmentedTranscipt results from the initial one that one shoule be the
     * final ultimate pro max ultra and super solution to interface.
     * also i used the combination of the segment to reduce the use of the api.
     */
    SegmentedTranscription TransSegmentedTranscription(SegmentedTranscription transcription, String language);

}
