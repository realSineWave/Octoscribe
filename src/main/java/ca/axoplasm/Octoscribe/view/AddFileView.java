package ca.axoplasm.Octoscribe.view;

import ca.axoplasm.Octoscribe.interface_adapter.AddFile.AddFileController;
import ca.axoplasm.Octoscribe.interface_adapter.AddFile.FileListModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class AddFileView extends JFrame {
    private final JFileChooser fileChooser = new JFileChooser();
    private JPanel contentPane;
    private JButton settingsButton;
    private JButton aboutButton;
    private JButton addFileButton;
    private JTable fileStatusTable;
    private JTextField modelName;
    private JTextField audioLanguageCode;
    private JCheckBox translateCheckbox;
    private JTextField translateToLanguageCode;
    private JCheckBox subVideoCheckbox;
    private JCheckBox createPDFCheckbox;
    private AddFileController controller = null;
    private FileListModel fileListModel;
    private SettingsView settingsView;
    private AboutView aboutView;

    public AddFileView(FileListModel fileListModel) {
        setTitle("Octoscribe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();

        fileStatusTable.setModel(fileListModel);
        fileChooser.setMultiSelectionEnabled(true);

        addFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog(AddFileView.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File[] files = fileChooser.getSelectedFiles();
                    controller.addFiles(
                            files,
                            modelName.getText(),
                            audioLanguageCode.getText(),
                            translateCheckbox.isSelected(),
                            translateToLanguageCode.getText(),
                            subVideoCheckbox.isSelected(),
                            createPDFCheckbox.isSelected()
                    );
                    try {
                        controller.execute();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(AddFileView.this, ex.getMessage());
                    }
                }
            }
        });

        settingsButton.addActionListener(e -> {
            settingsView.setVisible(true);
        });

        aboutButton.addActionListener(e -> {
            aboutView.setVisible(true);
        });
    }

    public void setController(AddFileController controller) {
        this.controller = controller;
    }

    public JTable getFileStatusTable() {
        return fileStatusTable;
    }

    public void setSettingsView(SettingsView settingsView) {
        this.settingsView = settingsView;
    }

    public void setAboutView(AboutView aboutView) {
        this.aboutView = aboutView;
    }
}
