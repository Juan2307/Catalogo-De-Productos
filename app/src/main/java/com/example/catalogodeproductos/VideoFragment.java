package com.example.catalogodeproductos;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class VideoFragment extends BaseFragment {

    private Handler handler = new Handler(Looper.getMainLooper());
    private Map<Integer, Runnable> runnableMap = new HashMap<>();

    public VideoFragment() {
        super(R.layout.fragment_video);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupCustomVideoControls(
                view.findViewById(R.id.video_view_tenis),
                view.findViewById(R.id.img_placeholder_tenis),
                view.findViewById(R.id.btn_play_tenis),
                view.findViewById(R.id.btn_rewind_tenis),
                view.findViewById(R.id.btn_forward_tenis),
                view.findViewById(R.id.seekbar_tenis),
                "tenis"
        );

        setupCustomVideoControls(
                view.findViewById(R.id.video_view_futbol),
                view.findViewById(R.id.img_placeholder_futbol),
                view.findViewById(R.id.btn_play_futbol),
                view.findViewById(R.id.btn_rewind_futbol),
                view.findViewById(R.id.btn_forward_futbol),
                view.findViewById(R.id.seekbar_futbol),
                "futbol"
        );

        setupCustomVideoControls(
                view.findViewById(R.id.video_view_salud),
                view.findViewById(R.id.img_placeholder_salud),
                view.findViewById(R.id.btn_play_salud),
                view.findViewById(R.id.btn_rewind_salud),
                view.findViewById(R.id.btn_forward_salud),
                view.findViewById(R.id.seekbar_salud),
                "salud"
        );
    }

    private void setupCustomVideoControls(
            final VideoView videoView,
            final ImageView placeholder,
            final ImageView btnPlay,
            final ImageButton btnRewind,
            final ImageButton btnForward,
            final SeekBar seekBar,
            String fileName
    ) {
        String packageName = requireContext().getPackageName();
        int videoResId = getResources().getIdentifier(fileName, "raw", packageName);

        if (videoResId != 0) {
            Uri uri = Uri.parse("android.resource://" + packageName + "/" + videoResId);
            videoView.setVideoURI(uri);

            videoView.setOnPreparedListener(mp -> seekBar.setMax(videoView.getDuration()));

            Runnable updateSeekBar = new Runnable() {
                @Override
                public void run() {
                    if (videoView.isPlaying()) {
                        seekBar.setProgress(videoView.getCurrentPosition());
                    }
                    handler.postDelayed(this, 1000);
                }
            };

            runnableMap.put(videoView.getId(), updateSeekBar);

            btnPlay.setOnClickListener(v -> {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    btnPlay.setImageResource(android.R.drawable.ic_media_play);
                    handler.removeCallbacks(updateSeekBar);
                } else {
                    if (placeholder.getVisibility() == View.VISIBLE) {
                        placeholder.setVisibility(View.GONE);
                        videoView.setVisibility(View.VISIBLE);
                        btnRewind.setVisibility(View.VISIBLE);
                        btnForward.setVisibility(View.VISIBLE);
                        seekBar.setVisibility(View.VISIBLE);
                    }
                    videoView.start();
                    btnPlay.setImageResource(android.R.drawable.ic_media_pause);
                    handler.post(updateSeekBar);
                }
            });

            btnForward.setOnClickListener(v -> {
                videoView.seekTo(videoView.getCurrentPosition() + 10000);
                seekBar.setProgress(videoView.getCurrentPosition());
            });

            btnRewind.setOnClickListener(v -> {
                int newPos = Math.max(videoView.getCurrentPosition() - 10000, 0);
                videoView.seekTo(newPos);
                seekBar.setProgress(videoView.getCurrentPosition());
            });

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        videoView.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

            videoView.setOnCompletionListener(mp -> {
                btnPlay.setImageResource(android.R.drawable.ic_media_play);
                seekBar.setProgress(0);
                handler.removeCallbacks(updateSeekBar);
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }
}