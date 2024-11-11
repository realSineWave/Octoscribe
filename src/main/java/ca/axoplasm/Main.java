package ca.axoplasm;

import data_access.AudioToTranscriptDataAccessObject;
import data_access.AudioToTranscriptFileSaveObject;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        AudioToTranscriptFileSaveObject fsf = new AudioToTranscriptFileSaveObject();
        AudioToTranscriptDataAccessObject adta = new AudioToTranscriptDataAccessObject();
        fsf.save(adta.toSegmentedTranscription());
    }
}