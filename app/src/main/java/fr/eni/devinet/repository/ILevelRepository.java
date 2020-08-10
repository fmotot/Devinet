package fr.eni.devinet.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.eni.devinet.model.Level;
import fr.eni.devinet.model.LevelWithProgress;

public interface ILevelRepository {
    void insert(Level level);

    LiveData<List<Level>> get();

    LiveData<List<LevelWithProgress>> getWithProgress();

    void update(Level level);

    void delete(Level level);
}
