package com.enderstudy.roomtinker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewWordActivity extends AppCompatActivity {

    private static final String EXTRA_REPLY = "com.enderstudy.roomtinker.REPLY";
    private static final String EXTRA_TITLE = "com.enderstudy.roomtinker.WORD";
    private static final String EXTRA_DESCRIPTION = "com.enderstudy.roomtinker.DESCRIPTION";
    private EditText mEditWordView;
    private EditText mEditWordDescriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        mEditWordView = findViewById(R.id.edit_word);
        mEditWordDescriptionView = findViewById(R.id.edit_word_description);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditWordView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String word = mEditWordView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    replyIntent.putExtra(EXTRA_TITLE, mEditWordView.getText().toString());
                    replyIntent.putExtra(EXTRA_DESCRIPTION, mEditWordDescriptionView.getText().toString());
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    public static String getExtraReply() {
        return EXTRA_REPLY;
    }
    public static String getExtraTitle() { return EXTRA_TITLE; }
    public static String getExtraDescription() { return EXTRA_DESCRIPTION; }
}
