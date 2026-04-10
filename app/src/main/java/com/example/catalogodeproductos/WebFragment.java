package com.example.catalogodeproductos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WebFragment extends BaseFragment {

    public WebFragment() {

        super(R.layout.fragment_web);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        WebView webView = view.findViewById(R.id.webView);
        EditText etUrl = view.findViewById(R.id.et_url);
        TextView tvBrowserTitle = view.findViewById(R.id.tv_browser_title);

        setupWebView(webView, tvBrowserTitle);
        setupControls(view, webView, etUrl);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupWebView(final WebView webView, final TextView tvBrowserTitle) {

        webView.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_MOVE)
                v.getParent().requestDisallowInterceptTouchEvent(true);

            return false;

        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                String url = request.getUrl().toString();

                // 1. Si la URL es normal (http o https), retornamos FALSE.
                // Esto le dice a Android: "No abras el navegador externo, cárgalo aquí mismo".
                if (url.startsWith("http://") || url.startsWith("https://"))
                    return false;

                // 2. Manejo de esquemas especiales para evitar el ERR_UNKNOWN_URL_SCHEME
                try {

                    if (url.startsWith("tel:") || url.startsWith("mailto:") || url.startsWith("whatsapp:")) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        view.getContext().startActivity(intent);
                        return true;

                    }

                    if (url.startsWith("intent:")) {

                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);

                        if (intent != null) {

                            view.getContext().startActivity(intent);
                            return true;

                        }

                    }

                } catch (Exception e) {

                    // Si hay un error o no tienes la app instalada (ej. Whatsapp),
                    // retornamos TRUE para que el WebView no intente cargar la URL y no salga el error.
                    return true;

                }

                // Para cualquier otro esquema desconocido, cancelamos la carga.
                return true;

            }

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);

                if (url != null && tvBrowserTitle != null) {

                    String cleanUrl = url.replaceFirst("^https?://", "");
                    tvBrowserTitle.setText(cleanUrl);

                }

            }

        });

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        webView.setWebChromeClient(new android.webkit.WebChromeClient());
        webView.loadUrl("https://www.sportpoli.com");

    }

    private void setupControls(View view, final WebView webView, final EditText etUrl) {

        Button btnGo = view.findViewById(R.id.btn_go);
        Button btnBack = view.findViewById(R.id.btn_back);
        Button btnForward = view.findViewById(R.id.btn_forward);
        Button btnRefresh = view.findViewById(R.id.btn_refresh);

        btnGo.setOnClickListener(v -> {
            String input = etUrl.getText().toString().trim();
            if (!input.isEmpty()) {

                if (input.startsWith("http://") || input.startsWith("https://"))
                    webView.loadUrl(input);
                else if (input.contains(".") && !input.contains(" "))
                    webView.loadUrl("https://" + input);
                else {

                    String query = input.replace(" ", "+");
                    String url = "https://www.google.com/search?q=" + query;
                    webView.loadUrl(url);

                }

            }

        });

        btnBack.setOnClickListener(v -> {

            if (webView.canGoBack())
                webView.goBack();

        });

        btnForward.setOnClickListener(v -> {

            if (webView.canGoForward())
                webView.goForward();

        });

        btnRefresh.setOnClickListener(v -> webView.reload());

    }

}
