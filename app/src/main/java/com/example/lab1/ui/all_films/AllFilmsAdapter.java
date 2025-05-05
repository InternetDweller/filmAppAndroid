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
    private static ArrayList<Film> mDataset = null;
    private final AllFilmsRVInterface allFilmsRVInterface;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewTitle, mTextViewGenres, mTextViewYear;

        public ViewHolder(View v, AllFilmsRVInterface rvInterface) {
            super(v);
            mTextViewTitle = v.findViewById(R.id.recyclerview_textView_title);
            mTextViewGenres = v.findViewById(R.id.recyclerview_textView_genres);
            mTextViewYear = v.findViewById(R.id.recyclerview_textView_year);
            v.setOnClickListener(rvView -> {
                if (rvInterface != null) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        rvInterface.onRVItemClick(position, mDataset);
                    }
                }
            });
        }
    }

    public AllFilmsAdapter(ArrayList<Film> dataset, AllFilmsRVInterface rvinterface) {
        mDataset = dataset;
        allFilmsRVInterface = rvinterface;
    }

    @NonNull
    @Override
    public AllFilmsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(v, allFilmsRVInterface);
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