package com.example.SIT305_51C;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface BookmarkDao {
    @Insert
    void bookmarkStory(BookmarkedNews story);

    @Query("SELECT * FROM news_bookmarks WHERE userId = :userId")
    List<BookmarkedNews> getUserBookmarks(int userId);

    @Query("DELETE FROM news_bookmarks WHERE id = :bookmarkId")
    void deleteBookmark(int bookmarkId);
}