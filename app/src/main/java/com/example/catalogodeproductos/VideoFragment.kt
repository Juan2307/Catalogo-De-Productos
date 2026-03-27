package com.example.catalogodeproductos

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.VideoView
import androidx.fragment.app.Fragment

class VideoFragment : Fragment(R.layout.fragment_video) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listener = activity as? MenuFragment.OnOptionClickListener

        // Navegación del Footer
        view.findViewById<ImageButton>(R.id.foot_profile).setOnClickListener { listener?.onOptionClicked("profile") }
        view.findViewById<ImageButton>(R.id.foot_photos).setOnClickListener { listener?.onOptionClicked("photos") }
        view.findViewById<ImageButton>(R.id.foot_web).setOnClickListener { listener?.onOptionClicked("web") }
        view.findViewById<ImageButton>(R.id.foot_buttons).setOnClickListener { listener?.onOptionClicked("buttons") }

        // Configuración de Videos con Controles Personalizados (Saltos de 10s)
        setupCustomVideoControls(
            view.findViewById(R.id.video_view_tenis),
            view.findViewById(R.id.img_placeholder_tenis),
            view.findViewById(R.id.btn_play_tenis),
            view.findViewById(R.id.btn_rewind_tenis),
            view.findViewById(R.id.btn_forward_tenis),
            "tenis"
        )

        setupCustomVideoControls(
            view.findViewById(R.id.video_view_futbol),
            view.findViewById(R.id.img_placeholder_futbol),
            view.findViewById(R.id.btn_play_futbol),
            view.findViewById(R.id.btn_rewind_futbol),
            view.findViewById(R.id.btn_forward_futbol),
            "futbol"
        )

        setupCustomVideoControls(
            view.findViewById(R.id.video_view_salud),
            view.findViewById(R.id.img_placeholder_salud),
            view.findViewById(R.id.btn_play_salud),
            view.findViewById(R.id.btn_rewind_salud),
            view.findViewById(R.id.btn_forward_salud),
            "salud"
        )
    }

    private fun setupCustomVideoControls(
        videoView: VideoView,
        placeholder: ImageView,
        btnPlay: ImageView,
        btnRewind: ImageButton,
        btnForward: ImageButton,
        fileName: String
    ) {
        val packageName = requireContext().packageName
        val videoResId = resources.getIdentifier(fileName, "raw", packageName)

        if (videoResId != 0) {
            val uri = Uri.parse("android.resource://$packageName/$videoResId")
            videoView.setVideoURI(uri)

            // Acción de Play / Pause
            btnPlay.setOnClickListener {
                if (videoView.isPlaying) {
                    videoView.pause()
                    btnPlay.setImageResource(android.R.drawable.ic_media_play)
                } else {
                    if (placeholder.visibility == View.VISIBLE) {
                        placeholder.visibility = View.GONE
                        videoView.visibility = View.VISIBLE
                        btnRewind.visibility = View.VISIBLE
                        btnForward.visibility = View.VISIBLE
                    }
                    videoView.start()
                    btnPlay.setImageResource(android.R.drawable.ic_media_pause)
                }
            }

            // Adelantar 10 segundos
            btnForward.setOnClickListener {
                val currentPos = videoView.currentPosition
                videoView.seekTo(currentPos + 10000)
            }

            // Retroceder 10 segundos
            btnRewind.setOnClickListener {
                val currentPos = videoView.currentPosition
                val newPos = if (currentPos - 10000 < 0) 0 else currentPos - 10000
                videoView.seekTo(newPos)
            }

            // Al terminar el video
            videoView.setOnCompletionListener {
                btnPlay.setImageResource(android.R.drawable.ic_media_play)
            }
        }
    }
}
