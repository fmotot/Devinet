package fr.eni.devinet.model;

import androidx.room.Embedded;

import org.jetbrains.annotations.NotNull;

public class WordListWithProgress {
    @Embedded
    private WordList wordList;
    private int total;
    private int progress;

    public WordListWithProgress(WordList wordList, int total, int progress) {
        this.wordList = wordList;
        this.total = total;
        this.progress = progress;
    }

    public WordList getWordList() {
        return wordList;
    }

    public void setWordList(WordList wordList) {
        this.wordList = wordList;
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

    @NotNull
    @Override
    public String toString() {
        return "WordListWithProgress{" +
                "wordList=" + wordList +
                ", total=" + total +
                ", progress=" + progress +
                '}';
    }
}
