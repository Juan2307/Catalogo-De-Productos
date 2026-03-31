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
    }

    private OnOptionClickListener listener;
    private String selectedOption = "profile";

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
    }

    private void setupClickListeners(View view) {

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