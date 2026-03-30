package com.example.catalogodeproductos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class ButtonsFragment : BaseFragment(R.layout.fragment_buttons) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar botón SUPPORT
        view.findViewById<Button>(R.id.btn_support)?.setOnClickListener {
            Toast.makeText(requireContext(), "Le dio click en SUPPORT", Toast.LENGTH_SHORT).show()
            
            // Funcionalidad extra: Abrir WhatsApp o Correo para soporte
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:sportpoli@corporative.com")
                putExtra(Intent.EXTRA_SUBJECT, "Soporte Técnico SportPoli")
            }
            startActivity(Intent.createChooser(emailIntent, "Enviar correo de soporte..."))
        }

        // Configurar botón CATALOGO
        view.findViewById<Button>(R.id.btn_catalogo)?.setOnClickListener {
            Toast.makeText(requireContext(), "Le dio click en CATALOGO", Toast.LENGTH_SHORT).show()
            
            // Funcionalidad extra: Abrir sitio web del catálogo
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sportpoli.com/catalogo"))
            startActivity(webIntent)
        }
    }
}
