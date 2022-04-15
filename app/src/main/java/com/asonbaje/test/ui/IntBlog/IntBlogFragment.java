package com.asonbaje.test.ui.IntBlog;

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

import com.asonbaje.test.databinding.IntblogBinding;

public class IntBlogFragment extends Fragment {

    private IntblogBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        IntBlogViewModel IntBlogViewModel =
                new ViewModelProvider(this).get(IntBlogViewModel.class);

        binding = IntblogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final WebView blogWebView = binding.idWebViewIntBlog;
        final ProgressBar loadingPB = binding.idPBLoading;
        blogWebView.loadUrl("https://www.shop.asonbaje.com.np/blog/");
        WebSettings webSettings = blogWebView.getSettings();

        //Javascript for WebView
        webSettings.setJavaScriptEnabled(true);
        //Enable Cache inside the app HTML
        blogWebView.getSettings().setDomStorageEnabled(true);

        // Set cache size to 8 mb by default. should be more than enough
        blogWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 100);

        // This next one is crazy. It's the DEFAULT location for your app's cache
        // But it didn't work for me without this line
        //Same Cache Grouping for faster loading based on the site base
        blogWebView.getSettings().setAppCachePath("/data/data/" + "AsonBaje" + "/cache/Intl");
        blogWebView.getSettings().setAllowFileAccess(true);
        blogWebView.getSettings().setAppCacheEnabled(true);

        blogWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        blogWebView.setWebViewClient(new WebViewClient() {

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

        blogWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (blogWebView.canGoBack()) {
                                blogWebView.goBack();
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