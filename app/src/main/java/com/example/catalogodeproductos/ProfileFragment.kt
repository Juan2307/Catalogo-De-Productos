package com.example.catalogodeproductos

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listener = activity as? MenuFragment.OnOptionClickListener

        view.findViewById<ImageButton>(R.id.foot_photos).setOnClickListener {
            listener?.onOptionClicked("photos")
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
