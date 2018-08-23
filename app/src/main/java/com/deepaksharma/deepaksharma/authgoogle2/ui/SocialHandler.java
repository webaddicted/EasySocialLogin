package com.deepaksharma.deepaksharma.authgoogle2.ui;

import android.view.View;

/**
 * Created by deepaksharma on 23/8/18.
 */

public class SocialHandler {
    SocialLoginListener mSocialLoginListener;

    public SocialHandler(SocialLoginListener socialLoginListener) {
        mSocialLoginListener = socialLoginListener;
    }

    public void onGoogle(View v) {
        mSocialLoginListener.onGoogle();
    }

    public void onFacebook(View v) {
        mSocialLoginListener.onFacebook();
    }

    public void onFBShare(View v) {
        mSocialLoginListener.onFBShare();
    }

    public void onTwitter(View v) {
        mSocialLoginListener.onTwitter();
    }

    public void onInstagram(View v) {
        mSocialLoginListener.onInstagram();
    }

    public void onLogOut(View v) {
        mSocialLoginListener.onLogOut();
    }
}
