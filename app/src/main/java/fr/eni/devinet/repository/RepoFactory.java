package fr.eni.devinet.repository;

import android.app.Application;
import android.content.Context;

public class RepoFactory {

    public static ICategoryRepository getCategoryRepository(Context context){
        return new CategoryDBRepository(context);
    }

    public static ILevelRepository getLevelRepository(Context context){
        return new LevelDBRepository(context);
    }

    public  static IWordListRepository getWordListRepository(Context context){
        return new WordListDBRepository(context);
    }

    public static IWordRepository getWordRepository(Context context){
        return new WordDBRepository(context);
    }
}
