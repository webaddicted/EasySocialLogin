package com.example.webaddicted;

import android.content.Context;
import android.util.Log;

/**
 * Created by deepaksharma on 20/8/18.
 */

public class AppClass  {
    private static final String TAG = AppClass.class.getSimpleName();
    private static Context mContext;
    private static LoginType mLoginType;

    public static Context init(Context context) {
        if (context == null)
            mContext = context;
        return mContext;
    }

    public static Context getInstance() {
        if (mContext == null) {
            Log.d(TAG, "getInstance: module not initalize");
            return null;
        }
        return mContext;
    }

    public static void setLoginType(LoginType loginType) {
        mLoginType = loginType;
    }

    public static LoginType getLoginType() {
        return mLoginType;
    }

}
