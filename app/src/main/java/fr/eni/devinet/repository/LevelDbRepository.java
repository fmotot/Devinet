package fr.eni.devinet.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.eni.devinet.dal.AppDatabase;
import fr.eni.devinet.dal.LevelDao;
import fr.eni.devinet.model.Level;

public class LevelDbRepository implements  ILevelRepository {
    private LevelDao levelDao;
    private LiveData<List<Level>> levels;

    public LevelDbRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        levelDao = appDatabase.getLevelDao();
        levels = levelDao.get();
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
