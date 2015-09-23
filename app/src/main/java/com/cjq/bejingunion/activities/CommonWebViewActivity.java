package com.cjq.bejingunion.activities;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.androidquery.AQuery;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.R;

/**
 * Created by CJQ on 2015/8/27.
 */
public class CommonWebViewActivity extends BaseActivity {

    private WebView webView;
    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_webview);

        aq=new AQuery(this);
        webView = aq.id(R.id.common_webview_webview).getWebView();

        aq.id(R.id.common_webview_back).clicked(this, "closeUp");

        WebSettings settings = webView.getSettings();
        String url =getIntent().getStringExtra("url");
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(false);
    }

    public void closeUp(){
        finish();
    }
}
