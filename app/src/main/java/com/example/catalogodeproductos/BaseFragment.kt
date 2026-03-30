package com.example.catalogodeproductos

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    protected var navigationListener: MenuFragment.OnOptionClickListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationListener = activity as? MenuFragment.OnOptionClickListener
        setupFooterNavigation(view)
    }

    private fun setupFooterNavigation(view: View) {
        // Mapeo genérico de IDs de footer a acciones
        val footerActions = mapOf(
            R.id.foot_profile to "profile",
            R.id.foot_photos to "photos",
            R.id.foot_video to "video",
            R.id.foot_web to "web",
            R.id.foot_buttons to "buttons"
        )

        footerActions.forEach { (id, option) ->
            view.findViewById<ImageButton>(id)?.setOnClickListener {
                navigationListener?.onOptionClicked(option)
            }
        }
    }
}
