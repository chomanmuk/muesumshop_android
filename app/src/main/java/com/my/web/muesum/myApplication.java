package com.my.web.muesum;

import android.app.Application;

/**
 * Created by chomark on 2016. 1. 12..
 */
public class myApplication extends Application {
    private String regGCMId; /*GCM ID*/
    private String regAppId; /*GCM ID*/

    public String getRegGCMId() {
        return regGCMId;
    }

    public void setRegGCMId(String regGCMId) {
        this.regGCMId = regGCMId;
    }

    public String getRegAppId() {
        return regAppId;
    }

    public void setRegAppId(String regAppId) {
        this.regAppId = regAppId;
    }

    @Override
    public String toString() {
        return "myApplication{" +
                "regGCMId='" + regGCMId + '\'' +
                ", regAppId='" + regAppId + '\'' +
                '}';
    }
}
