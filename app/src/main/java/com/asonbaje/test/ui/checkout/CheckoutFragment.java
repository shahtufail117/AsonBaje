package com.asonbaje.test.ui.checkout;

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

import com.asonbaje.test.databinding.CheckoutBinding;

public class CheckoutFragment extends Fragment {

    private CheckoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CheckoutViewModel checkoutViewModel =
                new ViewModelProvider(this).get(CheckoutViewModel.class);

        binding = CheckoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final WebView checkoutWebView = binding.idWebViewCheckout;
        final ProgressBar loadingPB = binding.idPBLoading;
        checkoutWebView.loadUrl("https://www.asonbaje.com.np/checkout/");
        WebSettings webSettings = checkoutWebView.getSettings();

        //Javascript for WebView
        webSettings.setJavaScriptEnabled(true);
        //Enable Cache inside the app HTML
        checkoutWebView.getSettings().setDomStorageEnabled(true);

        // Set cache size to 8 mb by default. should be more than enough
        checkoutWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 100);

        // This next one is crazy. It's the DEFAULT location for your app's cache
        // But it didn't work for me without this line
        checkoutWebView.getSettings().setAppCachePath("/data/data/" + "AsonBaje" + "/cache/home");
        checkoutWebView.getSettings().setAllowFileAccess(true);
        checkoutWebView.getSettings().setAppCacheEnabled(true);

        checkoutWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        checkoutWebView.setWebViewClient(new WebViewClient() {

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

        checkoutWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (checkoutWebView.canGoBack()) {
                                checkoutWebView.goBack();
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