package com.deepaksharma.webaddicted.auth;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.deepaksharma.webaddicted.utils.AppClass;
import com.deepaksharma.webaddicted.utils.LoginType;
import com.deepaksharma.webaddicted.vo.UserModel;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

/**
 * Created by deepaksharma on 20/8/18.
 */

public class GoogleAuth {
    private static String TAG = GoogleAuth.class.getSimpleName();
    private static final int RC_SIGN_IN = 123;
    private static onGoogleListener mOnGoogleListener;

    public static void init(Activity activity, String clientId, onGoogleListener onGoogleListener) {
        mOnGoogleListener = onGoogleListener;
        AppClass.setLoginType(LoginType.GOOGLE);
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(clientId)
                    .requestProfile()
                    .requestEmail()
                    .build();
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            activity.startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result.getSignInAccount());
        } else
            mOnGoogleListener.onFailure(data.getDataString());
    }

    private static void handleSignInResult(GoogleSignInAccount account) {
            if (account!=null && account.getPhotoUrl() != null) {
                UserModel userModel = new UserModel(account.getId() + "", account.getIdToken(), account.getDisplayName(),
                        account.getEmail(), account.getPhotoUrl().toString(), "", "");
                Log.d(TAG, "handleSignInResult: DisplayName -> " + account.getDisplayName() +
                        "\n Email Id -> " + account.getEmail() + "\n Id -> " + account.getId() +
                        "\n IdToken -> " + account.getIdToken() + "\n Photo Url -> " + account.getPhotoUrl() +
                        "\n GivenName -> " + account.getGivenName());
                mOnGoogleListener.onSuccess(userModel);
            }
    }

    public static GoogleSignInClient isLogin(Activity activity) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
        return mGoogleSignInClient;
    }

    public static boolean logOut(Activity activity) {
        isLogin(activity).signOut();
        return true;
    }

    public interface onGoogleListener {
        void onSuccess(UserModel userModel);

        void onFailure(String errorMessage);
    }
}
