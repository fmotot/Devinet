package fr.eni.devinet.model;

import android.os.Parcel;
import android.os.Parcelable;

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
public class WordList implements Parcelable {
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

    protected WordList(Parcel in) {
        id = in.readInt();
        name = in.readString();
        levelId = in.readInt();
    }

    public static final Creator<WordList> CREATOR = new Creator<WordList>() {
        @Override
        public WordList createFromParcel(Parcel in) {
            return new WordList(in);
        }

        @Override
        public WordList[] newArray(int size) {
            return new WordList[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(levelId);
    }
}
