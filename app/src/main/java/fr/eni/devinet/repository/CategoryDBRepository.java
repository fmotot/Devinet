package fr.eni.devinet.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import fr.eni.devinet.dal.AppDatabase;
import fr.eni.devinet.dal.CategoryDao;
import fr.eni.devinet.model.Category;

public class CategoryDBRepository implements ICategoryRepository {

    private final CategoryDao categoryDao;

    public CategoryDBRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        categoryDao = db.getCategoryDao();
    }

    @Override
    public void insert(final Category category) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                categoryDao.insert(category);
            }
        });
    }

    @Override
    public LiveData<Category> get() {
        return null;
    }

    @Override
    public void update(final Category category) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                categoryDao.update(category);
            }
        });
    }

    @Override
    public void delete(final Category category) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                categoryDao.delete(category);
            }
        });
    }
}
