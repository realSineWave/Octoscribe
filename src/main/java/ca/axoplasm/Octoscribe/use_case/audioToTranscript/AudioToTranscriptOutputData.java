package ca.axoplasm.Octoscribe.use_case.audioToTranscript;

public class AudioToTranscriptOutputData {

    private final String fileName;

    private final boolean status;

    public AudioToTranscriptOutputData(String fileName, Boolean status){
        this.fileName = fileName;
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public Boolean getStatus(){
        return this.status;
    }
}
