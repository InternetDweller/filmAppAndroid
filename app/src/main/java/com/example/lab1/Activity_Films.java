package com.example.lab1;

import android.os.Bundle;

import com.example.lab1.db.DBHelper;
import com.example.lab1.model.Film;
import com.example.lab1.ui.all_films.AllFilmsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lab1.databinding.ActivityFilmsBinding;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Activity_Films extends AppCompatActivity {

    private ActivityFilmsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Fix white top bar icons (sets up flags)
        EdgeToEdge.enable(this);

        binding = ActivityFilmsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Fix elements rendering under the top android bar
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        //=========================================================
        AllFilmsViewModel allfilmsviewmodel = new ViewModelProvider(this).get(AllFilmsViewModel.class);

        DBHelper dbhelper = new DBHelper(this);
        dbhelper.loadFilmsIntoDb(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                ArrayList<Film> tmpFilms = dbhelper.getShortInfo();
                allfilmsviewmodel.postFilmsData(tmpFilms);
            }
        });
        //=========================================================

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        // (commented because unused ↓)
        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_all_films, R.id.navigation_selected_film)
                .build();*/
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_films);
        // Literally why does this exist ↓ ;-;
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}