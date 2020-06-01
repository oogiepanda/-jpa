package com.oogie.controller;

import com.oogie.BaseTest;
import com.oogie.model.SongListEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TableGenerator;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SongListServiceTest extends BaseTest {

    private SongListService songListService = new SongListService(conn);
    private static SongListServiceJPA songListServiceJPA;
    private static final String NAME1 = "Oh Sherrie";
    private static final String NAME2 = "Wheel in the Sky";
    private static EntityManager entityManager;
    private static EntityManagerFactory emfactory;

    @BeforeAll
    public static void config() {
        emfactory = Persistence.createEntityManagerFactory("discography");
        entityManager = emfactory.createEntityManager();
        songListServiceJPA = new SongListServiceJPA(entityManager);
    }

    @AfterAll
    public static void destroy() {
        entityManager.close();
        emfactory.close();
    }

    private SongListEntity createSongListEntity() {
        SongListEntity songListEntity = new SongListEntity();
        songListEntity.setSongName("Separate");
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

        assertThat(songs.size(), is(1));
        assertThat(origSong.getSongName(), is(songs.get(0).getSongName()));
        origSong.setSongName(NAME1);
        origSong.setMusician(NAME1);
        int id = songs.get(0).getId();
        update(origSong, id);
        SongListEntity updatedSong = retrieve(id);
        assertThat(updatedSong.getSongName(), is(NAME1));
        assertThat(updatedSong.getMusician(), is(NAME1));

        assertThat(origSong.getYear().intValue(), is(updatedSong.getYear().intValue()));
        assertThat(origSong.getAlbum(), is(updatedSong.getAlbum()));
        assertThat(origSong.getGenre(), is(updatedSong.getGenre()));
        delete(id);
        assertThat(retrieve(id).getId(), is(0));
    }

    @Test
    public void crudJPA() {
        SongListEntity origSong = createSongListEntity();
        int id = songListServiceJPA.create(origSong);
        SongListEntity nuSong = songListServiceJPA.retrieve(id);
        assertThat(nuSong.getSongName(), is(origSong.getSongName()));

        nuSong.setSongName(NAME2);
        songListServiceJPA.update(nuSong, id);
        SongListEntity thirdSong = songListServiceJPA.retrieve(id);
        assertThat(thirdSong.getSongName(),is(NAME2));

        songListServiceJPA.delete(id);
        SongListEntity fourthSong = songListServiceJPA.retrieve(id);
        assertThat(fourthSong, is(nullValue()));

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