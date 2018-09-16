package com.deepaksharma.webaddicted.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;


import com.deepaksharma.webaddicted.R;
import com.deepaksharma.webaddicted.utils.AppClass;
import com.deepaksharma.webaddicted.utils.LoginType;
import com.deepaksharma.webaddicted.vo.UserModel;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.StatusesService;

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
        final TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        User user = userResult.data;
        String profileImage = user.profileImageUrl.replace("_normal", "");
        final UserModel userModel = new UserModel(user.getId()+"", twitterSession.getAuthToken().token,user.name, "", profileImage, "", "");

        client.requestEmail(twitterSession, new Callback<String>() {
            @Override
            public void success(Result<String> emailResult) {
                String email = emailResult.data;
                User user = userResult.data;
//                mOnTwitterListener.onSuccess(userResult.data, email);
                String profileImage = user.profileImageUrl.replace("_normal", "");
                UserModel userModel = new UserModel(user.getId()+"", twitterSession.getAuthToken().token,user.name, email, profileImage, "", "");
                Log.d(TAG, "success: username-> " +user.name +
                        "\nprofileImage-> " + profileImage +
                        "\nemailid -> " + user.email+
                "\nemail -> "+email);
                mOnTwitterListener.onSuccess(userModel);
            }

            @Override
            public void failure(TwitterException e) {
                mOnTwitterListener.onSuccess(userModel);
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
        void onFailure(String message);

        void onSuccess(UserModel userModel);
    }

    public static void sharePost(){

//        StatusesService statusesService = TwitterCore.getInstance().getApiClient().getStatusesService();//statusesService;
//        statusesService.up();
//        statusesService.update(message, null, null, null, null, null, null, null, null)
//                .enqueue(object : Callback<Tweet>() {
//            override fun success(result: Result<Tweet>) {
//                Toast.makeText(context, R.string.tweet_posted,Toast.LENGTH_SHORT).show()
//            }
//
//            override  fun failure(exception: TwitterException) {
//                Toast.makeText(context,exception.localizedMessage,Toast.LENGTH_SHORT).show()
//            }
//        })
//        postEditText.setText("")

//        TwitterSession twitt = new Twitt_Sharing(MainActivity.this,
//                consumer_key, secret_key);
//        string_img_url = "http://3.bp.blogspot.com/_Y8u09A7q7DU/S-o0pf4EqwI/AAAAAAAAFHI/PdRKv8iaq70/s1600/id-do-anything-logo.jpg";
//        string_msg = "https://chintankhetiya.wordpress.com/";
//        // here we have web url image so we have to make it as file to
//        // upload
//        String_to_File(string_img_url);
//        // Now share both message & image to sharing activity
//        twitt.shareToTwitter(string_msg, casted_image);

    }
}
