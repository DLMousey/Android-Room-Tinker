package com.enderstudy.roomtinker.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "word_table")
public class Word implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull @ColumnInfo(name = "word")
    private String mWord;

    @Nullable @ColumnInfo(name = "description")
    private String mDescription;

    public Word() { }
    public Word(String word) {
        this.mWord = word;
    }

    protected Word(Parcel in) {
        id = in.readInt();
        mWord = in.readString();
        mDescription = in.readString();
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

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

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getId());
        out.writeString(this.getWord());
        out.writeString(this.getDescription());
    }
}
