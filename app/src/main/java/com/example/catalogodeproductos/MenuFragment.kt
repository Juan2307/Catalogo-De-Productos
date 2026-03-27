package com.example.catalogodeproductos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class MenuFragment : Fragment(R.layout.fragment_menu) {

    interface OnOptionClickListener {
        fun onOptionClicked(option: String)
    }

    private var listener: OnOptionClickListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (context is OnOptionClickListener) {
            listener = context as OnOptionClickListener
        }

        view.findViewById<View>(R.id.btn_profile).setOnClickListener { 
            highlightOption("profile")
            listener?.onOptionClicked("profile") 
        }
        view.findViewById<View>(R.id.btn_photos).setOnClickListener { 
            highlightOption("photos")
            listener?.onOptionClicked("photos") 
        }
        view.findViewById<View>(R.id.btn_video).setOnClickListener { 
            highlightOption("video")
            listener?.onOptionClicked("video") 
        }
        view.findViewById<View>(R.id.btn_web).setOnClickListener { 
            highlightOption("web")
            listener?.onOptionClicked("web") 
        }
        view.findViewById<View>(R.id.btn_buttons).setOnClickListener { 
            highlightOption("buttons")
            listener?.onOptionClicked("buttons") 
        }
    }

    fun highlightOption(option: String) {
        val views = listOf(
            view?.findViewById<View>(R.id.btn_profile),
            view?.findViewById<View>(R.id.btn_photos),
            view?.findViewById<View>(R.id.btn_video),
            view?.findViewById<View>(R.id.btn_web),
            view?.findViewById<View>(R.id.btn_buttons)
        )

        // Limpiar fondos
        views.forEach { it?.setBackgroundResource(android.R.color.transparent) }

        // Aplicar Highlight (Fondo semitransparente como el mockup)
        val selectedView = when (option) {
            "profile" -> views[0]
            "photos" -> views[1]
            "video" -> views[2]
            "web" -> views[3]
            "buttons" -> views[4]
            else -> null
        }
        selectedView?.setBackgroundColor(0x33FFFFFF) // 20% blanco
    }
}
