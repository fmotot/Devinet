package fr.eni.devinet.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

public class LevelWithProgress {
    @Embedded
    private Level level;
    private int total;
    private int progress;

    public LevelWithProgress(Level level, int total, int progress) {
        this.level = level;
        this.total = total;
        this.progress = progress;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "LevelWithProgress{" +
                "level=" + level +
                ", total=" + total +
                ", progress=" + progress +
                '}';
    }
}
