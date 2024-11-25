package ca.axoplasm.Octoscribe.data_access;

import ca.axoplasm.Octoscribe.use_case.createSubtitledVideo.CreateSubtitledVideoMediaConvertInterface;
import ca.axoplasm.Octoscribe.use_case.videoToAudio.VideoToAudioMediaConvertInterface;

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MediaConvertObject implements VideoToAudioMediaConvertInterface, CreateSubtitledVideoMediaConvertInterface {
    String filename = null;

    @Override
    public String audioToVideo(File video) {
        if (this.checkSystem()) {
            return "System doesn't have FFMPEG";
        }
        String audioName = this.createAudioName(video, ".mp3");
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
    public String createSubtitledVideo(File video, File subtitle) {
        if (this.checkSystem()) {
            return "System doesn't have FFMPEG";
        }
        String videoName = this.createSubtitledName(video, ".mp4");
        this.filename = videoName;
        String[] command = {
                "ffmpeg",
                "-i", video.getAbsolutePath(),
                "-vf", "subtitles=" + subtitle.getAbsolutePath(),
                videoName};

        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

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

    private String createAudioName(File video, String format) {
        String name = video.getParent() + "/" +
                video.getName().substring(0, video.getName().lastIndexOf(".")) +
                format;
        File audio = new File(name);
        int counter = 0;
        while (audio.exists()) {
            counter++;
            audio = new File(name.substring(0, name.lastIndexOf(".")) + "(" + counter + ")" + format);
        }
        if (counter != 0) {
            name = name.substring(0, name.lastIndexOf(".")) + "(" + counter + ")" + format;
        }
        return name;
    }

    private String createSubtitledName(File video, String format) {
        String name = video.getParent() + "/" + "subtitled" +
                video.getName().substring(0, video.getName().lastIndexOf(".")) +
                format;
        File subtitledvideo = new File(name);
        int counter = 0;
        while (subtitledvideo.exists()) {
            counter++;
            subtitledvideo = new File(name.substring(0, name.lastIndexOf(".")) + "(" + counter + ")" + format);
        }
        if (counter != 0) {
            name = name.substring(0, name.lastIndexOf(".")) + "(" + counter + ")" + format;
        }
        return name;
    }

    private boolean checkSystem(){
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

            return exitCode != 0;
        } catch (IOException | InterruptedException e) {
            return true;
        }
    }


}
