package com.example.webaddicted;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

/**
 * Created by deepaksharma on 20/8/18.
 */

public class GoogleAuth{
    private static String TAG = GoogleAuth.class.getSimpleName();
    private static final int RC_SIGN_IN = 123;

    public static void init(Activity activity, String clientId) {
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
            handleSignInResult(result);
        }
    }

    private static void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct.getPhotoUrl() != null) {
                Log.d(TAG, "handleSignInResult: DisplayName -> " + acct.getDisplayName() +
                        "\n Email Id -> " + acct.getEmail() + "\n Id -> " + acct.getId() +
                        "\n IdToken -> " + acct.getIdToken() + "\n Photo Url -> " + acct.getPhotoUrl() +
                        "\n GivenName -> " + acct.getGivenName());
            }
        } else {
            Log.d(TAG, "handleSignInResult: fail");
        }
    }

    private static GoogleSignInClient isLogin(Activity activity) {
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

}
