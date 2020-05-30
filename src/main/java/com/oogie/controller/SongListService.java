package com.oogie.controller;

import com.mysql.cj.util.StringUtils;
import com.oogie.model.SongListEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SongListService extends BaseService {
    public SongListService(Connection conn) {
        super(conn);
    }

    public void create(SongListEntity ce) {
        Statement stmt = null;
        List<SongListEntity> retval = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            String sql = "INSERT INTO song_list (song_name, musician, year, album, genre) VALUES ('"
                    + ce.getSongName() + "', '"
                    + ce.getMusician() + "', "
                    + ce.getYear() + ", '"
                    + ce.getAlbum() + "', '"
                    + ce.getGenre() + "')";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
        }//end try
    }

    public SongListEntity retrieve(int id) {
        Statement stmt = null;
        SongListEntity songListEntity = new SongListEntity();
        try {
            stmt = conn.createStatement();
            if (id > 0) {
                StringBuilder sql = new StringBuilder("select * from song_list where id = ");
                sql.append(id);
                System.out.println(sql);
                ResultSet rs = stmt.executeQuery(sql.toString());

                while (rs.next()) {
                    songListEntity.setId(rs.getInt("id"));
                    songListEntity.setSongName(rs.getString("song_name"));
                    songListEntity.setMusician(rs.getString("musician"));
                    songListEntity.setYear(rs.getInt("year"));
                    songListEntity.setAlbum(rs.getString("album"));
                    songListEntity.setGenre(rs.getString("genre"));
                }
                rs.close();
                stmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
        }//end try
        return songListEntity;
    }

    public List<SongListEntity> retrieve(SongListEntity ce) {
        Statement stmt = null;
        List<SongListEntity> retval = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            StringBuilder sql = new StringBuilder("select * from song_list where 1 = 1");
            if (!StringUtils.isNullOrEmpty(ce.getSongName())) {
                sql.append(" and song_name = '").append(ce.getSongName()).append("'");
            }
            if (!StringUtils.isNullOrEmpty(ce.getMusician())) {
                sql.append(" and musician = '").append(ce.getMusician()).append("'");
            }
            if (ce.getYear() < 0) {
                sql.append(" and year = ").append(ce.getYear()).append(" ");
            }
            if (!StringUtils.isNullOrEmpty(ce.getAlbum())) {
                sql.append(" and album = '").append(ce.getAlbum()).append("'");
            }
            if (!StringUtils.isNullOrEmpty(ce.getGenre())) {
                sql.append(" and genre = '").append(ce.getGenre()).append("'");
            }
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql.toString());

            while (rs.next()) {
                SongListEntity songListEntity = new SongListEntity();
                songListEntity.setId(rs.getInt("id"));
                songListEntity.setSongName(rs.getString("song_name"));
                songListEntity.setMusician(rs.getString("musician"));
                songListEntity.setYear(rs.getInt("year"));
                songListEntity.setAlbum(rs.getString("album"));
                songListEntity.setGenre(rs.getString("genre"));
                retval.add(songListEntity);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
        }//end try
        return retval;
    }

    public void update(SongListEntity ce, int id) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            StringBuilder sql = new StringBuilder("update song_list set");
            if (!StringUtils.isNullOrEmpty(ce.getSongName())) {
                sql.append(" song_name ='").append(ce.getSongName()).append("',");
            }
            if (!StringUtils.isNullOrEmpty(ce.getMusician())) {
                sql.append(" musician = '").append(ce.getMusician()).append("',");
            }
            if (ce.getYear() < 0) {
                sql.append(" year = ").append(ce.getYear()).append(",");
            }
            if (!StringUtils.isNullOrEmpty(ce.getAlbum())) {
                sql.append(" album = '").append(ce.getAlbum()).append("',");
            }
            if (!StringUtils.isNullOrEmpty(ce.getGenre())) {
                sql.append(" genre = '").append(ce.getGenre()).append("',");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(" where id = ").append(id);
            System.out.println(sql);
            stmt.executeUpdate(sql.toString());
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
        }//end try
    }

    public void delete(int id) {
        if (id < 0) {
            return;
        }
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "delete from song_list where id = " + id;
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
        }//end try
    }

    public void delete(SongListEntity ce) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "delete from song_list where song_name = '" + ce.getSongName() + "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
        }//end try
    }

}
