package fr.eni.devinet.dal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.eni.devinet.model.LevelWithProgress;
import fr.eni.devinet.model.Word;
import fr.eni.devinet.model.WordList;
import fr.eni.devinet.model.WordListWithProgress;

@Dao
public interface WordListDao {
    @Insert
    void insert(WordList wordList);

    @Query("SELECT * FROM WordList")
    LiveData<List<WordList>> get();

    @Query("SELECT * FROM WordList WHERE name = :name AND level_id = :level_id")
    WordList get(String name, int level_id);

    /**
     * Récupère la liste des WordList avec la progression
     *
     * @param levelId
     * @return
     */
    @Query("SELECT WordList.*, COUNT(*) as total, t.progress " +
            "FROM WordList " +
            "INNER JOIN Word ON WordList.id = Word.list_id " +
            "LEFT JOIN (  " +
            "    SELECT WordList.id, COUNT(*) AS progress " +
            "    FROM WordList " +
            "    INNER JOIN Word ON WordList.id = Word.list_id " +
            "    WHERE Word.word = Word.proposal " +
            "    AND WordList.level_id = :levelId " +
            "    GROUP BY WordList.id " +
            ") AS t ON t.id = WordList.id " +
            "WHERE WordList.level_id = :levelId " +
            "GROUP BY WordList.id")
    LiveData<List<WordListWithProgress>> getWithProgress(int levelId);

    @Update
    void update(WordList wordList);

    @Delete
    void delete(WordList wordList);
}
