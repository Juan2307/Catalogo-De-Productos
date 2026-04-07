package com.example.catalogodeproductos;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class ButtonsFragment extends BaseFragment {

    public ButtonsFragment() {
        super(R.layout.fragment_buttons);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // FORMULARIO
        View formLayout = view.findViewById(R.id.layout_support_form);
        EditText etNombre = view.findViewById(R.id.etNombre);
        EditText etCorreo = view.findViewById(R.id.etCorreo);
        EditText etMensaje = view.findViewById(R.id.etMensaje);

        // BOTONES
        Button btnEnviar = view.findViewById(R.id.btnEnviar);
        Button btnSupport = view.findViewById(R.id.btn_support);
        Button btnCatalogo = view.findViewById(R.id.btn_catalogo);

        // SUPPORT (mostrar/ocultar formulario)
        btnSupport.setOnClickListener(v -> {
            if (formLayout.getVisibility() == View.GONE) {
                formLayout.setVisibility(View.VISIBLE);
            } else {
                formLayout.setVisibility(View.GONE);
            }
        });

        // ENVIAR CORREO
        btnEnviar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String correo = etCorreo.getText().toString().trim();
            String mensaje = etMensaje.getText().toString().trim();

            if (nombre.isEmpty() || correo.isEmpty() || mensaje.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            String asunto = "Soporte - " + nombre;
            String cuerpo = "Nombre: " + nombre +
                    "\nCorreo: " + correo +
                    "\n\nMensaje:\n" + mensaje;

            String uriText = "mailto:sportpoli@corporative.com" +
                    "?subject=" + Uri.encode(asunto) +
                    "&body=" + Uri.encode(cuerpo);

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse(uriText));

            startActivity(intent);
        });

        // DESCARGAR CATÁLOGO
        btnCatalogo.setOnClickListener(v -> {
            String url = "https://drive.google.com/uc?export=download&id=13JGlXbP3FnpUVP7ZWG_puYSZ_n9JjGbl";

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setTitle("Catálogo");
            request.setDescription("Descargando...");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    "catalogo.pdf"
            );

            DownloadManager manager = (DownloadManager) requireContext().getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);

            Toast.makeText(requireContext(), "Descargando...", Toast.LENGTH_SHORT).show();
        });

        // ICONOS
        // ICONOS
        float scale = getResources().getDisplayMetrics().density;
        int iconSizePx = (int) (24 * scale + 0.5f);

        Drawable d1 = ContextCompat.getDrawable(requireContext(), R.drawable.ic_catalogo);
        if (d1 != null) {
            d1.setBounds(0, 0, iconSizePx, iconSizePx);
            btnCatalogo.setCompoundDrawables(d1, null, null, null);
        }

        Drawable d2 = ContextCompat.getDrawable(requireContext(), R.drawable.ic_soporte);
        if (d2 != null) {
            d2.setBounds(0, 0, iconSizePx, iconSizePx);
            btnSupport.setCompoundDrawables(d2, null, null, null);
        }

        Drawable d3 = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_send);
        if (d3 != null) {
            d3.setBounds(0, 0, iconSizePx, iconSizePx);
            btnEnviar.setCompoundDrawables(null, null, d3, null);
        }


        // FAQ

        TextView q1 = view.findViewById(R.id.faq_q1);
        TextView a1 = view.findViewById(R.id.faq_a1);

        TextView q2 = view.findViewById(R.id.faq_q2);
        TextView a2 = view.findViewById(R.id.faq_a2);

        View.OnClickListener toggle = v -> {
            if (v.getId() == R.id.faq_q1) {
                toggleView(a1);
            } else if (v.getId() == R.id.faq_q2) {
                toggleView(a2);
            }
        };

        q1.setOnClickListener(toggle);
        q2.setOnClickListener(toggle);
    }

        // ANIMAR FAQ
        private void toggleView(View view) {
            if (view.getVisibility() == View.GONE) {
                view.setVisibility(View.VISIBLE);
                view.setAlpha(0f);
                view.animate().alpha(1f).setDuration(250).start();
            } else {
                view.animate()
                        .alpha(0f)
                        .setDuration(200)
                        .withEndAction(() -> view.setVisibility(View.GONE))
                        .start();
            }
        }
}