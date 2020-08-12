package fr.eni.devinet.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.eni.devinet.model.Word;

public interface IWordRepository {
    void insert(Word word);

    LiveData<List<Word>> get();

    void update(Word word);

    void delete(Word word);

    LiveData<List<Word>> getFromList(int wordListId);

    float getAllProgress();
}
