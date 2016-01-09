package com.my.web.muesum;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Mark on 2015-11-27.
 */
public class JavascriptInterface {
    private static Activity mContext;
    JavascriptInterface(Activity c){
        mContext = c;
    }
    @android.webkit.JavascriptInterface
    public void ShowToast(String toast){
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

}
