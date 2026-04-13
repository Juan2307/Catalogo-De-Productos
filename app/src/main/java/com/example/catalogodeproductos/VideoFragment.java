package com.example.catalogodeproductos;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends BaseFragment {

    private List<ExoPlayer> players = new ArrayList<>();

    public VideoFragment() {

        super(R.layout.fragment_video);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // Configuración de los 3 videos con ExoPlayer (Media3)
        setupExoPlayer(
                view.findViewById(R.id.player_view_tenis),
                view.findViewById(R.id.img_placeholder_tenis),
                view.findViewById(R.id.btn_play_tenis),
                "tenis"
        );

        setupExoPlayer(
                view.findViewById(R.id.player_view_futbol),
                view.findViewById(R.id.img_placeholder_futbol),
                view.findViewById(R.id.btn_play_futbol),
                "futbol"
        );

        setupExoPlayer(
                view.findViewById(R.id.player_view_salud),
                view.findViewById(R.id.img_placeholder_salud),
                view.findViewById(R.id.btn_play_salud),
                "salud"
        );

    }

    private void setupExoPlayer(
            final PlayerView playerView,
            final ImageView placeholder,
            final ImageView btnPlay,
            String fileName
    ) {

        String packageName = requireContext().getPackageName();

        int videoResId = getResources().getIdentifier(fileName, "raw", packageName);

        if (videoResId != 0) {

            Uri uri = Uri.parse("android.resource://" + packageName + "/" + videoResId);

            // Crear el jugador ExoPlayer
            ExoPlayer player = new ExoPlayer.Builder(requireContext()).build();
            player.setMediaItem(MediaItem.fromUri(uri));
            player.prepare();

            playerView.setPlayer(player);
            players.add(player);

            // Acción del botón Play central (estilo personalizado)
            btnPlay.setOnClickListener(v -> {
                placeholder.setVisibility(View.GONE);
                btnPlay.setVisibility(View.GONE);
                playerView.setVisibility(View.VISIBLE);

                player.play();
            });

            // Opcional: Si quieres que el botón reaparezca al pausar desde los controles de Media3
            player.addListener(new androidx.media3.common.Player.Listener() {
                @Override
                public void onIsPlayingChanged(boolean isPlaying) {

                    if (!isPlaying && player.getPlaybackState() != ExoPlayer.STATE_ENDED) {
                        // El jugador se pausó
                    }

                }
            });

        }

    }

    @Override
    public void onPause() {

        super.onPause();

        for (ExoPlayer player : players)
            player.pause();

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        for (ExoPlayer player : players)
            player.release();

        players.clear();

    }

}
