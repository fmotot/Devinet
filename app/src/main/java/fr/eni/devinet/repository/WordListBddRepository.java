package fr.eni.devinet.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.eni.devinet.dal.AppDatabase;
import fr.eni.devinet.dal.WordDao;
import fr.eni.devinet.dal.WordListDao;
import fr.eni.devinet.model.Word;
import fr.eni.devinet.model.WordList;

public class WordListBddRepository implements IWordListRepository {
    private WordListDao wordListDao;
    private LiveData<List<WordList>> wordList;
    private MutableLiveData<WordList> observateurWordList;

    public WordListBddRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        wordListDao = appDatabase.wordListDao();
        wordList = wordListDao.get();
        observateurWordList = new MutableLiveData<WordList>();
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
