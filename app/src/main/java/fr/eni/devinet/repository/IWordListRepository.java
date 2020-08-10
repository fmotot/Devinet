package fr.eni.devinet.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.eni.devinet.model.Word;

public interface IListRepository {
    void insert(WordList wordList);

    LiveData<List<WordListe>> get();

    void update(WordList wordList);

    void delete(WordList wordList);
}
