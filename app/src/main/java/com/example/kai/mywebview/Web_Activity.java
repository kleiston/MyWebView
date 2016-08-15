package com.example.kai.mywebview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web_Activity extends AppCompatActivity {

    private WebView mWebView;

    /**
     * initialize a WebView which directs to "google.de
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_);
       // Bundle extras =getIntent().getExtras();
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = mWebView.getSettings();
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl("http://google.de");
    }


    /**
     * Created by Kai on 08.08.16.
     */
    public static class MyWebViewClient extends WebViewClient {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {

              /*  if (url != null && url.startsWith("http://")) {
                    view.getContext().startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                } else {
                    return false;
                }
            */
                return false;
            }
    }
}
