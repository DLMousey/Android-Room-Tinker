package com.enderstudy.roomtinker.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enderstudy.roomtinker.Entity.Word;
import com.enderstudy.roomtinker.Interface.OnItemClickListener;
import com.enderstudy.roomtinker.Interface.OnItemLongClickListener;
import com.enderstudy.roomtinker.R;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> implements OnItemClickListener {

    @Override
    public void onClick(View view, int position) {
        this.onClick(view, position);
    }

    private final LayoutInflater mInflater;
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;
    private List<Word> mWords;

    public WordListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        RecyclerView.ViewHolder holder = new WordViewHolder(itemView);
        return (WordViewHolder) holder;
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        holder.wordItemView.setLongClickable(true);

        if (mWords != null) {
            Word current = mWords.get(position);
            holder.wordItemView.setText(current.getWord());
        } else {
            holder.wordItemView.setText("No Word!");
        }
    }

    public void setClickListener(OnItemClickListener callback) {
        mClickListener = callback;
    }

    public void setLongClickListener(OnItemLongClickListener callback) { mLongClickListener = callback; }

    public void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }

    public Word getWordAtPosition(int position) {
        return mWords.get(position);
    }

    /**
     * Word View Holder Inner Class
     */
    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView wordItemView;

        public WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
            wordItemView.setOnClickListener(this);
            wordItemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if (mLongClickListener != null) mLongClickListener.onLongClick(view, getAdapterPosition());
            return false;
        }
    }
}
