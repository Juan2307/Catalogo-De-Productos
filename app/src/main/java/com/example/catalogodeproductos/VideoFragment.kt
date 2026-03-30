package com.example.catalogodeproductos

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.VideoView

class VideoFragment : BaseFragment(R.layout.fragment_video) {

    private val handler = Handler(Looper.getMainLooper())
    private val runnableMap = mutableMapOf<Int, Runnable>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuración de Videos con Controles Personalizados
        setupCustomVideoControls(
            view.findViewById(R.id.video_view_tenis),
            view.findViewById(R.id.img_placeholder_tenis),
            view.findViewById(R.id.btn_play_tenis),
            view.findViewById(R.id.btn_rewind_tenis),
            view.findViewById(R.id.btn_forward_tenis),
            view.findViewById(R.id.seekbar_tenis),
            "tenis"
        )

        setupCustomVideoControls(
            view.findViewById(R.id.video_view_futbol),
            view.findViewById(R.id.img_placeholder_futbol),
            view.findViewById(R.id.btn_play_futbol),
            view.findViewById(R.id.btn_rewind_futbol),
            view.findViewById(R.id.btn_forward_futbol),
            view.findViewById(R.id.seekbar_futbol),
            "futbol"
        )

        setupCustomVideoControls(
            view.findViewById(R.id.video_view_salud),
            view.findViewById(R.id.img_placeholder_salud),
            view.findViewById(R.id.btn_play_salud),
            view.findViewById(R.id.btn_rewind_salud),
            view.findViewById(R.id.btn_forward_salud),
            view.findViewById(R.id.seekbar_salud),
            "salud"
        )
    }

    private fun setupCustomVideoControls(
        videoView: VideoView,
        placeholder: ImageView,
        btnPlay: ImageView,
        btnRewind: ImageButton,
        btnForward: ImageButton,
        seekBar: SeekBar,
        fileName: String
    ) {
        val packageName = requireContext().packageName
        val videoResId = resources.getIdentifier(fileName, "raw", packageName)

        if (videoResId != 0) {
            val uri = Uri.parse("android.resource://$packageName/$videoResId")
            videoView.setVideoURI(uri)

            videoView.setOnPreparedListener { mp ->
                seekBar.max = videoView.duration
            }

            val updateSeekBar = object : Runnable {
                override fun run() {
                    if (videoView.isPlaying) {
                        seekBar.progress = videoView.currentPosition
                    }
                    handler.postDelayed(this, 1000)
                }
            }
            runnableMap[videoView.id] = updateSeekBar

            btnPlay.setOnClickListener {
                if (videoView.isPlaying) {
                    videoView.pause()
                    btnPlay.setImageResource(android.R.drawable.ic_media_play)
                    handler.removeCallbacks(updateSeekBar)
                } else {
                    if (placeholder.visibility == View.VISIBLE) {
                        placeholder.visibility = View.GONE
                        videoView.visibility = View.VISIBLE
                        btnRewind.visibility = View.VISIBLE
                        btnForward.visibility = View.VISIBLE
                        seekBar.visibility = View.VISIBLE
                    }
                    videoView.start()
                    btnPlay.setImageResource(android.R.drawable.ic_media_pause)
                    handler.post(updateSeekBar)
                }
            }

            btnForward.setOnClickListener {
                videoView.seekTo(videoView.currentPosition + 10000)
                seekBar.progress = videoView.currentPosition
            }

            btnRewind.setOnClickListener {
                val newPos = (videoView.currentPosition - 10000).coerceAtLeast(0)
                videoView.seekTo(newPos)
                seekBar.progress = videoView.currentPosition
            }

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) videoView.seekTo(progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            videoView.setOnCompletionListener {
                btnPlay.setImageResource(android.R.drawable.ic_media_play)
                seekBar.progress = 0
                handler.removeCallbacks(updateSeekBar)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}
