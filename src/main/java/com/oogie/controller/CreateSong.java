package com.oogie.controller;

import com.oogie.model.SongListEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CreateSong {

    public static void main( String[ ] args ) {

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "discography" );

        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );

        SongListEntity songListEntity = new SongListEntity();
        songListEntity.setSongName("Hello");
        songListEntity.setMusician("Journey");
        songListEntity.setYear(1985);
        songListEntity.setAlbum("Revelation");
        songListEntity.setGenre("Rock");

        entitymanager.persist(songListEntity);
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();
    }
}