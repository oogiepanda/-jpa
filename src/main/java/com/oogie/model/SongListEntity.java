package com.oogie.model;

import javax.persistence.*;

@Entity
@Table(name = "song_list", schema = "discography", catalog = "")
public class SongListEntity {
    private int id;
    private String songName;
    private String musician;
    private Integer year;
    private String album;
    private String genre;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "song_name")
    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    @Basic
    @Column(name = "musician")
    public String getMusician() {
        return musician;
    }

    public void setMusician(String musician) {
        this.musician = musician;
    }

    @Basic
    @Column(name = "year")
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Basic
    @Column(name = "album")
    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Basic
    @Column(name = "genre")
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SongListEntity that = (SongListEntity) o;

        if (id != that.id) return false;
        if (songName != null ? !songName.equals(that.songName) : that.songName != null) return false;
        if (musician != null ? !musician.equals(that.musician) : that.musician != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        if (album != null ? !album.equals(that.album) : that.album != null) return false;
        if (genre != null ? !genre.equals(that.genre) : that.genre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (songName != null ? songName.hashCode() : 0);
        result = 31 * result + (musician != null ? musician.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (album != null ? album.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        return result;
    }
}
