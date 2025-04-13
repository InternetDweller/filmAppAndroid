package com.example.lab1;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Console;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // PUT THIS AFTER SHOWING THE ACTIVITY LOL
        // https://stackoverflow.com/a/47994067
        Button Button_Login = findViewById(R.id.Button_Login);
        EditText Input_Username = findViewById(R.id.Input_Username),
                 Input_Password = findViewById(R.id.Input_Password);

        Button_Login.setOnClickListener(
                // Lambda expression
                // replaces new View.OnClickListener()
                // that overrides onClick(View v)
                v -> {
                    TypedArray logindata = getResources().obtainTypedArray(R.array.logindata);
                    boolean loginDataMatches = false;

                    for (int i = 0; i < logindata.length(); i++) {
                        String[] strs = logindata.getString(i).split(",");
                        if (Input_Username.getText().toString().equals(strs[0]) && Input_Password.getText().toString().equals(strs[1])) {
                            loginDataMatches = true;
                            switchActivities();
                        }
                    }

                    if (!loginDataMatches) { Toast.makeText(MainActivity.this, "Неверные данные", Toast.LENGTH_SHORT).show(); }
                    logindata.recycle();
                });
    };

    private void switchActivities() {
        Intent Intent_SwitchActivity = new Intent(this, Activity_Films.class);
        startActivity(Intent_SwitchActivity);
    }

    /*public void clicklogin(View view) {
        System.out.println("Button clicked");
        Toast.makeText(this, "aaaa", Toast.LENGTH_SHORT).show();
    }*/
}