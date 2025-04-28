package com.example.lab1.ui.all_films;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab1.databinding.FragmentAllFilmsBinding;
import com.example.lab1.model.Film;

import java.util.ArrayList;

public class AllFilmsFragment extends Fragment {
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter<AllFilmsAdapter.ViewHolder> mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;

//================================

    private FragmentAllFilmsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //AllFilmsViewModel allFilmsViewModel =
                //new ViewModelProvider(this).get(AllFilmsViewModel.class);
        AllFilmsViewModel allFilmsViewModel =
                new ViewModelProvider(requireActivity()).get(AllFilmsViewModel.class);

        binding = FragmentAllFilmsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//================================

        // ❗❗❗ LITERAL WTF BROOOO Q-Q ↓↓↓
        //View v = inflater.inflate(R.layout.fragment_all_films, container, false);
        //mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_all_films);
        mRecyclerView = binding.recyclerviewAllFilms;
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        final ArrayList<Film> recyclerViewData = new ArrayList<>();
        mAdapter = new AllFilmsAdapter(recyclerViewData);
        mRecyclerView.setAdapter(mAdapter);

        // Updating the RecyclerView on mutable data change
        allFilmsViewModel.getFilms().observe(getViewLifecycleOwner(), films -> {
            if (!films.isEmpty()) {
                if (!recyclerViewData.isEmpty()) {
                    int oldSize = recyclerViewData.size();
                    recyclerViewData.clear();
                    mAdapter.notifyItemRangeRemoved(0, oldSize - 1);
                }
                recyclerViewData.addAll(films);
                mAdapter.notifyItemRangeInserted(0, films.size() - 1);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}