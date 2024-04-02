package com.example.myprojectdemo.ui;

public class ArtistModel {
    public String title;
    public String duration;
    public String albumName;
    public String albumCover;
    public int id;

    public ArtistModel(String title, String duration, String albumName, String albumCover, int id) {
        this.title = title;
        this.duration = duration;
        this.albumName = albumName;
        this.albumCover = albumCover;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(String albumCover) {
        this.albumCover = albumCover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}