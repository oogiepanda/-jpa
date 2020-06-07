package com.oogie.view;

import com.oogie.controller.CredentialsServiceJPA;
import com.oogie.model.CredentialsEntity;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CredentialsCheckGui {
    private JPanel panelMain;
    public JFrame frame;
    private JTextField usernameTextField;
    private JPasswordField passwordPField;
    private JButton confirmButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    //private final MainApp mainApp;
    //private final EntityManager entityManager;
    private CredentialsServiceJPA credentialsServiceJPA;

    public List<CredentialsEntity> credentials;

    public CredentialsCheckGui(final MainApp mainApp, final EntityManager entityManager) {
        //this.mainApp = mainApp;
        //this.entityManager = entityManager;
        credentialsServiceJPA = new CredentialsServiceJPA(entityManager);
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
                if (!usernameTextField.getText().isEmpty() &&
                        usernameTextField.getText().length() <= 6 &&
                        passwordPField.getPassword().length > 0 &&
                        passwordPField.getPassword().length <= 6) {
                    try {
                        CredentialsEntity credentialsEntity = createCredentialsEntity();
                        credentials = credentialsServiceJPA.retrieve(credentialsEntity);
                        if (credentials.size() != 0) {
                            SongListGui songListGui = new SongListGui(mainApp, entityManager, credentials);
                            songListGui.frame.setVisible(true);
                            songListGui.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                            frame.dispose();
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
    }

    private CredentialsEntity createCredentialsEntity() {
        CredentialsEntity credentialsEntity = new CredentialsEntity();
        credentialsEntity.setUsername(usernameTextField.getText());
        char[] passString = passwordPField.getPassword();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < passwordPField.getPassword().length; i++) {
            sb.append(passString[i]);
        }
        credentialsEntity.setPassword(sb.toString());
        return credentialsEntity;
    }
}
