package com.oogie.controller;

import com.oogie.model.SongListEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CreateSong {

    public static void main( String[ ] args ) {

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "NewPersistenceUnit" );

        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );

        SongListEntity song = new SongListEntity( );
        song.setAlbum("High & Dry");
        song.setGenre("Rock");
        song.setMusician("Def Leppard");
        song.setSongName("High");
        song.setYear(1983);

        entitymanager.persist( song );
        entitymanager.getTransaction( ).commit( );

        entitymanager.close( );
        emfactory.close( );
    }
}