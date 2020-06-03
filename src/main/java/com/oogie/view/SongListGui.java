package com.oogie.view;

import com.oogie.controller.SongListServiceJPA;
import com.oogie.model.SongListEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class SongListGui {
    private JPanel panelMain;
    private JFrame frame;
    private JTextField songNameTextField;
    private JTextField musicianTextField;
    private JTextField yearTextField;
    private JTextField albumTextField;
    private JTextField genreTextField;
    private JButton createButton;
    private JButton retrieveButton;
    private JButton updateButton;
    private String NUNAME = "Hello";
    private JButton deleteButton;
    private JTextArea songListTextArea;
    private JLabel songNameLabel;
    private JLabel musicianLabel;
    private JLabel yearLabel;
    private JLabel albumLabel;
    private JLabel genreLabel;

    private static EntityManager entityManager;
    private static EntityManagerFactory emfactory;
    private static SongListServiceJPA songListServiceJPA;
    private int id = 0;

    public SongListGui() {
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
                } catch (Exception exception) {
                    System.out.println(exception);
                }
            }
        });
        retrieveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SongListEntity songListEntity = songListServiceJPA.retrieve(id);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Song Name: ").append(songListEntity.getSongName()).append(", Musician: ").append(songListEntity.getMusician())
                            .append(", Year: ").append(songListEntity.getYear()).append(", Album: ").append(songListEntity.getGenre())
                            .append(", Genre: ").append(songListEntity.getGenre());
                    songListTextArea.setText(sb.toString());
                } catch (Exception exception) {
                    System.out.println(exception);
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SongListEntity nuSong = createSongListEntity();
                songListServiceJPA.update(nuSong, id);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    songListServiceJPA.delete(id);
                } catch (Exception exception) {
                    System.out.println(exception);
                }
            }
        });
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent we) {
                config();
            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                destroy();
            }
        });
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


    public static void config() {
        emfactory = Persistence.createEntityManagerFactory("discography");
        entityManager = emfactory.createEntityManager();
        songListServiceJPA = new SongListServiceJPA(entityManager);
    }

    public static void destroy() {
        entityManager.close();
        emfactory.close();
    }

    public static void main(String[] args) {
        SongListGui songListGui = new SongListGui();
        songListGui.frame.setVisible(true);
        songListGui.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
