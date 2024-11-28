package ca.axoplasm.Octoscribe.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AddFileView extends JFrame {
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
    private final JFileChooser fileChooser = new JFileChooser();

    public AddFileView() {
        setTitle("Octoscribe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();

        fileChooser.setMultiSelectionEnabled(true);

        addFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog(AddFileView.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File[] files = fileChooser.getSelectedFiles();


                }
            }
        });
    }
}
