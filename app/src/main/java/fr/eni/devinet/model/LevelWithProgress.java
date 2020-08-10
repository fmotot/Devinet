package fr.eni.devinet.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

public class LevelWithProgress {
    @Embedded Level level;

    String progress;
}
