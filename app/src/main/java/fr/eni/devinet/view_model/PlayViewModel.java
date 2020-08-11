package fr.eni.devinet.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.eni.devinet.model.Word;
import fr.eni.devinet.repository.IWordRepository;
import fr.eni.devinet.repository.RepoFactory;

public class PlayViewModel extends AndroidViewModel {

    private IWordRepository wordRepository;

    public PlayViewModel(@NonNull Application application) {
        super(application);

        wordRepository = RepoFactory.getWordRepository(application);
    }

    public LiveData<List<Word>> getWordsFromWordList(int wordListId){
        return wordRepository.getFromList(wordListId);
    }
}
