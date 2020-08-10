package fr.eni.devinet.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;


@Entity(foreignKeys = {
        @ForeignKey(
                entity = List.class,
                parentColumns = "id",
                childColumns = "list_id",
                onDelete = ForeignKey.NO_ACTION
        )
        , @ForeignKey(
        entity = Category.class,
        parentColumns = "id",
        childColumns = "category_id",
        onDelete = ForeignKey.NO_ACTION
)})
public class Word {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String word;
    @NonNull
    private String img;
    private String proposal;
    @ColumnInfo(name = "category_id")
    private int categoryId;
    @ColumnInfo(name = "list_id")
    private int listId;

    public Word(int id, @NotNull String word, @NotNull String img, String proposal, int categoryId, int listId) {
        this.id = id;
        this.word = word;
        this.img = img;
        this.proposal = proposal;
        this.categoryId = categoryId;
        this.listId = listId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getWord() {
        return word;
    }

    public void setWord(@NotNull String word) {
        this.word = word;
    }

    @NotNull
    public String getImg() {
        return img;
    }

    public void setImg(@NotNull String img) {
        this.img = img;
    }

    public String getProposal() {
        return proposal;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    @NotNull
    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", img='" + img + '\'' +
                ", proposal='" + proposal + '\'' +
                ", category=" + categoryId +
                ", list=" + listId +
                '}';
    }
}
