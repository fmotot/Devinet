package fr.eni.devinet.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.eni.devinet.model.Level;

public interface ILevelRepository {
    void insert(Level level);

    LiveData<List<Level>> get();

    void update(Level level);

    void delete(Level level);
}
