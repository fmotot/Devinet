package fr.eni.devinet.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Collections;
import java.util.List;

import fr.eni.devinet.dal.AppDatabase;
import fr.eni.devinet.dal.LevelDao;
import fr.eni.devinet.model.Level;
import fr.eni.devinet.model.LevelWithProgress;

public class LevelDBRepository implements  ILevelRepository {
    private LevelDao levelDao;
    private LiveData<List<Level>> levels;
    private LiveData<List<LevelWithProgress>> levelsWithProgress;

    public LevelDBRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        levelDao = appDatabase.getLevelDao();
        levels = levelDao.get();
        levelsWithProgress = levelDao.getWithProgress();
    }

    @Override
    public void insert(final Level level) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                levelDao.insert(level);
            }
        });
    }

    @Override
    public LiveData<List<Level>> get() {
        return levels;
    }

    @Override
    public LiveData<List<LevelWithProgress>> getWithProgress() {
        return levelsWithProgress;
    }


    @Override
    public void update(final Level level) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                levelDao.update(level);
            }
        });
    }

    @Override
    public void delete(final Level level) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                levelDao.delete(level);
            }
        });
    }
}
