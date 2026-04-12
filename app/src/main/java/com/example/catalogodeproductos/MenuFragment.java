package com.example.catalogodeproductos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MenuFragment extends Fragment {

    // INTERFAZ
    public interface OnOptionClickListener {
        void onOptionClicked(String option);
    }

    private OnOptionClickListener listener;

    private boolean isExpanded = false;

    LinearLayout rootMenu;
    ImageView btnToggle;

    TextView textProfile, textPhotos, textVideo, textWeb, textButtons;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);


        if (getActivity() instanceof OnOptionClickListener) {
            listener = (OnOptionClickListener) getActivity();
        }

        rootMenu = view.findViewById(R.id.root_menu);
        btnToggle = view.findViewById(R.id.btn_toggle_menu);

        textProfile = view.findViewById(R.id.text_profile);
        textPhotos = view.findViewById(R.id.text_photos);
        textVideo = view.findViewById(R.id.text_video);
        textWeb = view.findViewById(R.id.text_web);
        textButtons = view.findViewById(R.id.text_buttons);

        // Ocultar textos al inicio
        setTextVisibility(false);

        btnToggle.setOnClickListener(v -> toggleMenu());

        // Clicks del menú
        view.findViewById(R.id.btn_profile).setOnClickListener(v -> sendOption("profile"));
        view.findViewById(R.id.btn_photos).setOnClickListener(v -> sendOption("photos"));
        view.findViewById(R.id.btn_video).setOnClickListener(v -> sendOption("video"));
        view.findViewById(R.id.btn_web).setOnClickListener(v -> sendOption("web"));
        view.findViewById(R.id.btn_buttons).setOnClickListener(v -> sendOption("buttons"));

        return view;
    }

    private void sendOption(String option) {
        if (listener != null) {
            listener.onOptionClicked(option);
        }
    }

    private void toggleMenu() {
        LayoutParams params = rootMenu.getLayoutParams();

        if (isExpanded) {
            params.width = dpToPx(50);
            setTextVisibility(false);
        } else {
            params.width = dpToPx(220);
            setTextVisibility(true);
        }

        rootMenu.setLayoutParams(params);
        isExpanded = !isExpanded;
    }

    private void setTextVisibility(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;

        textProfile.setVisibility(visibility);
        textPhotos.setVisibility(visibility);
        textVideo.setVisibility(visibility);
        textWeb.setVisibility(visibility);
        textButtons.setVisibility(visibility);
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    // resaltar selección
    public void highlightOption(String option) {

    }
}