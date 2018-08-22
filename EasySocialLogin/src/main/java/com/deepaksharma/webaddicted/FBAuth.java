package com.deepaksharma.webaddicted;

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

import org.json.JSONObject;

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
                        String str_facebookname, str_facebookemail, str_facebookid,
                                str_birthday, str_location, strPhoto = null;
                        try {
                            Log.e("object", object.toString());
                            str_facebookname = object.getString("name");

                            try {
                                str_facebookemail = object.getString("email");
                            } catch (Exception e) {
                                str_facebookemail = "";
                                e.printStackTrace();
                            }
                            try {
                                str_facebookid = object.getString("id");
                            } catch (Exception e) {
                                str_facebookid = "";
                                e.printStackTrace();
                            }
                            try {
                                str_birthday = object.getString("birthday");
                            } catch (Exception e) {
                                str_birthday = "";
                                e.printStackTrace();
                            }

                            try {
                                JSONObject jsonobject_location = object.getJSONObject("location");
                                str_location = jsonobject_location.getString("name");

                            } catch (Exception e) {
                                str_location = "";
                                e.printStackTrace();
                            }
//                            try {
//                                strPhoto = object.getString("user_photos");
//                            } catch (Exception e) {
//                                strPhoto = "";
//                                e.printStackTrace();
//                            }
                            Log.d(TAG, "onCompleted: str_facebookname -> " + str_facebookname +
                                    "\n str_facebookemail -> " + str_facebookemail +
                                    "\n str_facebookid -> " + str_facebookid +
                                    "\n str_birthday -> " + str_birthday +
                                    "\n str_location -> " + str_location +
                                    "\n strPhoto -> " + strPhoto);
                            UserModel userModel  = new UserModel(str_facebookid, str_facebookname, str_facebookemail,strPhoto,"", str_birthday);
                            mOnFBListener.onSuccess(userModel);
//                            fn_profilepic();

                        } catch (Exception e) {
                            mOnFBListener.onFailure(e.getMessage());
                            Log.d(TAG, "onCompleted: " + e.getMessage());
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, email,gender,birthday,location");
        request.setParameters(parameters);
        request.executeAsync();
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
