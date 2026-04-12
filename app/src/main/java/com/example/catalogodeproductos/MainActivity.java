package com.example.catalogodeproductos;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements MenuFragment.OnOptionClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        // Cargar el menú (sidebar fijo)
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_fragment_container, new MenuFragment())
                .commit();

        // Fragment inicial
        if (savedInstanceState == null) {
            onOptionClicked("profile");
        }
    }

    @Override
    public void onOptionClicked(String option) {

        Fragment fragment;

        switch (option) {
            case "photos":
                fragment = new PhotosFragment();
                break;
            case "video":
                fragment = new VideoFragment();
                break;
            case "web":
                fragment = new WebFragment();
                break;
            case "buttons":
                fragment = new ButtonsFragment();
                break;
            default:
                fragment = new ProfileFragment();
                break;
        }

        //
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_fragment_container, fragment)
                .commit();

        // Resaltar opción seleccionada
        MenuFragment menuFragment = (MenuFragment)
                getSupportFragmentManager().findFragmentById(R.id.menu_fragment_container);

        if (menuFragment != null) {
            menuFragment.highlightOption(option);
        }
    }
}