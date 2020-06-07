package com.oogie.view;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;

public class MainApp {
    private EntityManagerFactory emfactory;
    private EntityManager entityManager;

    public static void main(String[] args) {
        MainApp mainApp = new MainApp();
        mainApp.run();
    }

    public void run() {
        config();
        CredentialsCheckGui credentialsCheckGui = new CredentialsCheckGui(this, entityManager);
        credentialsCheckGui.frame.setVisible(true);
        credentialsCheckGui.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public void config() {
        emfactory = Persistence.createEntityManagerFactory("discography");
        entityManager = emfactory.createEntityManager();
    }

    public void destroy() {
        entityManager.close();
        emfactory.close();
    }
}
