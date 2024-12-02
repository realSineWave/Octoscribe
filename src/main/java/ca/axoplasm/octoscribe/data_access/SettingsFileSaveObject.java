package ca.axoplasm.octoscribe.data_access;

import ca.axoplasm.octoscribe.entity.Settings;
import ca.axoplasm.octoscribe.use_case.settings.SettingsDataAccessInterface;
import jakarta.json.*;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

import java.io.*;

public class SettingsFileSaveObject implements SettingsDataAccessInterface {
    AppDirs appDirs = AppDirsFactory.getInstance();
    File configFile;

    public SettingsFileSaveObject() throws IOException {
        String configDir = appDirs.getUserConfigDir("Octoscribe", null, "axoplasm");
        File configDirFile = new File(configDir);
        boolean dirExists = configDirFile.exists();
        boolean mkdirsResult = configDirFile.mkdirs();

        if (!mkdirsResult && !dirExists) {
            throw new IOException("Failed to create configuration directory");
        }

        this.configFile = new File(configDir, "config.json");
    }

    @Override
    public Settings loadSettings() {
        JsonReader jsonReader;
        try {
            jsonReader = Json.createReader(new FileReader(configFile));
        } catch (FileNotFoundException e) {
            return loadDefaultSettings();
        }

        JsonStructure jsonStructure = jsonReader.read();
        String whisperAPIEndpoint = ((JsonString) jsonStructure.getValue("/whisperAPIEndpoint")).getString();
        String whisperAPIKey = ((JsonString) jsonStructure.getValue("/whisperAPIKey")).getString();
        String deeplAPIKey = ((JsonString) jsonStructure.getValue("/deeplAPIKey")).getString();

        return new Settings(whisperAPIEndpoint, whisperAPIKey, deeplAPIKey);
    }

    private Settings loadDefaultSettings() {
        return new Settings(
                "https://api.openai.com/v1/audio/transcriptions",
                null,
                null
        );
    }

    @Override
    public void saveSettings(Settings settings) throws IOException {
        JsonObject settingsModel = Json.createObjectBuilder()
                .add("whisperAPIEndpoint", settings.getWhisperAPIEndpoint())
                .add("whisperAPIKey", settings.getWhisperAPIKey())
                .add("deeplAPIKey", settings.getDeeplAPIKey())
                .build();
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(settingsModel.toString());
        }
    }
}
