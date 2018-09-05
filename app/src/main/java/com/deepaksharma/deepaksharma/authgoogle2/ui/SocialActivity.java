package com.deepaksharma.deepaksharma.authgoogle2.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.deepaksharma.deepaksharma.authgoogle2.R;
import com.deepaksharma.deepaksharma.authgoogle2.databinding.ActivityMainBinding;
import com.deepaksharma.deepaksharma.authgoogle2.ui.base.BaseActivity;
import com.deepaksharma.deepaksharma.authgoogle2.constant.SocialConstant;
import com.deepaksharma.deepaksharma.authgoogle2.utils.Utilites;
import com.deepaksharma.webaddicted.auth.InstaShare;
import com.deepaksharma.webaddicted.utils.AppClass;
import com.deepaksharma.webaddicted.auth.FBAuth;
import com.deepaksharma.webaddicted.auth.FBShare;
import com.deepaksharma.webaddicted.auth.GoogleAuth;
import com.deepaksharma.webaddicted.auth.TwitterAuth;
import com.deepaksharma.webaddicted.vo.UserModel;

import java.io.File;

public class SocialActivity extends BaseActivity implements SocialLoginListener, FBShare.FbPostListener {
    String TAG = SocialActivity.class.getSimpleName();
    ActivityMainBinding activityMainBinding;
    SocialViewModel socialViewModel;
    Bitmap myLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuth.init(SocialActivity.this, SocialConstant.TWITTER_KEY, SocialConstant.TWITTER_SECRET);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        socialViewModel = ViewModelProviders.of(this).get(SocialViewModel.class);
        activityMainBinding.setSocialHandler(new SocialHandler(this));
        myLogo = ((BitmapDrawable) getResources().getDrawable(R.drawable.deepak)).getBitmap();
    }

    @Override
    public void onGoogle() {
        GoogleAuth.init(SocialActivity.this, getString(R.string.default_web_client_id), new GoogleAuth.onGoogleListener() {
            @Override
            public void onSuccess(UserModel userModel) {
                socialViewModel.loginInfo(activityMainBinding, userModel);
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
                socialViewModel.loginInfo(activityMainBinding, userModel);
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
        if (FBAuth.isLoggedIn()) {
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
            FBShare.init(SocialActivity.this, this).shareImage(myLogo);
        } else
            Utilites.showToast("First login on facebook.");
    }

    @Override
    public void onTwitter() {
        TwitterAuth.requestLogin(SocialActivity.this, new TwitterAuth.onTwitterListener() {
            @Override
            public void onSuccess(UserModel userModel) {
                socialViewModel.loginInfo(activityMainBinding, userModel);
            }

            @Override
            public void onFailure(String errorMessage) {
                activityMainBinding.txtUserInfo.setText(errorMessage);
                Toast.makeText(SocialActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTwitterShare() {

    }

    @Override
    public void onInstagram() {
        selectFile();
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
    protected void onSelectedFile(File file) {
        Utilites.showImagefromLocal(activityMainBinding.imgUser, file);
        InstaShare.sharePost(this, file);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (AppClass.getLoginType() != null) {
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
    }

    @Override
    public void onFbPostSuccess(String postId) {
        Log.d(TAG, "onFbPostSuccess: " + postId);
    }

    @Override
    public void onFbPostFailure(String error) {
        Log.d(TAG, "onFbPostFailure: " + error);
    }


}
