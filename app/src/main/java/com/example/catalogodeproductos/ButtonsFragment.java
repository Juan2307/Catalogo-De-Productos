package com.example.catalogodeproductos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ButtonsFragment extends BaseFragment {

    public ButtonsFragment() {
        super(R.layout.fragment_buttons);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Botón SUPPORT
        Button btnSupport = view.findViewById(R.id.btn_support);
        if (btnSupport != null) {
            btnSupport.setOnClickListener(v -> {
                Toast.makeText(requireContext(), "Le dio click en SUPPORT", Toast.LENGTH_SHORT).show();

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:sportpoli@corporative.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Soporte Técnico SportPoli");

                startActivity(Intent.createChooser(emailIntent, "Enviar correo de soporte..."));
            });
        }

        // Botón CATALOGO
        Button btnCatalogo = view.findViewById(R.id.btn_catalogo);
        if (btnCatalogo != null) {
            btnCatalogo.setOnClickListener(v -> {
                Toast.makeText(requireContext(), "Le dio click en CATALOGO", Toast.LENGTH_SHORT).show();

                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://sportpoli.com/catalogo"));

                startActivity(webIntent);
            });
        }
    }
}