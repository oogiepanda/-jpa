package com.oogie.view;

import com.oogie.controller.CredentialsServiceJPA;
import com.oogie.model.CredentialsEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

public class CredentialsCheck {
    private JPanel panelMain;
    private JFrame frame;
    private JTextField usernameTextField;
    private JPasswordField passwordPField;
    private JButton confirmButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    private static EntityManager entityManager;
    private static EntityManagerFactory emfactory;
    private static CredentialsServiceJPA credentialsServiceJPA;

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
                if (!usernameTextField.getText().isEmpty() &&
                        usernameTextField.getText().length() <= 6 &&
                        passwordPField.getPassword().length > 0 &&
                        passwordPField.getPassword().length <= 6) {
                    try {
                        CredentialsEntity credentialsEntity = createCredentialsEntity();
                        List<CredentialsEntity> credentials = credentialsServiceJPA.retrieve(credentialsEntity);
                        if (credentials.size() != 0) {
                            SongListGui songListGui = new SongListGui();
                            songListGui.frame.setVisible(true);
                            frame.dispose();
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent we) {
                config();
            }

            @Override
            public void windowClosing(WindowEvent we) {
                destroy();
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

    public static void config() {
        emfactory = Persistence.createEntityManagerFactory("discography");
        entityManager = emfactory.createEntityManager();
        credentialsServiceJPA = new CredentialsServiceJPA(entityManager);
    }

    public static void destroy() {
        entityManager.close();
        emfactory.close();
    }

    public static void main(String[] args) {
        CredentialsCheck credentialsCheck = new CredentialsCheck();
        credentialsCheck.frame.setVisible(true);
        credentialsCheck.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
