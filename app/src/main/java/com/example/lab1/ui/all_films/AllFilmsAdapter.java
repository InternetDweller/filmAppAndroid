package com.example.lab1.ui.all_films;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab1.R;
import com.example.lab1.model.Film;

import java.util.ArrayList;

public class AllFilmsAdapter extends RecyclerView.Adapter<AllFilmsAdapter.ViewHolder> {
    private final ArrayList<Film> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewTitle, mTextViewGenres, mTextViewYear;

        public ViewHolder(View v) {
            super(v);
            mTextViewTitle = v.findViewById(R.id.recyclerview_textView_title);
            mTextViewGenres = v.findViewById(R.id.recyclerview_textView_genres);
            mTextViewYear = v.findViewById(R.id.recyclerview_textView_year);
        }
    }

    public AllFilmsAdapter(ArrayList<Film> dataset) {
        mDataset = dataset;
    }

    @NonNull
    @Override
    public AllFilmsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewholder, int elementId) {
        viewholder.mTextViewTitle.setText(mDataset.get(elementId).getValidName());
        viewholder.mTextViewGenres.setText(mDataset.get(elementId).genres);
        viewholder.mTextViewYear.setText(mDataset.get(elementId).getYearString());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}