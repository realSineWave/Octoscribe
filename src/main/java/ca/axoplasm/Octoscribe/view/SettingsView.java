package ca.axoplasm.Octoscribe.view;

import ca.axoplasm.Octoscribe.interface_adapter.Settings.SettingsController;

import javax.swing.*;
import java.io.IOException;

public class SettingsView extends JFrame {
    private JPanel contentPane;
    private JTextField whisperAPIField;
    private JButton cancelButton;
    private JButton OKButton;
    private JPasswordField whisperAPIKeyField;
    private JPasswordField deeplAPIKeyField;
    private SettingsController controller;

    public SettingsView() {
        setTitle("Settings");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(contentPane);
        pack();

        cancelButton.addActionListener(e -> {
            dispose();
        });

        OKButton.addActionListener(e -> {
            try {
                controller.updateSettings(
                        whisperAPIField.getText(),
                        new String(whisperAPIKeyField.getPassword()),
                        new String(deeplAPIKeyField.getPassword())
                );
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(SettingsView.this, ex.getMessage());
            }
            dispose();
        });
    }

    public void setController(SettingsController controller) {
        this.controller = controller;
    }

    public void setWhisperAPI(String whisperAPIEndpoint) {
        this.whisperAPIField.setText(whisperAPIEndpoint);
    }

    public void setWhisperAPIKey(String whisperAPIKey) {
        this.whisperAPIKeyField.setText(whisperAPIKey);
    }

    public void setDeeplAPIKey(String deeplAPIKey) {
        this.deeplAPIKeyField.setText(deeplAPIKey);
    }
}
