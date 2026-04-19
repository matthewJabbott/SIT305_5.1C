package com.example.SIT305_51C;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface VideoDao {
    @Insert
    void addVideo(VideoItem video);

    @Query("SELECT * FROM playlist WHERE userId = :userId")
    List<VideoItem> getUserPlaylist(int userId);
}