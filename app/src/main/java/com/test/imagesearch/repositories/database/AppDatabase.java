package com.test.imagesearch.repositories.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.test.imagesearch.models.MasterData;

@Database(entities = {MasterData.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract DataDao dataDao();
}