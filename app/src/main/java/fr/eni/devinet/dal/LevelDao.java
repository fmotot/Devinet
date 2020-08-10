package fr.eni.devinet.dal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.eni.devinet.model.Level;
import fr.eni.devinet.model.LevelWithProgress;

@Dao
public interface LevelDao {
    @Insert
    void insert(Level level);

    @Query("SELECT * FROM Level")
    LiveData<List<Level>> get();

    @Query("SELECT * FROM Level WHERE name = :name")
    Level get(String name);

    /**
     * Récupère la liste des niveaux avec la progression
     */
    @Query("SELECT Level.*, COUNT(*) as total, t.progress " +
            "FROM Level " +
            "INNER JOIN WordList ON Level.id = WordList.level_id " +
            "INNER JOIN Word ON WordList.id = Word.list_id " +
            "LEFT JOIN (  " +
            "    SELECT Level.id, COUNT(*) AS progress " +
            "    FROM Level " +
            "    INNER JOIN WordList ON Level.id = WordList.level_id " +
            "    INNER JOIN Word ON WordList.id = Word.list_id " +
            "    WHERE word.word = Word.proposal " +
            "    GROUP BY Level.id " +
            ") AS t ON t.id = Level.id " +
            "GROUP BY Level.id")
    LiveData<List<LevelWithProgress>> getWithProgress();

    @Update
    void update(Level level);

    @Delete
    void delete(Level level);
}
