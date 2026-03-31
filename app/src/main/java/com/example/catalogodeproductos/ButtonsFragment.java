package com.example.catalogodeproductos;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

        // BOTÓN SUPPORT
        Button btnSupport = view.findViewById(R.id.btn_support);
        btnSupport.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Soporte", Toast.LENGTH_SHORT).show();

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:sportpoli@corporative.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Soporte Técnico SportPoli");

            startActivity(Intent.createChooser(emailIntent, "Enviar correo..."));
        });

        // BOTÓN CATÁLOGO
        Button btnCatalogo = view.findViewById(R.id.btn_catalogo);

        btnCatalogo.setOnClickListener(v -> {

            String url = "https://drive.google.com/uc?export=download&id=13JGlXbP3FnpUVP7ZWG_puYSZ_n9JjGbl";

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setTitle("Catálogo Sportpoli");
            request.setDescription("Descargando catálogo...");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            request.setAllowedOverMetered(true);
            request.setAllowedOverRoaming(true);

            request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    "catalogo_sportpoli.pdf"
            );

            DownloadManager manager = (DownloadManager) requireContext().getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);

            Toast.makeText(requireContext(), "Descargando catálogo...", Toast.LENGTH_SHORT).show();
        });
    }


}