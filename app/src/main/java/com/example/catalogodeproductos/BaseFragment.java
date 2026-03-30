package com.example.catalogodeproductos;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseFragment extends Fragment {

    protected MenuFragment.OnOptionClickListener navigationListener;

    public BaseFragment(@LayoutRes int layoutId) {
        super(layoutId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof MenuFragment.OnOptionClickListener) {
            navigationListener = (MenuFragment.OnOptionClickListener) getActivity();
        }

        setupFooterNavigation(view);
    }

    private void setupFooterNavigation(View view) {

        Map<Integer, String> footerActions = new HashMap<>();
        footerActions.put(R.id.foot_profile, "profile");
        footerActions.put(R.id.foot_photos, "photos");
        footerActions.put(R.id.foot_video, "video");
        footerActions.put(R.id.foot_web, "web");
        footerActions.put(R.id.foot_buttons, "buttons");

        for (Map.Entry<Integer, String> entry : footerActions.entrySet()) {
            Integer id = entry.getKey();
            String option = entry.getValue();

            ImageButton button = view.findViewById(id);
            if (button != null) {
                button.setOnClickListener(v -> {
                    if (navigationListener != null) {
                        navigationListener.onOptionClicked(option);
                    }
                });
            }
        }
    }
}