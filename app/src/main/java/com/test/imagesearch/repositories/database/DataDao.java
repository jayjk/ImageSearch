package com.test.imagesearch.repositories.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.test.imagesearch.models.MasterData;

import java.util.List;

@Dao
public interface DataDao {
 
    @Query("SELECT * FROM masterdata")
    List<MasterData> getAllData();
 
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<MasterData> data);

    @Query("SELECT * FROM masterdata WHERE id=:imgID")
    List<MasterData> getSingleData(String imgID);

    @Query("UPDATE masterdata SET user_comment=:comment WHERE id=:id")
    void updateComment(String comment,String id);
 

    
}