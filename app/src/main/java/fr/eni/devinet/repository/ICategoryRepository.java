package fr.eni.devinet.repository;

import androidx.lifecycle.LiveData;

import fr.eni.devinet.model.Category;


public interface ICategoryRepository {
    void insert(Category category);

    LiveData<Category> get();

    void update(Category category);

    void delete(Category category);
}
