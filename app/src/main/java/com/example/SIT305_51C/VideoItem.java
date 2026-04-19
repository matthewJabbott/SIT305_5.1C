package com.example.SIT305_51C;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


// Match the tableName to the VideoDao query ("SELECT * FROM playlist...")
@Entity(tableName = "playlist")
public class VideoItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int userId;
    public String videoUrl;
    public String title;

    // Room can use THIS constructor if the names match the fields
    public VideoItem(int userId, String videoUrl, String title) {
        this.userId = userId;
        this.videoUrl = videoUrl;
        this.title = title;
    }
}