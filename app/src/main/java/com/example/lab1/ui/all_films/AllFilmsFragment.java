package com.example.lab1.ui.all_films;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab1.FilmDataViewModel;
import com.example.lab1.R;
import com.example.lab1.databinding.FragmentAllFilmsBinding;
import com.example.lab1.model.Film;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AllFilmsFragment extends Fragment implements AllFilmsRVInterface {
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter<AllFilmsAdapter.ViewHolder> mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;

//================================

    private FragmentAllFilmsBinding binding;

    private FilmDataViewModel filmDataViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //AllFilmsViewModel allFilmsViewModel =
                //new ViewModelProvider(this).get(AllFilmsViewModel.class);
        AllFilmsViewModel allFilmsViewModel =
                new ViewModelProvider(requireActivity()).get(AllFilmsViewModel.class);
        filmDataViewModel = new ViewModelProvider(requireActivity()).get(FilmDataViewModel.class);

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
        mAdapter = new AllFilmsAdapter(recyclerViewData, this); //because this implements the interface
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

        FloatingActionButton fab = binding.fab;
        fab.setVisibility(GONE);

        fab.setOnClickListener(v -> {
            mRecyclerView.smoothScrollToPosition(0);
            fab.setVisibility(GONE);
        });

        LinearLayoutManager llm = (LinearLayoutManager)mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fab.setVisibility(VISIBLE);
                } else {
                    if (llm != null && llm.findFirstCompletelyVisibleItemPosition() == 0) {
                        fab.setVisibility(GONE);
                    }
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

    @Override
    public void onRVItemClick(int itemPos, ArrayList<Film> data) {
        /*Bundle bundle = new Bundle();
        bundle.putSerializable("film", data.get(itemPos));

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_films);
        navController.navigate(
                R.id.navigation_selected_film,
                bundle,
                // Sync the bottom ribbon ↓
                new NavOptions.Builder().setPopUpTo(R.id.mobile_navigation, true).build()
        );*/

        filmDataViewModel.setSelectedFilm(data.get(itemPos));
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_films);
        navController.navigate(
                R.id.navigation_selected_film,
                null,
                // Sync the bottom ribbon ↓
                new NavOptions.Builder().setPopUpTo(R.id.mobile_navigation, true).build()
        );
    }
}