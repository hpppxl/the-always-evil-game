package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        WebView aboutView = (WebView)findViewById(R.id.webView);
        aboutView.loadUrl("file:///android_asset/about/about.html");
    }
}
