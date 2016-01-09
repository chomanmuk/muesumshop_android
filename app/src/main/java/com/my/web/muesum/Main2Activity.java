package com.my.web.muesum;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.HttpAuthHandler;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    private WebView webView;
    private static Context context;
    private static String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        webView = (WebView)findViewById(R.id.webView2);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebViewClient(new WebViewClientClass());
        webView.loadUrl(url);
        webView.addJavascriptInterface(new com.my.web.muesum.JavascriptInterface(Main2Activity.this), "android");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog,
                                          boolean isUserGesture, Message resultMsg) {

                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
            }
            @Override
            public boolean onJsAlert(WebView webView, String url, String message, final JsResult result) {
                new AlertDialog
                        .Builder(Main2Activity.this)
                        .setTitle("알림말")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }

            ;
        });

    }
    private static class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.stopLoading();
            Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
            if(url.contains("close")){
                Toast.makeText(context, "창닫기", Toast.LENGTH_SHORT).show();
            }else{
                view.loadUrl(url);
            }
            return false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String fallingUrl){
            Toast.makeText(context, "로드 에러", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            // TODO Auto-generated method stub
            Log.d("webview", handler.toString());
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
        @Override
        public void onPageFinished(WebView view, String url){

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
