package ca.axoplasm.Octoscribe.data_access;

import ca.axoplasm.Octoscribe.use_case.videoToAudio.VideoToAudioMediaConvertInterface;

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MediaConvertObject implements VideoToAudioMediaConvertInterface {
    String filename = null;

    @Override
    public String audioToVideo(File video) {
        this.checkSystem();
        String audioName = this.createAudioName(video);
        this.filename = audioName;
        String[] command = {
                "ffmpeg",
                "-i", video.getAbsolutePath(),
                "-map",
                "0:a",
                "-acodec",
                "libmp3lame",
                audioName};

        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);

            Process process = pb.start();

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return "Video Conversion Failed";
            } else {
                return "Video Conversion Successful";
            }
        } catch (IOException | InterruptedException e) {
            return "Video Conversion Failed";
        }
    }

    @Override
    public String getFileName() {
        return this.filename;
    }

    private String createAudioName(File video) {
        String name = video.getParent() + "/" +
                video.getName().substring(0, video.getName().lastIndexOf(".")) +
                ".mp3";
        File audio = new File(name);
        int counter = 0;
        while (audio.exists()) {
            counter++;
            audio = new File(name.substring(0, name.lastIndexOf(".")) + "(" + counter + ")" + ".mp3");
        }
        if (counter != 0) {
            name = name.substring(0, name.lastIndexOf(".")) + "(" + counter + ")" + ".mp3";
        }
        return name;
    }

    private void checkSystem(){
        try {
            ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-version");
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new RuntimeException("System doesn't have FFMPEG");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("System doesn't have FFMPEG");
        }
    }


}
