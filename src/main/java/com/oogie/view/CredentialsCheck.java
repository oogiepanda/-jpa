package com.oogie.view;

import com.oogie.controller.CredentialsService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CredentialsCheck {
    private JPanel panelMain;
    private JFrame frame;
    private JTextField usernameTextField;
    private JPasswordField passwordPField;
    private JButton confirmButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    private static CredentialsService credentialsService;

    public CredentialsCheck() {
        frame = new JFrame();
        frame.setTitle("Enter Your Credentials");
        frame.setSize(275, 180);
        panelMain = new JPanel();
        usernameTextField = new JTextField(20);
        passwordPField = new JPasswordField(20);
        frame.setContentPane(panelMain);
        panelMain.add(usernameLabel);
        panelMain.add(usernameTextField);
        panelMain.add(passwordLabel);
        panelMain.add(passwordPField);
        panelMain.add(confirmButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SongListGui songListGui = new SongListGui();
                songListGui.frame.setVisible(true);
                frame.dispose();
            }
        });
    }

    public static void main(String[] args) {
        CredentialsCheck credentialsCheck = new CredentialsCheck();
        credentialsCheck.frame.setVisible(true);
        credentialsCheck.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
