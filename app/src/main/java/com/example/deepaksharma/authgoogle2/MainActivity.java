package com.example.deepaksharma.authgoogle2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.webaddicted.AppClass;
import com.example.webaddicted.FBAuth;
import com.example.webaddicted.FBShare;
import com.example.webaddicted.GoogleAuth;
import com.example.webaddicted.TwitterAuth;
import com.twitter.sdk.android.core.models.User;

public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName();
    private static final String TWITTER_KEY = "euRoJOFhjuSz0PgiDfm87U5y3";
    private static final String TWITTER_SECRET = "ZLMmiM3RL9ftD9uenN3tDFu742P1cwJONzllNjC8KpdvNjZuvJ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuth.init(MainActivity.this, TWITTER_KEY, TWITTER_SECRET);
        setContentView(R.layout.activity_main);
    }

    public void onGoogle(View view) {
        GoogleAuth.init(MainActivity.this, getString(R.string.default_web_client_id));
    }

    public void onFacebook(View view) {
        FBAuth.fbLogin(MainActivity.this);
    }

    public void onFBShare(View view) {
//        FBShare.init(MainActivity.this).shareLink("https://github.com/webaddicted");
//        FBShare.init(MainActivity.this).shareLink("https://github.com/webaddicted", "#DEEPAK_SHARMA");
        Bitmap myLogo = ((BitmapDrawable) getResources().getDrawable(R.drawable.deepak)).getBitmap();
//        new Handler().post(() -> {
//            List<Bitmap> bitmaps = new ArrayList<>();
//            bitmaps.add(myLogo);
//            bitmaps.add(myLogo);
//            bitmaps.add(myLogo);
//            Log.d(TAG, "onFBShare: "+myLogo.toString());
//            FBShare.init(MainActivity.this).shareMultipleImage(bitmaps, "#Deepak_Sharma");
//
//        }) ;
        FBShare.init(MainActivity.this).shareImage(myLogo);
    }

    public void onTwitter(View view) {
        TwitterAuth.requestLogin(MainActivity.this, new TwitterAuth.onTwitterListener() {
            @Override
            public void onSuccess(User user) {
                String profileImage = user.profileImageUrl.replace("_normal", "");
                Log.d(TAG, "success: username-> " + user.name+"\n url -> "+profileImage);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onInstagram(View view) {
    }

    public void onLogOut(View view) {
        if (AppClass.getLoginType() != null && AppClass.getLoginType().toString().length() > 0) {
            switch (AppClass.getLoginType()) {
                case GOOGLE:
                    GoogleAuth.logOut(MainActivity.this);
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
}
