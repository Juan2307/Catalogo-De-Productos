package com.example.catalogodeproductos;

import android.annotation.SuppressLint;
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
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
            }
            return false;
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();

                if (url.startsWith("http://") || url.startsWith("https://")) {
                    view.loadUrl(url);
                    return true;
                }

                return false;
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

                //
                if (input.startsWith("http://") || input.startsWith("https://")) {
                    webView.loadUrl(input);
                }
                //
                else if (input.contains(".") && !input.contains(" ")) {
                    webView.loadUrl("https://" + input);
                }
                //
                else {
                    String query = input.replace(" ", "+");
                    String url = "https://www.google.com/search?q=" + query;
                    webView.loadUrl(url);
                }
            }
        });
        btnBack.setOnClickListener(v -> {
            if (webView.canGoBack()) {
                webView.goBack();
            }
        });

        btnForward.setOnClickListener(v -> {
            if (webView.canGoForward()) {
                webView.goForward();
            }
        });

        btnRefresh.setOnClickListener(v -> webView.reload());
    }
}