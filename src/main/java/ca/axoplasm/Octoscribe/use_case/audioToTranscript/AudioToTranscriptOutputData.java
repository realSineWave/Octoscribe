package ca.axoplasm.Octoscribe.use_case.audioToTranscript;

import ca.axoplasm.Octoscribe.entity.SegmentedTranscription;

import java.io.File;

public class AudioToTranscriptOutputData {

    private final SegmentedTranscription segmentedTranscription;
    private final String name;
    private final File transcript;

    public AudioToTranscriptOutputData(SegmentedTranscription segmentedTranscription, String name, File transcript) {

        this.segmentedTranscription = segmentedTranscription;
        this.name = name;
        this.transcript = transcript;
    }

    public String getFileName() {
        return this.name;
    }

    public File getTranscript() {
        return this.transcript;
    }

    public SegmentedTranscription getSegmentedTranscription() {
        return this.segmentedTranscription;
    }
}
