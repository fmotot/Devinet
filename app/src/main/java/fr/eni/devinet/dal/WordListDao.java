package fr.eni.devinet.dal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.eni.devinet.model.Word;
import fr.eni.devinet.model.WordList;

@Dao
public interface WordListDao {
    @Insert
    void insert(WordList wordList);

    @Query("SELECT * FROM WordList")
    LiveData<List<WordList>> get();

    @Query("SELECT * FROM WordList WHERE name = :name AND level_id = :level_id")
    WordList get(String name, int level_id);

    @Update
    void update(WordList wordList);

    @Delete
    void delete(WordList wordList);
}
