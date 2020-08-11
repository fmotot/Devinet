package fr.eni.devinet.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.eni.devinet.model.Word;
import fr.eni.devinet.model.WordList;
import fr.eni.devinet.model.WordListWithProgress;

public interface IWordListRepository {
    void insert(WordList wordList);

    LiveData<List<WordList>> get();

    LiveData<List<WordListWithProgress>> getWithProgress(int level_id);

    void update(WordList wordList);

    void delete(WordList wordList);
}
