package com.example.lab1.ui.all_films;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab1.api.FilmsAPI;
import com.example.lab1.api.JsonParser;
import com.example.lab1.databinding.FragmentAllFilmsBinding;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

        FilmsAPI.Requests.getTopFilms(getContext(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    System.out.println("response here");
                    ArrayList<JsonParser.Film> filmsParsed = JsonParser.parseShortInfo(response.body().string());
                    System.out.println(filmsParsed.get(0).name);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
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