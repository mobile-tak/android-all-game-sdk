package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabd_word_search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Created by Basit on 12/11/2016.
 */

public class Word {
    @Expose
    @SerializedName("word")
    private String word;
    @Expose
    @SerializedName("highlighted")
    private boolean highlighted;
    @Expose
    @SerializedName("fromRow")
    private int fromRow;
    @Expose
    @SerializedName("fromColumn")
    private int fromColumn;
    @Expose
    @SerializedName("toRow")
    private int toRow;
    @Expose
    @SerializedName("toColumn")
    private int toColumn;

    public Word(String word, boolean highlighted, int fromRow, int fromColumn, int toRow, int toColumn) {
        this.word = word;
        this.highlighted = highlighted;
        this.fromRow = fromRow;
        this.fromColumn = fromColumn;
        this.toRow = toRow;
        this.toColumn = toColumn;
    }

    public int getFromRow() {
        return fromRow;
    }

    public void setFromRow(int fromRow) {
        this.fromRow = fromRow;
    }

    public int getFromColumn() {
        return fromColumn;
    }

    public void setFromColumn(int fromColumn) {
        this.fromColumn = fromColumn;
    }

    public int getToRow() {
        return toRow;
    }

    public void setToRow(int toRow) {
        this.toRow = toRow;
    }

    public int getToColumn() {
        return toColumn;
    }

    public void setToColumn(int toColumn) {
        this.toColumn = toColumn;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word1 = (Word) o;
        return isHighlighted() == word1.isHighlighted() && getFromRow() == word1.getFromRow() && getFromColumn() == word1.getFromColumn() && getToRow() == word1.getToRow() && getToColumn() == word1.getToColumn() && getWord().equals(word1.getWord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWord(), isHighlighted(), getFromRow(), getFromColumn(), getToRow(), getToColumn());
    }
}
