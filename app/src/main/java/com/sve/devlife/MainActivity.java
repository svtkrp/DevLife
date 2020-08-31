package com.sve.devlife;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sve.devlife.model.Meme;
import com.sve.devlife.model.Memes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

import static com.sve.devlife.CurrentPreferences.setStoredMemes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        setStoredMemes(MainActivity.this, new Memes(new ArrayList<Meme>()));
    }

}