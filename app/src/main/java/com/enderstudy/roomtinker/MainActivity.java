package com.enderstudy.roomtinker;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import com.enderstudy.roomtinker.Adapter.WordListAdapter;
import com.enderstudy.roomtinker.Dao.WordDao;
import com.enderstudy.roomtinker.Entity.Word;
import com.enderstudy.roomtinker.Interface.OnItemClickListener;
import com.enderstudy.roomtinker.Interface.OnItemLongClickListener;
import com.enderstudy.roomtinker.ViewModel.WordViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener, OnItemLongClickListener {

    private WordViewModel mWordViewModel;
    private WordListAdapter mWordAdapter;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(this);

        adapter.setClickListener(this);
        adapter.setLongClickListener(this);
        mWordAdapter = adapter;

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                adapter.setWords(words);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
            new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView,
                                      RecyclerView.ViewHolder viewHolder,
                                      RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    Word swipedWord = adapter.getWordAtPosition(position);
                    Toast.makeText(MainActivity.this, "Deleting " + swipedWord.getWord(), Toast.LENGTH_LONG).show();

                    mWordViewModel.delete(swipedWord);
                }
            }
        );

        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word();
            word.setWord(data.getStringExtra(NewWordActivity.getExtraTitle()));
            word.setDescription(data.getStringExtra(NewWordActivity.getExtraDescription()));

            mWordViewModel.insert(word);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, int position) {
        Word clickedWord = mWordAdapter.getWordAtPosition(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("word_data", clickedWord);

        Log.d("com.enderstudy.roomtinker.handlers", "on click fired in main activity");
        Log.d("com.enderstudy.roomtinker.handlers", clickedWord.getWord());

        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {
        Log.d("com.enderstudy.roomtinker.handlers", "on long click fired in main activity");
    }
}
