package com.my.web.muesum;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.HttpAuthHandler;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private static Context context;
    private RelativeLayout mWebViewContainer;
    private Runnable mRunnable;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskWrites()
                .detectDiskReads()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork()
                .detectCustomSlowCalls()
                .penaltyLog().build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();
        mWebViewContainer = (RelativeLayout)findViewById(R.id.mWebViewContainer);

        callChrome("http://www.muesum.co.kr/shop/main");

        /*
        webView = (WebView)findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClientClass());
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        webView.addJavascriptInterface(new com.my.web.muesum.JavascriptInterface(MainActivity.this), "android");
        context = this;
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                Log.d("sdfsdf", "onCreateWindow"+resultMsg);
                WebView targetWebView = new WebView(context); // pass a context
                targetWebView.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageStarted(WebView view, String url,
                                              Bitmap favicon) {

                        super.onPageStarted(view, url, favicon);
                    }
                });
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(targetWebView);
                resultMsg.sendToTarget();
                return true;
            }
            @Override
            public void onCloseWindow(WebView window) {
                Toast.makeText(context, "창닫기", Toast.LENGTH_SHORT).show();
            }
            @Override
            public boolean onJsAlert(WebView webView, String url, String message, final JsResult result) {
                new AlertDialog
                        .Builder(MainActivity.this)
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

        webView.loadUrl("http://muesum.co.kr/");
        */
    }
    public void callChrome(String url){
        //크롬브라우저 패키지명
        String packageName = "com.android.chrome";

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setPackage(packageName); //바로 이 부분
        i.setData(Uri.parse("http://muesum.co.kr/shop/?app=android&appvar=" + ((myApplication) this.getApplication()).getRegGCMId()));

        //크롬브라우저가 설치되어있으면 호출, 없으면 마켓으로 설치유도
        List<ResolveInfo> activitiesList = getPackageManager().queryIntentActivities(i, -1);
        if(activitiesList.size() > 0) {
            startActivity(i);
        } else {
            Intent playStoreIntent = new Intent(Intent.ACTION_VIEW);
            playStoreIntent.setData(Uri.parse("market://details?id="+packageName));
            playStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(playStoreIntent);
        }

        mRunnable = new Runnable(){
            public void run() {
                finish();
            }
        };
        mHandler = new Handler();
        mHandler.post(mRunnable); // Runnable 객체 실행
        mHandler.postAtFrontOfQueue(mRunnable); // Runnable 객체를 Queue 맨앞에 할당
        mHandler.postDelayed(mRunnable, 1000); // Runnable 객체를 1초 뒤에 실행
    }
    private ValueCallback<Uri> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 1;
    @Override
    protected  void onActivityResult(int requestCode, int resultCode,
                                     Intent intent) {
        Toast.makeText(context, "창닫기", Toast.LENGTH_SHORT).show();
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null
                    : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
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
            Log.d("webview",handler.toString());
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
        @Override
        public void onPageFinished(WebView view, String url){

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {

            webView.goBack();

            return true;

        }

        return super.onKeyDown(keyCode, event);

    }
}
