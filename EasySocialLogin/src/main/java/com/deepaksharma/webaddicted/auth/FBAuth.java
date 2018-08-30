package com.deepaksharma.webaddicted.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.deepaksharma.webaddicted.utils.AppClass;
import com.deepaksharma.webaddicted.vo.FbResponse;
import com.deepaksharma.webaddicted.utils.LoginType;
import com.deepaksharma.webaddicted.vo.UserModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by deepaksharma on 20/8/18.
 */

public class FBAuth {
    private static final String TAG = FBAuth.class.getSimpleName();
    private static List<String> READ_PERMISSION = Arrays.asList("public_profile", "email", "user_birthday", "user_friends", "user_photos");
    private static CallbackManager mCallBackManager;
    private static LoginManager loginManager;
    private static ShareDialog shareDialog;
    private static onFBListener mOnFBListener;

    public static void fbLogin(@NonNull final Activity activity, onFBListener onFBListener) {
        mOnFBListener = onFBListener;
        AppClass.setLoginType(LoginType.FACEBOOK);
        FacebookSdk.sdkInitialize(activity);
        loginManager = LoginManager.getInstance();
        loginManager.logInWithReadPermissions(activity, READ_PERMISSION);
        mCallBackManager = CallbackManager.Factory.create();
        loginManager.registerCallback(mCallBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                mOnFBListener.onFailure("Cancel by user.");
                Log.d(TAG, "onCancel: Facebook Authentication cancel.");
            }

            @Override
            public void onError(FacebookException exception) {
                mOnFBListener.onFailure(exception.getMessage());
                Log.d(TAG, "onError: Facebook Authentication error " + exception.getMessage());
            }
        });
    }

    private static void handleFacebookAccessToken(@NonNull AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken: " + token);
        GraphRequest request = GraphRequest.newMeRequest(token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        FbResponse fbResponse = fromJson(object.toString(), FbResponse.class);
                            Log.d(TAG, "onCompleted: str_facebookname -> " + fbResponse.getName() +
                                    "\n str_facebookemail -> " + fbResponse.getEmail() +
                                    "\n str_facebookid -> " + fbResponse.getId() +
                                    "\n str_birthday -> " + fbResponse.getBirthday() +
                                    "\n strPhoto -> " + fbResponse.getPicture().getData().getUrl());
                            UserModel userModel = new UserModel(fbResponse.getId(), fbResponse.getName(), fbResponse.getEmail(), fbResponse.getPicture().getData().getUrl(), "", fbResponse.getBirthday());
                            mOnFBListener.onSuccess(userModel);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name,first_name,last_name,email,gender,birthday,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }
    public static <T> T fromJson(String json, Class<T> clazz) {
        return new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .create().fromJson(json, clazz);
    }
    public static void activityResult(@NonNull int requestCode, @NonNull int resultCode, @NonNull Intent data) {
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }

    public static String getHashKey(@NonNull Context context) {
        String keyhash = null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyhash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getHashKey: " + keyhash);
        return keyhash;
    }

    public static boolean logOut() {
        LoginManager.getInstance().logOut();
        return true;
    }

    public static boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public interface onFBListener {
        void onSuccess(UserModel userModel);

        void onFailure(String errorMessage);

    }

}
