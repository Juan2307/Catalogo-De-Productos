package com.example.catalogodeproductos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class MenuFragment : Fragment(R.layout.fragment_menu) {

    interface OnOptionClickListener {
        fun onOptionClicked(option: String)
    }

    private var listener: OnOptionClickListener? = null

    private val menuItems by lazy {
        mapOf(
            "profile" to view?.findViewById<View>(R.id.btn_profile),
            "photos" to view?.findViewById<View>(R.id.btn_photos),
            "video" to view?.findViewById<View>(R.id.btn_video),
            "web" to view?.findViewById<View>(R.id.btn_web),
            "buttons" to view?.findViewById<View>(R.id.btn_buttons)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener = activity as? OnOptionClickListener

        setupClickListeners()
    }

    private fun setupClickListeners() {
        menuItems.forEach { (tag, view) ->
            view?.setOnClickListener {
                highlightOption(tag)
                listener?.onOptionClicked(tag)
            }
        }
    }

    fun highlightOption(option: String) {
        menuItems.forEach { (tag, view) ->
            if (tag == option) {
                // Highlight (Fondo semitransparente como el mockup o color específico)
                view?.setBackgroundColor(0x33FFFFFF)
            } else {
                view?.setBackgroundResource(android.R.color.transparent)
            }
        }
    }
}
