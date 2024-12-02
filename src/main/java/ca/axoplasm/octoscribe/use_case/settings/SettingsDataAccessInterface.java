package ca.axoplasm.octoscribe.use_case.settings;

import ca.axoplasm.octoscribe.entity.Settings;

import java.io.IOException;

public interface SettingsDataAccessInterface {
    Settings loadSettings();

    void saveSettings(Settings settings) throws IOException;
}
