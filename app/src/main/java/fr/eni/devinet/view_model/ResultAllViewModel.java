package fr.eni.devinet.view_model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import fr.eni.devinet.repository.IWordRepository;
import fr.eni.devinet.repository.RepoFactory;

public class ResultAllViewModel extends AndroidViewModel {

    private IWordRepository wordRepository;
    //private LiveData<Float> wordProgress;

    public ResultAllViewModel(@NonNull Application application) {
        super(application);

        wordRepository = RepoFactory.getWordRepository(application);
        //wordProgress = wordRepository.getAllProgress();
    }

    public LiveData<Float> get(){
        return wordRepository.getAllProgress();
    }

}
