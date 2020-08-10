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
    private LiveData<List<WordList>> wordsList;
    private MutableLiveData<WordList> observateurWordList;

    public WordListBddRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        wordListDao = appDatabase.wordListDao();
        wordsList = wordListDao.get();
        observateurWordList = new MutableLiveData<WordList>();
    }

    @Override
    public void insert(final WordList wordList) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                WordListDao.insert(wordList);
            }
        });
    }

    @Override
    public LiveData<List<WordList>> get() {
        return wordList;
    }

    @Override
    public void update(WordList wordList) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                WordListDao.update(wordList);
            }
        });
    }

    @Override
    public void delete(WordList wordList) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                WordListDao.delete(wordList);
            }
        });
    }
}
