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
import android.widget.TextView

class WebFragment : BaseFragment(R.layout.fragment_web) {

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webView = view.findViewById<WebView>(R.id.webView)
        val etUrl = view.findViewById<EditText>(R.id.et_url)
        val tvBrowserTitle = view.findViewById<TextView>(R.id.tv_browser_title)

        setupWebView(webView, tvBrowserTitle)
        setupControls(view, webView, etUrl)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupWebView(webView: WebView, tvBrowserTitle: TextView?) {
        webView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_MOVE) {
                v.parent.requestDisallowInterceptTouchEvent(true)
            }
            false
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                return if (url.startsWith("http://") || url.startsWith("https://")) {
                    false
                } else {
                    true
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                tvBrowserTitle?.text = url?.removePrefix("https://")?.removePrefix("http://")
            }
        }

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            cacheMode = WebSettings.LOAD_DEFAULT
        }

        webView.loadUrl("https://www.sportpoli.com")
    }

    private fun setupControls(view: View, webView: WebView, etUrl: EditText) {
        view.findViewById<Button>(R.id.btn_go)?.setOnClickListener {
            var urlInput = etUrl.text.toString().trim()
            if (urlInput.isNotEmpty()) {
                if (!urlInput.startsWith("http")) urlInput = "https://$urlInput"
                webView.loadUrl(urlInput)
            }
        }

        view.findViewById<Button>(R.id.btn_back)
            ?.setOnClickListener { if (webView.canGoBack()) webView.goBack() }
        view.findViewById<Button>(R.id.btn_forward)
            ?.setOnClickListener { if (webView.canGoForward()) webView.goForward() }
        view.findViewById<Button>(R.id.btn_refresh)?.setOnClickListener { webView.reload() }
    }
}
