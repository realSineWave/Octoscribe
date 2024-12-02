package ca.axoplasm.Octoscribe.use_case.settings;

import ca.axoplasm.Octoscribe.entity.Settings;

import java.io.IOException;

public interface SettingsDataAccessInterface {
    Settings loadSettings();

    void saveSettings(Settings settings) throws IOException;
}
