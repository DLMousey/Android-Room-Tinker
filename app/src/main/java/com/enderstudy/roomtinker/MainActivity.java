package com.enderstudy.roomtinker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.enderstudy.roomtinker.Adapter.WordListAdapter;
import com.enderstudy.roomtinker.Entity.Word;
import com.enderstudy.roomtinker.Interface.OnItemClickListener;
import com.enderstudy.roomtinker.Interface.OnItemLongClickListener;
import com.enderstudy.roomtinker.ViewModel.WordViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener, OnItemLongClickListener {

    private WordViewModel mWordViewModel;
    private WordListAdapter mWordAdapter;
    private RecyclerView mRecyclerView;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configureWordViewAdapter();
        configureItemTouchHelper();
        configureViewModel();
        configureFloatingActionButton();
    }

    /**
     * Set up the WordListAdapter which takes our values
     * from the Database and transforms them into data that the Recycler view can work with
     */
    public void configureWordViewAdapter() {
        final WordListAdapter adapter = new WordListAdapter(this);
        adapter.setClickListener(this);
        adapter.setLongClickListener(this);
        mWordAdapter = adapter;

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Set up an ItemTouchHelper which allows us to bind behaviour to the slide & move
     * capabilities of the recycler view
     */
    public void configureItemTouchHelper() {
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
                    Word swipedWord = mWordAdapter.getWordAtPosition(position);
                    Toast.makeText(MainActivity.this, "Deleting " + swipedWord.getWord(), Toast.LENGTH_LONG).show();

                    mWordViewModel.delete(swipedWord);
                }
            }
        );

        helper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * Create a ViewModel that's powered by Room's live data class,
     * This allows us the list view to automatically sync itself up with the data
     */
    public void configureViewModel() {
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                mWordAdapter.setWords(words);
            }
        });
    }

    /**
     * Configure the floating action button which takes us to the Create word view
     */
    public void configureFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
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

    /**
     * TODO: Remove this unused method
     * @param view
     * @param position
     */
    @Override
    public void onLongClick(View view, int position) {
        Log.d("com.enderstudy.roomtinker.handlers", "on long click fired in main activity");
    }
}
