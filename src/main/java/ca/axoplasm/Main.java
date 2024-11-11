package ca.axoplasm;

import data_access.DataAccessObject;
import data_access.AudioToTranscriptFileSaveObject;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        AudioToTranscriptFileSaveObject fsf = new AudioToTranscriptFileSaveObject();
        DataAccessObject adta = new DataAccessObject();
        fsf.save(adta.getSegmentedTranscription());
    }
}