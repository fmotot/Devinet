package fr.eni.devinet.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(foreignKeys = @ForeignKey(
        entity = Level.class,
        parentColumns = "id",
        childColumns = "level_id",
        onDelete = ForeignKey.NO_ACTION
))
public class WordList {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String name;
    @ColumnInfo(name = "level_id")
    private int levelId;

    public WordList(int id, @NotNull String name, int levelId) {
        this.id = id;
        this.name = name;
        this.levelId = levelId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    @NotNull
    @Override
    public String toString() {
        return "List{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", levelId=" + levelId +
                '}';
    }
}
