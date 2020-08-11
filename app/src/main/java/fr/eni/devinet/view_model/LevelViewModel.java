package fr.eni.devinet.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.Collections;
import java.util.List;

import fr.eni.devinet.model.LevelWithProgress;
import fr.eni.devinet.repository.ILevelRepository;
import fr.eni.devinet.repository.RepoFactory;

public class LevelViewModel extends AndroidViewModel {

    private ILevelRepository levelRepository;

    private LiveData<List<LevelWithProgress>> levels;

    public LevelViewModel(@NonNull Application application) {
        super(application);

        levelRepository = RepoFactory.getLevelRepository(application);
        levels = levelRepository.getWithProgress();
    }

    public LiveData<List<LevelWithProgress>> get(){
        return levelRepository.getWithProgress();
    }
}
