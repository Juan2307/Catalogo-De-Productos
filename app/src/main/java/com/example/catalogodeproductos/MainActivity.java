package com.example.catalogodeproductos;

import android.os.Bundle;
import android.widget.Toast; //

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements MenuFragment.OnOptionClickListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //
        Toast.makeText(this, "Entró a MainActivity", Toast.LENGTH_LONG).show();

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Botón atrás
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                    setEnabled(true);
                }
            }
        });

        // Cargar perfil por defecto
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

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_fragment_container, fragment)
                .commit();

        MenuFragment menuFragment = (MenuFragment)
                getSupportFragmentManager().findFragmentById(R.id.menu_fragment_container);

        if (menuFragment != null) {
            menuFragment.highlightOption(option);
        }

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}