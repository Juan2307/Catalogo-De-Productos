package com.example.catalogodeproductos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

// Este fragmento representa la sección de video.
class VideoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Infla el layout para este fragmento
        return inflater.inflate(R.layout.fragment_video, container, false)

    }

}
