package fr.eni.devinet.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.eni.devinet.dal.AppDatabase;
import fr.eni.devinet.dal.WordListDao;
import fr.eni.devinet.model.WordList;
import fr.eni.devinet.model.WordListWithProgress;

public class WordListDBRepository implements IWordListRepository {
    private WordListDao wordListDao;
    private LiveData<List<WordList>> wordList;

    public WordListDBRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        wordListDao = appDatabase.getWordListDao();
        wordList = wordListDao.get();
    }

    @Override
    public void insert(final WordList wordList) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                wordListDao.insert(wordList);
            }
        });
    }

    @Override
    public LiveData<List<WordList>> get() {
        return wordList;
    }

    @Override
    public LiveData<List<WordListWithProgress>> getWithProgress(int levelId){
        return wordListDao.getWithProgress(levelId);
    }

    @Override
    public void update(final WordList wordList) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                wordListDao.update(wordList);
            }
        });
    }

    @Override
    public void delete(final WordList wordList) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                wordListDao.delete(wordList);
            }
        });
    }
}
