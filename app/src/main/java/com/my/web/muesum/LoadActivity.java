package com.my.web.muesum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;

public class LoadActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView loadText;
    private Runnable mRunnable;
    private AppCompatActivity mActivity;
    public static Context mcontext;
    private Handler mHandler;
    private int step = 0;
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
        setContentView(R.layout.content_load);

        getSupportActionBar().hide();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        loadText    = (TextView) findViewById(R.id.loadtext);
        mActivity = this;
        mcontext = this;
        step = 0;
        /**
         * GCMID설정
         * */
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);

        final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("")) {
            GCMRegistrar.register(this, "481426464088");
        } else {
            Log.e("id", regId);
        }
        if(((myApplication) this.getApplication()).getRegGCMId() == null) {
            ((myApplication) this.getApplication()).setRegGCMId(regId);
            System.out.println("들어간값2 :" + ((myApplication) this.getApplication()).getRegGCMId());
        }

        mRunnable = new Runnable(){
            public void run() {
                step=step+1;
                if (step < 10) {
                    Log.e("GCM체크 ", ((myApplication) getApplication()).getRegGCMId());
                    progressBar.setProgress(step);
                    loadText.setText("GCM 코드 확인... " + step + "");
                    mHandler.postDelayed(mRunnable, 1000);

                } else {

                        Intent intent = new Intent(mcontext, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                }
            }
        };

        mHandler = new Handler();
        mHandler.post(mRunnable); // Runnable 객체 실행
        mHandler.postAtFrontOfQueue(mRunnable); // Runnable 객체를 Queue 맨앞에 할당
        mHandler.postDelayed(mRunnable, 3000); // Runnable 객체를 1초 뒤에 실행
    }

}
