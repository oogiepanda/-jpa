package com.oogie.controller;

import com.oogie.BaseTest;
import com.oogie.model.SongListEntity;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SongListServiceTest extends BaseTest {

    private SongListService songListService = new SongListService(conn);
    private static SongListServiceJPA songListServiceJPA;
    private static final String NAME1 = "Oh Sherrie";
    private static EntityManager entityManager;

    @BeforeClass
    public static void config() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "discography" );
        entityManager = emfactory.createEntityManager( );
        songListServiceJPA = new SongListServiceJPA(entityManager);
    }



    private SongListEntity createSongListEntity() {
        SongListEntity songListEntity = new SongListEntity();
        songListEntity.setSongName("Faithfully");
        songListEntity.setMusician("Journey");
        songListEntity.setYear(1983);
        songListEntity.setAlbum("Revelation");
        songListEntity.setGenre("Rock");
        return songListEntity;
    }

    private SongListEntity createSongListEntity2() {
        SongListEntity songListEntity = new SongListEntity();
        songListEntity.setSongName("Oh Sherrie");
        songListEntity.setMusician("Steve Perry");
        songListEntity.setYear(1984);
        songListEntity.setAlbum("Street Talk");
        songListEntity.setGenre("Rock");
        return songListEntity;
    }


    private void create(SongListEntity songListEntity) {
        songListService.create(songListEntity);
    }

    @Test
    public void crud() {
        SongListEntity origSong = createSongListEntity();
        create(origSong);
        List<SongListEntity> songs = retrieve(origSong);

        //assertTrue(songs.size() == 1);
        assertTrue(origSong.getSongName().equalsIgnoreCase(songs.get(0).getSongName()));
        SongListEntity songListEntity2 = createSongListEntity2();
        origSong.setSongName(NAME1);
        origSong.setMusician(NAME1);
        //songListEntity.setAlbum(NAME1);
        int id = songs.get(0).getId();
        //songListEntity.setGenre(NAME1);
        update(origSong, id);
        SongListEntity updatedSong = retrieve(id);
        assertTrue(origSong.getAlbum().equals(updatedSong.getAlbum()));
        assertTrue(origSong.getYear().intValue() == updatedSong.getYear().intValue());
        assertTrue(origSong.getGenre().equals(updatedSong.getGenre()));
        assertTrue(updatedSong.getSongName().equals(NAME1));
        assertTrue(updatedSong.getMusician().equals(NAME1));
        delete(id);
        assertTrue(retrieve(id).getId() == 0);
    }


    private List<SongListEntity> retrieve(SongListEntity songListEntity) {
        return songListService.retrieve(songListEntity);
    }

    private SongListEntity retrieve(int id) {
        return songListService.retrieve(id);
    }

    public void update(SongListEntity songListEntity, int id) {
        songListService.update(songListEntity, id);
    }

    public void delete(int id) {
        songListService.delete(id);
    }
    public void delete(SongListEntity songListEntity) {
        songListService.delete(songListEntity);
    }
}