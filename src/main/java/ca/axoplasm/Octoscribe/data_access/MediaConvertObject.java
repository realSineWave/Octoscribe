package ca.axoplasm.Octoscribe.data_access;

import ca.axoplasm.Octoscribe.use_case.createSubtitledVideo.CreateSubtitledVideoMediaConvertInterface;
import ca.axoplasm.Octoscribe.use_case.videoToAudio.VideoToAudioMediaConvertInterface;

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MediaConvertObject implements VideoToAudioMediaConvertInterface, CreateSubtitledVideoMediaConvertInterface {
    File file = null;

    /**
     * Transfers the video into audio file and returns whether successfully made an audio file.
     * @param video the video file you needs to take in to generate the audio file.
     * @return String indicates whether we've done it successfully.
     */
    @Override
    public void audioToVideo(File video) {
        if (this.checkSystem()) {
            throw new RuntimeException("FFmpeg not installed");
        }
        String audioName = this.createAudioName(video, ".mp3");
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
                throw new RuntimeException("Process exited with code " + exitCode);
            } else {
                String path = video.getAbsolutePath().substring(0, video.getAbsolutePath().lastIndexOf("/") + 1);
                String audioPath = path + audioName.substring(audioName.lastIndexOf("/") + 1);
                this.file = new File(audioPath);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
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
    public void createSubtitledVideo(File video, File subtitle) {
        if (this.checkSystem()) {
            throw new RuntimeException("Cannot convert video to subtitle");
        }
        String videoName = this.createSubtitledName(video, ".mp4");
        String[] command = {
                "ffmpeg",
                "-i", video.getAbsolutePath(),
                "-vf", "subtitles=" + subtitle.getAbsolutePath(),
                videoName};

        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);

            Process process = pb.start();

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Process exited with code " + exitCode);
            } else {
                String path = video.getAbsolutePath().substring(0, video.getAbsolutePath().lastIndexOf("/") + 1);
                String videoPath = path + videoName.substring(videoName.lastIndexOf("/") + 1);
                this.file = new File(videoPath);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the name of that file.
     * @return the name of that saved file
     */
    @Override
    public File getFile() {
        return this.file;
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
