package Gui;

import javax.swing.*;
import java.awt.*;

public class gui {

    private JFrame frame;
    private JPanel CenterPanel;
    private JPanel ButtonPanel;


    public gui() {
        frame = new JFrame("Post Tracking System");
        frame.setSize(1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        CenterPanel = new JPanel();
        CenterPanel.setBackground(Color.WHITE);
        frame.add(CenterPanel, BorderLayout.CENTER);


        ButtonPanel = new JPanel(new GridLayout(1, 6, 10, 0));
        String[] buttons = { "Create system", "Start", "Stop", "Resume", "All packages info", "Branch info" };
        for (String text : buttons) {
            JButton button = new JButton(text);
            if (text.equals("Create system")) {
                button.addActionListener(e -> openConfigDialog());
            }
            ButtonPanel.add(button);
        }
        frame.add(ButtonPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void openConfigDialog() {
        JDialog dialog = new JDialog(frame, "Create Post System", true);
        dialog.setSize(600, 400);
        dialog.setLayout(new GridLayout(7, 1, 5, 5));

        JLabel branchLabel = new JLabel("Number of Branches:",JLabel.CENTER);
        JSlider branchSlider = new JSlider(1, 10, 1);
        branchSlider.setMajorTickSpacing(1);
        branchSlider.setPaintTicks(true);
        branchSlider.setPaintLabels(true);

        JLabel truckLabel = new JLabel("Number of Trucks per Branch:",JLabel.CENTER);
        JSlider truckSlider = new JSlider(1, 10, 1);
        truckSlider.setMajorTickSpacing(1);
        truckSlider.setPaintTicks(true);
        truckSlider.setPaintLabels(true);

        JLabel PackagesLabel = new JLabel("Number of Packages:",JLabel.CENTER);
        JSlider PackagesSlider = new JSlider(2, 20, 2);
        PackagesSlider.setMajorTickSpacing(2);
        PackagesSlider.setPaintTicks(true);
        PackagesSlider.setPaintLabels(true);


        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            int branches = branchSlider.getValue();
            int trucks = truckSlider.getValue();
            int Packages = PackagesSlider.getValue();
            System.out.println("Branches: " + branches + ", Trucks: " + trucks + ", Packages: " + Packages);
            dialog.dispose();
        });
        JButton CancelButton = new JButton("Cancel");
        CancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        JPanel dialogButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        Dimension buttonSize = new Dimension(250, 50);
        okButton.setPreferredSize(buttonSize);
        CancelButton.setPreferredSize(buttonSize);

        dialog.add(branchLabel);
        dialog.add(branchSlider);
        dialog.add(truckLabel);
        dialog.add(truckSlider);
        dialog.add(PackagesLabel);
        dialog.add(PackagesSlider);
        dialogButtonPanel.add(okButton);
        dialogButtonPanel.add(CancelButton);
        dialog.add(dialogButtonPanel);

        dialog.setVisible(true);
    }
}
