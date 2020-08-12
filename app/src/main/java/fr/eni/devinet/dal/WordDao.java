package fr.eni.devinet.dal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.eni.devinet.model.Word;

@Dao
public interface WordDao {
    @Insert
    void insert(Word word);

    @Query("SELECT * FROM Word")
    LiveData<List<Word>> get();

    @Update
    void update(Word word);

    @Delete
    void delete(Word word);

    @Query("SELECT * FROM Word WHERE Word.list_id = :wordListId")
    LiveData<List<Word>> getFromList(int wordListId);

    @Query("SELECT ROUND((count(*)*100)/(SELECT count(*) FROM word)) as percentage FROM word WHERE proposal != \"\"")
    LiveData<Float> getAllProgress();
}
