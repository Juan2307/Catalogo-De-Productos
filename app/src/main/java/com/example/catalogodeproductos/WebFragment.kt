package com.example.catalogodeproductos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment

class WebFragment : Fragment(R.layout.fragment_web) {

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listener = activity as? MenuFragment.OnOptionClickListener

        // Navegación Footer (Solo los 4 iconos solicitados: Perfil, Foto, Video y Botones)
        view.findViewById<ImageButton>(R.id.foot_profile)?.setOnClickListener { listener?.onOptionClicked("profile") }
        view.findViewById<ImageButton>(R.id.foot_photos)?.setOnClickListener { listener?.onOptionClicked("photos") }
        view.findViewById<ImageButton>(R.id.foot_video)?.setOnClickListener { listener?.onOptionClicked("video") }
        view.findViewById<ImageButton>(R.id.foot_buttons)?.setOnClickListener { listener?.onOptionClicked("buttons") }

        // Configuración WebView Dinámico
        val webView = view.findViewById<WebView>(R.id.webView)
        val etUrl = view.findViewById<EditText>(R.id.et_url)
        val tvBrowserTitle = view.findViewById<TextView>(R.id.tv_browser_title)

        // SOLUCIÓN AL SCROLL: Evitar que el ScrollView principal intercepte los gestos del WebView
        webView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_MOVE) {
                v.parent.requestDisallowInterceptTouchEvent(true)
            }
            false
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url.toString()
                // Permitir solo http y https, bloquear otros esquemas que causan crash
                return if (url.startsWith("http://") || url.startsWith("https://")) {
                    false // WebView maneja la carga
                } else {
                    true // Bloquear esquemas desconocidos (whatsapp, intents, etc)
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Actualizar la barra cuando el usuario navegue internamente
                tvBrowserTitle?.text = url?.removePrefix("https://")?.removePrefix("http://")
            }
        }

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT

        webView.loadUrl("https://www.sportpoli.com")

        // Botón Ir
        view.findViewById<Button>(R.id.btn_go)?.setOnClickListener {
            var urlInput = etUrl.text.toString().trim()
            if (urlInput.isNotEmpty()) {
                if (!urlInput.startsWith("http")) urlInput = "https://$urlInput"
                webView.loadUrl(urlInput)
            }
        }

        view.findViewById<Button>(R.id.btn_back)?.setOnClickListener { if (webView.canGoBack()) webView.goBack() }
        view.findViewById<Button>(R.id.btn_forward)?.setOnClickListener { if (webView.canGoForward()) webView.goForward() }
        view.findViewById<Button>(R.id.btn_refresh)?.setOnClickListener { webView.reload() }
    }
}
