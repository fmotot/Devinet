package fr.eni.devinet.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.eni.devinet.dal.AppDatabase;
import fr.eni.devinet.dal.WordDao;
import fr.eni.devinet.model.Word;

public class WordDBRepository implements  IWordRepository{
    private WordDao wordDao;
    private LiveData<List<Word>> words;
    private LiveData<Float> allProgress;

    public WordDBRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        wordDao = appDatabase.getWordDao();
        words = wordDao.get();
        allProgress = wordDao.getAllProgress();
    }

    @Override
    public void insert(final Word word) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                wordDao.insert(word);
            }
        });
    }

    @Override
    public LiveData<List<Word>> get() {
        return words;
    }

    @Override
    public void update(final Word word) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                wordDao.update(word);
            }
        });
    }

    @Override
    public void delete(final Word word) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                wordDao.delete(word);
            }
        });
    }

    @Override
    public LiveData<List<Word>> getFromList(int wordListId) {

        return wordDao.getFromList(wordListId);
    }

    @Override
    public LiveData<Float> getAllProgress() {
        return allProgress;
    }
}
