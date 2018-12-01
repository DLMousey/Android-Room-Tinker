package com.enderstudy.roomtinker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.enderstudy.roomtinker.Entity.Word;

public class DetailActivity extends AppCompatActivity {

    private Word mWord;
    private TextView mWordTitle;
    private TextView mWordDescription;

    public DetailActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mWordTitle = (TextView) findViewById(R.id.word_field);
        mWordDescription = (TextView) findViewById(R.id.word_description);

        mWord = getIntent().getParcelableExtra("word_data");
        mWordTitle.setText(mWord.getWord());
        mWordDescription.setText(mWord.getDescription());
    }

    public void setWord(Word word) {
        this.mWord = word;
    }

    public Word getWord() {
        return this.mWord;
    }
}
