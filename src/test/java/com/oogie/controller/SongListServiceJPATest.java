package com.oogie.controller;

import com.oogie.model.SongListEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SongListServiceJPATest{

    public SongListEntity createSongListEntity() {
        SongListEntity songListEntity = new SongListEntity();
        songListEntity.setSongName("Faithfully");
        songListEntity.setMusician("Journey");
        songListEntity.setYear(1984);
        songListEntity.setAlbum("Revelation");
        songListEntity.setGenre("Rock");
        return songListEntity;
    }

    @Test
    void create() {
        SongListEntity songListEntity = createSongListEntity();

    }

    @Test
    void retrieve() {
    }

    @Test
    void testRetrieve() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void testDelete() {
    }
}