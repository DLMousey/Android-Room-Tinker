package com.enderstudy.roomtinker.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull @ColumnInfo(name = "word")
    private String mWord;

    @Nullable @ColumnInfo(name = "description")
    private String mDescription;

    public Word(String word) {
        this.mWord = word;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setWord(String word) {
        this.mWord = word;
    }

    public String getWord() {
        return this.mWord;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getDescription() {
        return this.mDescription;
    }
}
