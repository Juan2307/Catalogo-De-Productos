package com.example.catalogodeproductos;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements MenuFragment.OnOptionClickListener {

    private View navContainer;
    private boolean isMenuExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navContainer = findViewById(R.id.nav_view_container);

        // Botón atrás
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isMenuExpanded) {
                    toggleMenu();
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
    public void onToggleMenu() {
        toggleMenu();
    }

    private void toggleMenu() {
        isMenuExpanded = !isMenuExpanded;
        
        ViewGroup.LayoutParams params = navContainer.getLayoutParams();
        // Cambiado a 180dp para que no se expanda tanto
        params.width = isMenuExpanded ? 
                (int) (180 * getResources().getDisplayMetrics().density) :
                (int) (70 * getResources().getDisplayMetrics().density);
        navContainer.setLayoutParams(params);

        MenuFragment menuFragment = (MenuFragment)
                getSupportFragmentManager().findFragmentById(R.id.menu_fragment_container);
        if (menuFragment != null) {
            menuFragment.setExpanded(isMenuExpanded);
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
        
        // El menú se queda en su estado actual (abierto o cerrado) al cambiar de módulo
    }
}
