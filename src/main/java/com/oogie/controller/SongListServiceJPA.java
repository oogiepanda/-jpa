package com.oogie.controller;

import com.oogie.model.SongListEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

import static com.mysql.cj.util.StringUtils.isNullOrEmpty;


public class SongListServiceJPA extends BaseServiceJPA {
    public SongListServiceJPA(EntityManager entityManager) {
        super(entityManager);
    }


    private SongListEntity clone(SongListEntity song) {
        SongListEntity clone = new SongListEntity();
        clone.setSongName(song.getSongName());
        clone.setMusician(song.getMusician());
        clone.setYear(song.getYear());
        clone.setAlbum(song.getAlbum());
        clone.setGenre(song.getGenre());
        return clone;
    }

    public int create(SongListEntity ce) {
        entityManager.getTransaction().begin();
        SongListEntity song = clone(ce);
        entityManager.persist(song);
        Query query = entityManager.createNativeQuery("select max(id) from song_list;");
        int val = (int) query.getSingleResult();
        entityManager.getTransaction().commit();
        return val;
    }

    public SongListEntity retrieve(int id) {
        entityManager.getTransaction().begin();
        SongListEntity songListEntity = entityManager.find(SongListEntity.class, id);
        entityManager.getTransaction().commit();
        return songListEntity;
    }

    public List<SongListEntity> retrieve(SongListEntity ce) {
        if (isEmpty(ce)) {
            return Collections.emptyList();
        }
        StringBuilder sql = new StringBuilder("select s from SongListEntity s where 1 = 1"); //Note: the statement is HQL, not SQL
        if (!isNullOrEmpty(ce.getSongName())) {
            sql.append(" and song_name = '").append(ce.getSongName()).append("'");
        }
        if (!isNullOrEmpty(ce.getMusician())) {
            sql.append(" and musician = '").append(ce.getMusician()).append("'");
        }
        if (ce.getYear() != null) {
            sql.append(" and year = ").append(ce.getYear()).append(" ");
        }
        if (!isNullOrEmpty(ce.getAlbum())) {
            sql.append(" and album = '").append(ce.getAlbum()).append("'");
        }
        if (!isNullOrEmpty(ce.getGenre())) {
            sql.append(" and genre = '").append(ce.getGenre()).append("'");
        }
        System.out.println(sql);
        TypedQuery<SongListEntity> query = entityManager.createQuery(sql.toString(), SongListEntity.class);
        List<SongListEntity> songs = query.getResultList();
        return songs;
    }

    private boolean isEmpty(SongListEntity song) {
        return (isNullOrEmpty(song.getSongName()) &&
                isNullOrEmpty(song.getMusician()) &&
                song.getYear() == null &&
                isNullOrEmpty(song.getAlbum()) &&
                isNullOrEmpty(song.getGenre()));
    }

    public void update(SongListEntity ce, int id) {
        SongListEntity songListEntity = entityManager.find(SongListEntity.class, id);
        entityManager.getTransaction().begin();
        if (ce.getSongName() != null && !ce.getSongName().isEmpty()) {
            songListEntity.setSongName(ce.getSongName());
        }
        if (ce.getMusician() != null && !ce.getMusician().isEmpty()) {
            songListEntity.setMusician(ce.getMusician());
        }
        if (ce.getYear() != null) {
            songListEntity.setYear(ce.getYear());
        }
        if (ce.getAlbum() != null && !ce.getAlbum().isEmpty()) {
            songListEntity.setAlbum(ce.getAlbum());
        }
        if (ce.getGenre() != null && !ce.getGenre().isEmpty()) {
            songListEntity.setGenre(ce.getGenre());
        }
        entityManager.getTransaction().commit();
    }

    public void delete(int id) {
        entityManager.getTransaction().begin();
        SongListEntity songListEntity = entityManager.find(SongListEntity.class, id);
        entityManager.remove(songListEntity);
        entityManager.getTransaction().commit();
    }
}
