package com.example.catalogodeproductos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class MenuFragment : Fragment(R.layout.fragment_menu) {

    interface OnOptionClickListener {
        fun onOptionClicked(option: String)
    }

    private var listener: OnOptionClickListener? = null
    private var selectedOption: String = "profile"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener = activity as? OnOptionClickListener

        setupClickListeners()
        // Aplicar el resaltado guardado al crear la vista
        highlightOption(selectedOption)
    }

    private fun setupClickListeners() {
        val view = view ?: return
        val items = mapOf(
            "profile" to view.findViewById<View>(R.id.btn_profile),
            "photos" to view.findViewById<View>(R.id.btn_photos),
            "video" to view.findViewById<View>(R.id.btn_video),
            "web" to view.findViewById<View>(R.id.btn_web),
            "buttons" to view.findViewById<View>(R.id.btn_buttons)
        )

        items.forEach { (tag, itemView) ->
            itemView?.setOnClickListener {
                highlightOption(tag)
                listener?.onOptionClicked(tag)
            }
        }
    }

    fun highlightOption(option: String) {
        selectedOption = option
        val view = view ?: return // Si la vista no existe aún, se aplicará en onViewCreated

        val items = mapOf(
            "profile" to view.findViewById<View>(R.id.btn_profile),
            "photos" to view.findViewById<View>(R.id.btn_photos),
            "video" to view.findViewById<View>(R.id.btn_video),
            "web" to view.findViewById<View>(R.id.btn_web),
            "buttons" to view.findViewById<View>(R.id.btn_buttons)
        )

        items.forEach { (tag, itemView) ->
            if (tag == option) {
                // Highlight (Fondo semitransparente)
                itemView?.setBackgroundColor(0x33FFFFFF)
            } else {
                itemView?.setBackgroundResource(android.R.color.transparent)
            }
        }
    }
}
