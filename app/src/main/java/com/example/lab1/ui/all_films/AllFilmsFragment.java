package com.example.lab1.ui.all_films;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab1.databinding.FragmentAllFilmsBinding;

public class AllFilmsFragment extends Fragment {

    private FragmentAllFilmsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AllFilmsViewModel allFilmsViewModel =
                new ViewModelProvider(this).get(AllFilmsViewModel.class);

        binding = FragmentAllFilmsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAllFilms;
        allFilmsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}