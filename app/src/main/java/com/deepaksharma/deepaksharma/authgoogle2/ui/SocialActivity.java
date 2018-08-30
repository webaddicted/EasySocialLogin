package com.deepaksharma.deepaksharma.authgoogle2.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deepaksharma.deepaksharma.authgoogle2.R;
import com.deepaksharma.deepaksharma.authgoogle2.databinding.ActivityMainBinding;
import com.deepaksharma.webaddicted.utils.AppClass;
import com.deepaksharma.webaddicted.auth.FBAuth;
import com.deepaksharma.webaddicted.auth.FBShare;
import com.deepaksharma.webaddicted.auth.GoogleAuth;
import com.deepaksharma.webaddicted.auth.TwitterAuth;
import com.deepaksharma.webaddicted.vo.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twitter.sdk.android.core.models.User;

public class SocialActivity extends AppCompatActivity implements SocialLoginListener, FBShare.FbPostListener {
    String TAG = SocialActivity.class.getSimpleName();
    private static final String TWITTER_KEY = "euRoJOFhjuSz0PgiDfm87U5y3";
    private static final String TWITTER_SECRET = "ZLMmiM3RL9ftD9uenN3tDFu742P1cwJONzllNjC8KpdvNjZuvJ";
    ActivityMainBinding activityMainBinding;
    Bitmap myLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuth.init(SocialActivity.this, TWITTER_KEY, TWITTER_SECRET);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setSocialHandler(new SocialHandler(this));
        myLogo = ((BitmapDrawable) getResources().getDrawable(R.drawable.deepak)).getBitmap();
    }
    @Override
    public void onGoogle() {
        GoogleAuth.init(SocialActivity.this, getString(R.string.default_web_client_id), new GoogleAuth.onGoogleListener() {
            @Override
            public void onSuccess(GoogleSignInAccount acct) {
                Glide.with(SocialActivity.this).load(acct.getPhotoUrl())
                        .thumbnail(0.5f)
                        .crossFade()
                        .into(activityMainBinding.imgUser);
                activityMainBinding.txtUserInfo.setText("Id -> " + acct.getId() +
                                "\n\nId Token -> " + acct.getIdToken() +
                                "\n\nDisplay Name -> " + acct.getDisplayName() +
                                "\n\nGiven Name -> " + acct.getGivenName() +
                                "\n\nEmail Id -> " + acct.getEmail() +
                                "\n\nPhoto Url -> " + acct.getPhotoUrl());
            }

            @Override
            public void onFailure(String errorMessage) {
                activityMainBinding.txtUserInfo.setText(errorMessage);
                Log.d(TAG, "onFailure: " + errorMessage);
            }
        });
    }

    @Override
    public void onFacebook() {
        FBAuth.fbLogin(SocialActivity.this, new FBAuth.onFBListener() {
            @Override
            public void onSuccess(UserModel userModel) {
                Glide.with(SocialActivity.this).load(userModel.getImage())
                        .thumbnail(0.5f)
                        .crossFade()
                        .into(activityMainBinding.imgUser);
                activityMainBinding.txtUserInfo.setText("Facebookid -> " + userModel.getuId() +
                        "\n\nFacebook name -> " + userModel.getName() +
                        "\n\nFacebook email -> " + userModel.emailId +
                        "\n\nFacebook birthday -> " + userModel.birthday +
                        "\n\nFacebook Photo -> " + userModel.getImage());
            }

            @Override
            public void onFailure(String errorMessage) {
                activityMainBinding.txtUserInfo.setText(errorMessage);
                Log.d(TAG, "onFailure: " + errorMessage);
            }
        });
    }

    @Override
    public void onFBShare() {
//        FBShare.init(SocialActivity.this).shareLink("https://github.com/webaddicted");
//        FBShare.init(SocialActivity.this).shareLink("https://github.com/webaddicted", "#DEEPAK_SHARMA");
//        new Handler().post(() -> {
//            List<Bitmap> bitmaps = new ArrayList<>();
//            bitmaps.add(myLogo);
//            bitmaps.add(myLogo);
//            bitmaps.add(myLogo);
//            Log.d(TAG, "onFBShare: "+myLogo.toString());
//            FBShare.init(SocialActivity.this).shareMultipleImage(bitmaps, "#Deepak_Sharma");
//        }) ;
        Log.d(TAG, "onFBShare: " + myLogo.toString());
        FBShare.init(SocialActivity.this, this).shareImage(myLogo);    }

    @Override
    public void onTwitter() {
        TwitterAuth.requestLogin(SocialActivity.this, new TwitterAuth.onTwitterListener() {
            @Override
            public void onSuccess(User user, String email) {
                String profileImage = user.profileImageUrl.replace("_normal", "");
                Log.d(TAG, "success: username-> " + user.name + "\n url -> " + profileImage);
                Glide.with(SocialActivity.this).load(profileImage)
                        .thumbnail(0.5f)
                        .crossFade()
                        .into(activityMainBinding.imgUser);
                activityMainBinding.txtUserInfo.setText("id -> " + user.id +
                        "\nName -> " + user.name +
                        "\n\nEmail -> " + user.email +
                        "\n\nEmail -> " + email +
                        "\n\nPhoto -> " + profileImage);
            }

            @Override
            public void onFailure(String errorMessage) {
                activityMainBinding.txtUserInfo.setText(errorMessage);
                Toast.makeText(SocialActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onInstagram() {
    }

    @Override
    public void onLogOut() {
        if (AppClass.getLoginType() != null && AppClass.getLoginType().toString().length() > 0) {
            switch (AppClass.getLoginType()) {
                case GOOGLE:
                    GoogleAuth.logOut(SocialActivity.this);
                    break;
                case FACEBOOK:
                    FBAuth.logOut();
                    break;
                case FBSHARE:
                    FBShare.logOut();
                    break;
                case TWITTER:
                    TwitterAuth.logOut();
                    break;
            }
        } else {
            Log.d(TAG, "onLogOut: please Login.");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (AppClass.getLoginType()) {
            case GOOGLE:
                GoogleAuth.onActivityResult(requestCode, resultCode, data);
                break;
            case FACEBOOK:
                FBAuth.activityResult(requestCode, resultCode, data);
                break;
            case FBSHARE:
                FBShare.activityResult(requestCode, resultCode, data);
                break;
            case TWITTER:
                TwitterAuth.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onFbPostSuccess(String postId) {
        Log.d(TAG, "onFbPostSuccess: "+postId);
    }

    @Override
    public void onFbPostFailure(String error) {
        Log.d(TAG, "onFbPostFailure: "+error);
    }
}
