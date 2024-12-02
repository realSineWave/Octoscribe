package ca.axoplasm.Octoscribe.view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class AboutView extends JFrame {
    private JPanel contentPane;
    private JButton homepageButton;
    private JButton issuesButton;
    private JButton licenseButton;
    Desktop desktop = Desktop.getDesktop();

    public AboutView() {
        setTitle("About Octoscribe");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(contentPane);
        pack();

        homepageButton.addActionListener(e -> {
            URI homepageURI = URI.create("https://github.com/realSineWave/Octoscribe");
            try {
                desktop.browse(homepageURI);
            } catch (IOException _) {}
        });

        issuesButton.addActionListener(e -> {
            URI issuesURI = URI.create("https://github.com/realSineWave/Octoscribe/issues");
            try {
                desktop.browse(issuesURI);
            } catch (IOException _) {}
        });

        licenseButton.addActionListener(e -> {
            URI licenseURI = URI.create("https://github.com/realSineWave/Octoscribe/blob/master/LICENSE");
            try {
                desktop.browse(licenseURI);
            } catch (IOException _) {}
        });
    }
}
