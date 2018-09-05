package com.deepaksharma.deepaksharma.authgoogle2;

import android.app.Application;

import com.deepaksharma.webaddicted.utils.AppClass;

/**
 * Created by deepaksharma on 20/8/18.
 */

public class GlobalClass extends Application {
    private static GlobalClass mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        AppClass.init(getApplicationContext());
    }

    public static GlobalClass getInstance() {
        return mInstance;
    }
}
