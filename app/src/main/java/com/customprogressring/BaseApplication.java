package com.customprogressring;

import android.app.Application;

import com.customprogressring.util.Utils;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/9/11
 * description   : xxxx描述
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
