package ca.axoplasm.Octoscribe.data_access;

import ca.axoplasm.Octoscribe.use_case.createSubtitledVideo.CreateSubtitledVideoMediaConvertInterface;
import ca.axoplasm.Octoscribe.use_case.videoToAudio.VideoToAudioMediaConvertInterface;

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MediaConvertObject implements VideoToAudioMediaConvertInterface, CreateSubtitledVideoMediaConvertInterface {
    String filename = null;

    /**
     * Transfers the video into audio file and returns whether successfully made an audio file.
     * @param video the video file you needs to take in to generate the audio file.
     * @return String indicates whether we've done it successfully.
     */
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

    /**
     * Takes in the subtitle and video file to generate a subtitled video
     *
     * @param video the video needs to taken in.
     * @param subtitle the subtitle to patch.
     * @return the patched subtitle.
     */
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

    /**
     * Get the name of that file.
     * @return the name of that saved file
     */
    @Override
    public String getFileName() {
        return this.filename;
    }

    /**
     * Helper function. Generate the video in certain format.
     * @param video the video you need to take in for generating audio.
     * @param format the format of the output audio (.mp3)
     * @return The String that indicating the results.
     */
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

    /**
     * Generate the subtitle name  of the video.
     * Takes in video and format to generate a name
     * @param video the video needs to take in for generating the subtitle name.
     * @param format the format you'll need for generating the subtitle. .srt by default.
     * @return the string indicating the name of the subtitle file.
     */
    private String createSubtitledName(File video, String format) {
        String name = video.getParent() + "/" + "subtitled" +
                video.getName().substring(0, video.getName().lastIndexOf(".")) +
                format;
        File subtitledvideo = new File(name);
        int counter = 0;
        while (subtitledvideo.exists()) {
            counter++;
            subtitledvideo = new File(name.substring(0, name.lastIndexOf(".")) +
                    "(" + counter + ")" + format);
        }
        if (counter != 0) {
            name = name.substring(0, name.lastIndexOf(".")) + "(" + counter + ")" + format;
        }
        return name;
    }

    /**
     * Check whether we have the ffmpeg package.
     * @return ture or false. True if we have.
     */
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
