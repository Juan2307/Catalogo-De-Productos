package com.example.catalogodeproductos;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

public class MenuFragment extends Fragment {

    public interface OnOptionClickListener {
        void onOptionClicked(String option);
        void onToggleMenu();
    }

    private OnOptionClickListener listener;
    private String selectedOption = "profile";
    private boolean isExpanded = false;

    public MenuFragment() {
        super(R.layout.fragment_menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof OnOptionClickListener) {
            listener = (OnOptionClickListener) getActivity();
        }

        setupClickListeners(view);
        highlightOption(selectedOption);
        updateExpansionState(view);
    }

    private void setupClickListeners(View view) {
        View btnToggle = view.findViewById(R.id.btn_toggle_menu);
        if (btnToggle != null) {
            btnToggle.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onToggleMenu();
                }
            });
        }

        Map<String, View> items = new HashMap<>();
        items.put("profile", view.findViewById(R.id.btn_profile));
        items.put("photos", view.findViewById(R.id.btn_photos));
        items.put("video", view.findViewById(R.id.btn_video));
        items.put("web", view.findViewById(R.id.btn_web));
        items.put("buttons", view.findViewById(R.id.btn_buttons));

        for (Map.Entry<String, View> entry : items.entrySet()) {
            String tag = entry.getKey();
            View itemView = entry.getValue();

            if (itemView != null) {
                itemView.setOnClickListener(v -> {
                    highlightOption(tag);
                    if (listener != null) {
                        listener.onOptionClicked(tag);
                    }
                });
            }
        }
    }

    public void setExpanded(boolean expanded) {
        this.isExpanded = expanded;
        View view = getView();
        if (view != null) {
            updateExpansionState(view);
        }
    }

    private void updateExpansionState(View view) {
        int visibility = isExpanded ? View.VISIBLE : View.GONE;
        
        // El botón hamburguesa siempre está visible, pero podrías cambiar algo aquí si quisieras
        
        view.findViewById(R.id.txt_profile).setVisibility(visibility);
        view.findViewById(R.id.txt_photos).setVisibility(visibility);
        view.findViewById(R.id.txt_video).setVisibility(visibility);
        view.findViewById(R.id.txt_web).setVisibility(visibility);
        view.findViewById(R.id.txt_buttons).setVisibility(visibility);
        
        // Elementos del footer (Logo y Títulos abajo)
        view.findViewById(R.id.txt_footer_name).setVisibility(visibility);
        View slogan = view.findViewById(R.id.txt_footer_slogan);
        if (slogan != null) slogan.setVisibility(visibility);
    }

    public void highlightOption(String option) {
        selectedOption = option;

        View view = getView();
        if (view == null) return;

        Map<String, View> items = new HashMap<>();
        items.put("profile", view.findViewById(R.id.btn_profile));
        items.put("photos", view.findViewById(R.id.btn_photos));
        items.put("video", view.findViewById(R.id.btn_video));
        items.put("web", view.findViewById(R.id.btn_web));
        items.put("buttons", view.findViewById(R.id.btn_buttons));

        for (Map.Entry<String, View> entry : items.entrySet()) {
            String tag = entry.getKey();
            View itemView = entry.getValue();

            if (itemView != null) {
                if (tag.equals(option)) {
                    // Highlight
                    itemView.setBackgroundColor(0x33FFFFFF);
                } else {
                    itemView.setBackgroundResource(android.R.color.transparent);
                }
            }
        }
    }
}
