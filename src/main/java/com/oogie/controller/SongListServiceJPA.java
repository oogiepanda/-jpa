package com.oogie.controller;

import com.oogie.model.SongListEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class SongListServiceJPA extends BaseServiceJPA {
    public SongListServiceJPA(EntityManager conn) {
        super(conn);
    }

    public int create(SongListEntity ce) {
        entityManager.getTransaction().begin();
        entityManager.persist(ce);
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
        entityManager.getTransaction().begin();
        List<SongListEntity> songListEntities = entityManager.createNativeQuery(
                "SELECT * FROM song_list", SongListEntity.class)
                .getResultList();
        entityManager.getTransaction().commit();
        return songListEntities;


//        Statement stmt = null;
//
//        List<SongListEntity> retval = new ArrayList<>();
//        try {
//            //stmt = conn.createStatement();
//            StringBuilder sql = new StringBuilder("select * from song_list where 1 = 1");
//            if (!StringUtils.isNullOrEmpty(ce.getSongName())) {
//                sql.append(" and song_name = '").append(ce.getSongName()).append("'");
//            }
//            if (!StringUtils.isNullOrEmpty(ce.getMusician())) {
//                sql.append(" and musician = '").append(ce.getMusician()).append("'");
//            }
//            if (ce.getYear() < 0) {
//                sql.append(" and year = ").append(ce.getYear()).append(" ");
//            }
//            if (!StringUtils.isNullOrEmpty(ce.getAlbum())) {
//                sql.append(" and album = '").append(ce.getAlbum()).append("'");
//            }
//            if (!StringUtils.isNullOrEmpty(ce.getGenre())) {
//                sql.append(" and genre = '").append(ce.getGenre()).append("'");
//            }
//            System.out.println(sql);
//            ResultSet rs = stmt.executeQuery(sql.toString());
//
//            while (rs.next()) {
//                SongListEntity songListEntity = new SongListEntity();
//                songListEntity.setId(rs.getInt("id"));
//                songListEntity.setSongName(rs.getString("song_name"));
//                songListEntity.setMusician(rs.getString("musician"));
//                songListEntity.setYear(rs.getInt("year"));
//                songListEntity.setAlbum(rs.getString("album"));
//                songListEntity.setGenre(rs.getString("genre"));
//                retval.add(songListEntity);
//            }
//            rs.close();
//            stmt.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (stmt != null)
//                    stmt.close();
//            } catch (SQLException se2) {
//            }// nothing we can do
//        }//end try
//        return retval;
    }

    public void update(SongListEntity ce, int id) {
        SongListEntity songListEntity = entityManager.find(SongListEntity.class, id);

        entityManager.getTransaction().begin();
        songListEntity.setSongName(ce.getSongName());
        songListEntity.setMusician(ce.getMusician());
        songListEntity.setYear(ce.getYear());
        songListEntity.setAlbum(ce.getAlbum());
        songListEntity.setGenre(ce.getGenre());
        entityManager.getTransaction().commit();
    }

    public void delete(int id) {
        entityManager.getTransaction().begin();
        SongListEntity songListEntity = entityManager.find(SongListEntity.class, id);
        entityManager.remove(songListEntity);
        entityManager.getTransaction().commit();
    }

}
