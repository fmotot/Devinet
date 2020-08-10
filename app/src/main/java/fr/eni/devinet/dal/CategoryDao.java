package fr.eni.devinet.dal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import fr.eni.devinet.model.Category;

@Dao
public interface CategoryDao {
    @Insert
    void insert(Category category);

    @Query("SELECT * FROM Category")
    LiveData<Category> get();

    @Query("SELECT * FROM Category WHERE name = :name")
    Category get(String name);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);
}
