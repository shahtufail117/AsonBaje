package com.asonbaje.test.ui.InternationalLogin;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.asonbaje.test.databinding.FragmentInternationalloginBinding;

public class InternationalLogin extends Fragment {

    private FragmentInternationalloginBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InternationalLoginWebView IntelLoginViewModel =
                new ViewModelProvider(this).get(InternationalLoginWebView.class);

        binding = FragmentInternationalloginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final WebView IntelloginWebView = binding.idWebViewInternationalLogin;
        final ProgressBar loadingPB = binding.idPBLoading;
        IntelloginWebView.loadUrl("https://www.shop.asonbaje.com.np/my-account/");
        WebSettings webSettings = IntelloginWebView.getSettings();

        //Javascript for WebView
        webSettings.setJavaScriptEnabled(true);
        //Enable Cache inside the app HTML
        IntelloginWebView.getSettings().setDomStorageEnabled(true);

        // Set cache size to 8 mb by default. should be more than enough
        IntelloginWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 100);

        // This next one is crazy. It's the DEFAULT location for your app's cache
        // But it didn't work for me without this line
        IntelloginWebView.getSettings().setAppCachePath("/data/data/" + "AsonBaje" + "/cache/Intl");
        IntelloginWebView.getSettings().setAllowFileAccess(true);
        IntelloginWebView.getSettings().setAppCacheEnabled(true);

        IntelloginWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        IntelloginWebView.setWebViewClient(new WebViewClient() {

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingPB.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingPB.setVisibility(View.GONE);
            }
        });

        IntelloginWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (IntelloginWebView.canGoBack()) {
                                IntelloginWebView.goBack();
                            }

                    }
                }
                return false;
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}