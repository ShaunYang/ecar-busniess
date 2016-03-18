package com.pyec.ecarbusiness.ui;

import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.BaseActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.graphics.Bitmap;
import android.webkit.JsResult;

public class WebViewActivity extends BaseActivity{

	private final static String TAG = "WebViewActivity";
	private WebView m_webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        InitView();
    }

    private void InitView()
    {
		m_webView = (WebView) findViewById(R.id.webview);
    	WebSettings setting = m_webView.getSettings();
		setting.setCacheMode(WebSettings.LOAD_NO_CACHE);  
		m_webView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return super.shouldOverrideUrlLoading(view, url);
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}
			
		});
		m_webView.loadUrl("http://120.27.161.243/fecar/Application/Home/View/auction-rule.html");

    }
}
