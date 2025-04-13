package com.example.lab1.ui.selected_film;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab1.databinding.FragmentSelectedFilmBinding;
import com.example.lab1.ui.selected_film.SelectedFilmViewModel;

public class SelectedFilmFragment extends Fragment {
    private FragmentSelectedFilmBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SelectedFilmViewModel selectedFilmViewModel =
                new ViewModelProvider(this).get(SelectedFilmViewModel.class);

        binding = FragmentSelectedFilmBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSelectedFilm;
        selectedFilmViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}