package fr.eni.devinet.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.eni.devinet.model.Word;
import fr.eni.devinet.model.WordList;

public interface IWordListRepository {
    void insert(WordList wordList);

    LiveData<List<WordList>> get();

    void update(WordList wordList);

    void delete(WordList wordList);
}
