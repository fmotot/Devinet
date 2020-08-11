package fr.eni.devinet.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.eni.devinet.model.WordListWithProgress;
import fr.eni.devinet.repository.IWordListRepository;
import fr.eni.devinet.repository.RepoFactory;

public class WordListViewModel extends AndroidViewModel {

    private IWordListRepository wordListRepository;

    public WordListViewModel(@NonNull Application application) {
        super(application);

        wordListRepository = RepoFactory.getWordListRepository(application);
    }

    public LiveData<List<WordListWithProgress>> getWordListWithProgress(int levelId){
        return wordListRepository.getWithProgress(levelId);
    }
}
