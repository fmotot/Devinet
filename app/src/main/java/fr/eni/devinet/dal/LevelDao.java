package fr.eni.devinet.dal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.eni.devinet.model.Level;

@Dao
public interface LevelDao {
    @Insert
    void insert(Level level);

    @Query("SELECT * FROM Level")
    LiveData<List<Level>> get();

    @Query("SELECT * FROM Level WHERE name = :name")
    Level get(String name);

    @Update
    void update(Level level);

    @Delete
    void delete(Level level);
}
