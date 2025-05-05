package com.example.lab1.ui.selected_film;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab1.R;
import com.example.lab1.databinding.FragmentSelectedFilmBinding;
import com.example.lab1.model.Film;
import com.example.lab1.ui.selected_film.SelectedFilmViewModel;
import com.squareup.picasso.Picasso;

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

        //-------------------------------------------

        Bundle args = getArguments();
        if (args != null) {
            binding.textSelectedFilm.setVisibility(View.GONE);
            binding.selectedFilmData.setVisibility(View.VISIBLE);

            Film selectedFilm = (Film)args.getSerializable("film");
            //Log.d("RECEIVED", selectedFilm.getValidName());

            Picasso.get()
              .load(selectedFilm.poster)
              .into(binding.imageViewPoster);

            binding.textViewFilmTitle.setText(selectedFilm.getValidName());
            binding.textViewGenres.setText(selectedFilm.genres);
            binding.textViewYear.setText(selectedFilm.getYearString());
            binding.textViewCountries.setText(selectedFilm.countries);
            binding.textViewDescription.setText(selectedFilm.description);

        } else {
            binding.textSelectedFilm.setVisibility(View.VISIBLE);
            binding.selectedFilmData.setVisibility(View.GONE);
        }

        /*selectedFilmViewModel.getFilm().observe(getViewLifecycleOwner(), film -> {
            Log.d("OBSERVER", film.getValidName());
        });*/

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}