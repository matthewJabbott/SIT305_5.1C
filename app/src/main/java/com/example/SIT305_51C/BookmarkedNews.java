package com.example.SIT305_51C;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_bookmarks")
public class BookmarkedNews {
    // This line solves 'Cannot resolve symbol id'
    @PrimaryKey(autoGenerate = true)
    public int id;

    // This line solves 'Cannot resolve symbol userId'
    public int userId;

    public String title;
    public String description;
    public String source;
    public int imageResId;

    public BookmarkedNews(int userId, String title, String description, String source, int imageResId) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.source = source;
        this.imageResId = imageResId;
    }
}