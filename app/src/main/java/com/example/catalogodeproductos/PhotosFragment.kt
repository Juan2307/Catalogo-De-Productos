package com.example.catalogodeproductos

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class PhotosFragment : Fragment(R.layout.fragment_photos) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listener = activity as? MenuFragment.OnOptionClickListener

        view.findViewById<ImageButton>(R.id.foot_profile).setOnClickListener {
            listener?.onOptionClicked("profile")
        }
        view.findViewById<ImageButton>(R.id.foot_video).setOnClickListener {
            listener?.onOptionClicked("video")
        }
        view.findViewById<ImageButton>(R.id.foot_web).setOnClickListener {
            listener?.onOptionClicked("web")
        }
        view.findViewById<ImageButton>(R.id.foot_buttons).setOnClickListener {
            listener?.onOptionClicked("buttons")
        }
    }
}
