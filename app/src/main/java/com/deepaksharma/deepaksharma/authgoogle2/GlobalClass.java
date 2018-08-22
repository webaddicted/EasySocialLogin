package com.deepaksharma.deepaksharma.authgoogle2;

import android.app.Application;

import com.deepaksharma.webaddicted.AppClass;

/**
 * Created by deepaksharma on 20/8/18.
 */

public class GlobalClass extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        AppClass.init(getApplicationContext());
    }
}
