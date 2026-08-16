package com.usmanprime.enterprise;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.content.Intent;
import android.net.Uri;

public class MainActivity extends Activity {
  @Override public void onCreate(Bundle b) {
    super.onCreate(b);
    WebView w = new WebView(this);
    WebSettings s = w.getSettings();
    s.setJavaScriptEnabled(true);
    s.setDomStorageEnabled(true);
    s.setAllowFileAccess(true);
    w.setWebViewClient(new WebViewClient(){
      @Override public boolean shouldOverrideUrlLoading(WebView view, String url){
        try {
          if(url.startsWith("https://") || url.startsWith("http://")) return false;
          startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
          return true;
        } catch(Exception e){ return true; }
      }
    });
    w.loadUrl("file:///android_asset/index.html");
    setContentView(w);
  }
}
