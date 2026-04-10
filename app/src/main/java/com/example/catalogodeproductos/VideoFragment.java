package com.example.catalogodeproductos;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
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

        // Configuración de los 3 videos
        setupCustomVideoControls(
                view.findViewById(R.id.video_view_tenis),
                view.findViewById(R.id.img_placeholder_tenis),
                view.findViewById(R.id.btn_play_tenis),
                view.findViewById(R.id.seekbar_tenis),
                view.findViewById(R.id.controls_tenis),
                "tenis"
        );

        setupCustomVideoControls(
                view.findViewById(R.id.video_view_futbol),
                view.findViewById(R.id.img_placeholder_futbol),
                view.findViewById(R.id.btn_play_futbol),
                view.findViewById(R.id.seekbar_futbol),
                view.findViewById(R.id.controls_futbol),
                "futbol"
        );

        setupCustomVideoControls(
                view.findViewById(R.id.video_view_salud),
                view.findViewById(R.id.img_placeholder_salud),
                view.findViewById(R.id.btn_play_salud),
                view.findViewById(R.id.seekbar_salud),
                view.findViewById(R.id.controls_salud),
                "salud"
        );

    }

    private void setupCustomVideoControls(
            final VideoView videoView,
            final ImageView placeholder,
            final ImageView btnPlay,
            final SeekBar seekBar,
            final View controlsContainer,
            String fileName
    ) {

        String packageName = requireContext().getPackageName();
        int videoResId = getResources().getIdentifier(fileName, "raw", packageName);

        if (videoResId != 0) {

            Uri uri = Uri.parse("android.resource://" + packageName + "/" + videoResId);
            videoView.setVideoURI(uri);

            videoView.setOnPreparedListener(mp -> seekBar.setMax(videoView.getDuration()));

            // Actualización de la barra de progreso
            Runnable updateSeekBar = new Runnable() {

                @Override
                public void run() {

                    if (videoView.isPlaying())
                        seekBar.setProgress(videoView.getCurrentPosition());

                    handler.postDelayed(this, 1000);

                }

            };

            runnableMap.put(videoView.getId(), updateSeekBar);

            // Runnable específico para ocultar el botón
            final Runnable hidePlayButton = () -> {

                if (videoView.isPlaying())
                    btnPlay.setVisibility(View.GONE);

            };

            // Acción al tocar el recuadro del video (Container)
            controlsContainer.setOnClickListener(v -> {

                // Cancelamos cualquier ocultación pendiente para que no se buguee
                handler.removeCallbacks(hidePlayButton);

                if (videoView.isPlaying()) {

                    if (btnPlay.getVisibility() == View.VISIBLE)
                        btnPlay.setVisibility(View.GONE);
                    else {

                        btnPlay.setVisibility(View.VISIBLE);

                        // Si lo mostramos, lo volvemos a ocultar en 3 segundos
                        handler.postDelayed(hidePlayButton, 3000);

                    }

                } else {

                    // Si está pausado, el botón debe ser visible
                    btnPlay.setVisibility(View.VISIBLE);

                }

            });

            // Acción del botón Play/Pause central
            btnPlay.setOnClickListener(v -> {

                // Cancelamos ocultaciones pendientes
                handler.removeCallbacks(hidePlayButton);

                if (videoView.isPlaying()) {

                    videoView.pause();
                    btnPlay.setImageResource(android.R.drawable.ic_media_play);
                    btnPlay.setVisibility(View.VISIBLE);
                    handler.removeCallbacks(updateSeekBar);

                } else {

                    // Primera reproducción (quitar placeholder)
                    if (placeholder.getVisibility() == View.VISIBLE) {

                        placeholder.setVisibility(View.GONE);
                        videoView.setVisibility(View.VISIBLE);
                        seekBar.setVisibility(View.VISIBLE);

                    }

                    videoView.start();
                    btnPlay.setImageResource(android.R.drawable.ic_media_pause);

                    // Ocultar automáticamente el botón tras 2 segundos de iniciar
                    handler.postDelayed(hidePlayButton, 2000);
                    handler.post(updateSeekBar);

                }

            });

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    if (fromUser)
                        videoView.seekTo(progress);

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

            });

            videoView.setOnCompletionListener(mp -> {

                handler.removeCallbacks(hidePlayButton);
                btnPlay.setImageResource(android.R.drawable.ic_media_play);
                btnPlay.setVisibility(View.VISIBLE);
                seekBar.setProgress(0);
                handler.removeCallbacks(updateSeekBar);

            });

        }

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        // Limpiamos todo al cerrar el fragmento para evitar fugas de memoria
        handler.removeCallbacksAndMessages(null);

    }

}
