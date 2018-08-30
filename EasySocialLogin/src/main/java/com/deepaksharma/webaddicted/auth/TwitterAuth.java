package com.deepaksharma.webaddicted.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;


import com.deepaksharma.webaddicted.utils.AppClass;
import com.deepaksharma.webaddicted.utils.LoginType;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;

import io.fabric.sdk.android.Fabric;

/**
 * Created by deepaksharma on 22/8/18.
 */

public class TwitterAuth {
    private static final String TAG = TwitterAuth.class.getSimpleName();
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PROFILE_IMAGE_URL = "image_url";
    private static TwitterAuthClient client;
    private static onTwitterListener mOnTwitterListener;
    private static Activity mActivity;

    public static void init(Activity activity, String twitter_key, String twitter_secret) {
        mActivity = activity;
        TwitterAuthConfig authConfig = new TwitterAuthConfig(twitter_key, twitter_secret);
        Fabric.with(activity, new Twitter(authConfig));
    }

    public static void requestLogin(Activity activity, onTwitterListener onTwitterListener) {
        mOnTwitterListener = onTwitterListener;
        AppClass.setLoginType(LoginType.TWITTER);
        client = new TwitterAuthClient();
        client.authorize(activity, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> twitterSessionResult) {
                login(twitterSessionResult);
            }

            @Override
            public void failure(TwitterException e) {
                mOnTwitterListener.onFailure(e.getMessage());
            }
        });
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        client.onActivityResult(requestCode, resultCode, data);
    }

    public static void login(Result<TwitterSession> result) {
        TwitterSession session = result.data;
        final String username = session.getUserName();
        Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false, new Callback<User>() {
                    @Override
                    public void failure(TwitterException e) {
                        mOnTwitterListener.onFailure(e.getMessage());
                    }

                    @Override
                    public void success(Result<User> userResult) {
                        requestEmail(userResult);

                    }
                });

    }
    private static void requestEmail(final Result<User> userResult) {
        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        client.requestEmail(twitterSession, new Callback<String>() {
            @Override
            public void success(Result<String> emailResult) {
                String email = emailResult.data;
                User user = userResult.data;
                mOnTwitterListener.onSuccess(userResult.data, email);
                String profileImage = user.profileImageUrl.replace("_normal", "");
                Log.d(TAG, "success: username-> " +user.name +
                        "\nprofileImage-> " + profileImage +
                        "\nemailid -> " + user.email+
                "\nemail -> "+email);
            }

            @Override
            public void failure(TwitterException e) {
                mOnTwitterListener.onFailure(e.getMessage());
            }
        });
    }
    public static boolean logOut() {
        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (twitterSession != null) {
            clearTwitterCookies(mActivity);
            Twitter.getSessionManager().clearActiveSession();
            Twitter.logOut();
            return true;
        }else
            return false;
    }


    private static void clearTwitterCookies(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

    public interface onTwitterListener {
        void onSuccess(User data, String email);

        void onFailure(String message);
    }
}
