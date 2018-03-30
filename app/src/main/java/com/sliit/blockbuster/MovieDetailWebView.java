package com.sliit.blockbuster;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.Objects;

public class MovieDetailWebView extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_movie_detail_web_view);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled( false );
        webView= findViewById( R.id.detailView );
        progressBar = findViewById( R.id.progressBar );

        webView.setWebViewClient( new NewsClient() );
        String url = getIntent().getStringExtra("WEB_URL");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setHorizontalScrollBarEnabled(false);
    }

    public class NewsClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility( View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        super.finish();
    }

}
