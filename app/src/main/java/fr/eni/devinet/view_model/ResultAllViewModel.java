package fr.eni.devinet.view_model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import fr.eni.devinet.repository.IWordRepository;
import fr.eni.devinet.repository.RepoFactory;

public class ResultAllViewModel extends AndroidViewModel {

    private IWordRepository wordRepository;
    private float wordProgress;

    public ResultAllViewModel(@NonNull Application application) {
        super(application);

        wordRepository = RepoFactory.getWordRepository(application);
        wordProgress = wordRepository.getAllProgress();
    }

    public float get(){
        return wordRepository.getAllProgress();
    }

}
