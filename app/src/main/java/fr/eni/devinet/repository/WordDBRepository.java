package fr.eni.devinet.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.eni.devinet.dal.AppDatabase;
import fr.eni.devinet.dal.WordDao;
import fr.eni.devinet.model.Word;

public class WordDBRepository implements  IWordRepository{
    private WordDao wordDao;
    private LiveData<List<Word>> words;

    public WordDBRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        wordDao = appDatabase.getWordDao();
        words = wordDao.get();
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
}
