package com.oogie.view;

import com.oogie.controller.CredentialsServiceJPA;
import com.oogie.controller.SongListServiceJPA;
import com.oogie.model.CredentialsEntity;
import com.oogie.model.SongListEntity;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

public class SongListGui {
    private JPanel panelMain;
    public JFrame frame;
    private JOptionPane emptyOPane;
    private JTextField songNameTextField;
    private JTextField musicianTextField;
    private JTextField yearTextField;
    private JTextField albumTextField;
    private JTextField genreTextField;
    private JButton createButton;
    private JButton retrieveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextArea songListTextArea;
    private JLabel songNameLabel;
    private JLabel musicianLabel;
    private JLabel yearLabel;
    private JLabel albumLabel;
    private JLabel genreLabel;

    //private final MainApp mainApp;
    //private final EntityManager entityManager;
    private SongListServiceJPA songListServiceJPA;
    private int id = 0;

    private List<CredentialsEntity> credentials;

    public SongListGui(final MainApp mainApp, final EntityManager entityManager, final List<CredentialsEntity> credentials) {
        //this.mainApp = mainApp;
        //this.entityManager = entityManager;
        this.credentials = credentials;
        songListServiceJPA = new SongListServiceJPA(entityManager);
        frame = new JFrame();
        frame.setTitle("Song List Storage");
        frame.setSize(300, 350);
        panelMain = new JPanel();
        songNameTextField = new JTextField(20);
        musicianTextField = new JTextField(20);
        yearTextField = new JTextField(20);
        albumTextField = new JTextField(20);
        genreTextField = new JTextField(20);
        songListTextArea = new JTextArea(5, 25);
        songListTextArea.setEditable(false);

        frame.setContentPane(panelMain);
        panelMain.add(songNameLabel);
        panelMain.add(songNameTextField);
        panelMain.add(musicianLabel);
        panelMain.add(musicianTextField);
        panelMain.add(yearLabel);
        panelMain.add(yearTextField);
        panelMain.add(albumLabel);
        panelMain.add(albumTextField);
        panelMain.add(genreLabel);
        panelMain.add(genreTextField);
        panelMain.add(createButton);
        panelMain.add(retrieveButton);
        panelMain.add(updateButton);
        panelMain.add(deleteButton);
        panelMain.add(songListTextArea);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SongListEntity songListEntity = createSongListEntity();
                    id = songListServiceJPA.create(songListEntity);
                    retrieveButton.setEnabled(true);
                    CredentialsEntity credentialsEntity = credentials.get(0);
                    if (isAdmin(credentialsEntity) || isUser(credentialsEntity)) {
                        updateButton.setEnabled(true);
                        if (isAdmin(credentialsEntity)) {
                            deleteButton.setEnabled(true);
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        retrieveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SongListEntity songListEntity = createSongListEntity();
                    List<SongListEntity> songs = songListServiceJPA.retrieve(songListEntity);

                    StringBuilder sb = new StringBuilder();
                    for (SongListEntity s : songs) {
                        sb.append("ID: ").append(s.getId()).append(", Song Name: ").append(s.getSongName()).append(", Musician: ").append(s.getMusician())
                                .append(", Year: ").append(s.getYear()).append(", Album: ").append(s.getGenre())
                                .append(", Genre: ").append(s.getGenre()).append("\n");
                    }
                    songListTextArea.setText(sb.toString());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SongListEntity nuSong = createSongListEntity();
                    songListServiceJPA.update(nuSong, id);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    songListServiceJPA.delete(id);

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent we) {
                updateButton.setEnabled(false);
                deleteButton.setEnabled(false);
                if (isAdmin(credentials.get(0))) {
                    updateButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                } else if (isUser(credentials.get(0))) {
                    updateButton.setEnabled(true);
                }
            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                mainApp.destroy();
            }
            @Override
            public void windowClosed(WindowEvent we) {
                mainApp.destroy();
            }
        });
    }

    private boolean isAdmin(CredentialsEntity ce) {
        return ce.getAffiliation() == 1;
    }

    private boolean isUser(CredentialsEntity ce) {
        return ce.getAffiliation() == 2;
    }

    private SongListEntity createSongListEntity() {
        SongListEntity songListEntity = new SongListEntity();
        if (!songNameTextField.getText().isEmpty()) {
            songListEntity.setSongName(songNameTextField.getText());
            if (!musicianTextField.getText().isEmpty()) {
                songListEntity.setMusician(musicianTextField.getText());
            }
            if (!yearTextField.getText().isEmpty()) {
                Integer yearInt = Integer.parseInt(yearTextField.getText());
                songListEntity.setYear(yearInt);
            }
            if (!albumTextField.getText().isEmpty()) {
                songListEntity.setAlbum(albumTextField.getText());
            }
            if (!genreTextField.getText().isEmpty()) {
                songListEntity.setGenre(genreTextField.getText());
            }
        }
        return songListEntity;
    }
}
