package com.example.webaddicted;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by deepaksharma on 21/8/18.
 */

public class FBShare {
    private static final String TAG = FBShare.class.getSimpleName();
    private static ShareDialog shareDialog;
    private static CallbackManager mCallBackManager;
    static FBShare fbShare;

    public static FBShare init(@NonNull Activity activity) {
        AppClass.setLoginType(LoginType.FBSHARE);
        shareDialog = new ShareDialog(activity);
        mCallBackManager = CallbackManager.Factory.create();
        shareDialog.registerCallback(mCallBackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.d(TAG, "onSuccess: result -> " + result.getPostId());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: cancel by user ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError: share failed -> " + error.toString());
            }
        });
        return fbShare;
    }

    public static void activityResult(@NonNull int requestCode, @NonNull int resultCode, @NonNull Intent data) {
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }

    public static void shareLink(String link) {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent shareContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(link))

                    .build();
            shareDialog.show(shareContent);
        }
    }

    public static void shareLink(String link, String hashTag) {
//        "#ConnectTheWorld"
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(link))
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag(hashTag)
                        .build())
                .build();
        shareDialog.show(content);
    }

    public static void shareLinkTitle(String link, String contentTitle) {
//                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent shareContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(link))
                    .setContentTitle(contentTitle)
                    .build();
            shareDialog.show(shareContent);
        }
    }

    public static void shareLinkTitleDescription(String link, String contentTitle, String contentDescription) {
//                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent shareContent = new ShareLinkContent.Builder()
                    .setContentTitle(contentTitle)
                    .setContentDescription(contentDescription)
                    .setContentUrl(Uri.parse(link))
                    .build();
            shareDialog.show(shareContent);
        }
    }

    public static void shareLinkTitleDesImg(String link, String contentTitle, String contentDescription, Uri imageUri) {
//                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent shareContent = new ShareLinkContent.Builder()
                    .setContentTitle(contentTitle)
                    .setContentDescription(contentDescription)
                    .setContentUrl(Uri.parse(link))
                    .setImageUrl(imageUri)
                    .build();
            shareDialog.show(shareContent);
        }
    }

    public static void shareImage(Bitmap bitmap) {
        SharePhoto shareImage = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(shareImage)
                .build();
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
    }

    public static void shareImage(Bitmap bitmap, String caption) {
        SharePhoto shareImage = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .setCaption(caption)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(shareImage)
                .build();
        shareDialog.show(content);
    }

    public static void shareImageHashTag(Uri uri, String hashTag) {
        SharePhoto shareImage = new SharePhoto.Builder()
                .setImageUrl(uri)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(shareImage)
                .setShareHashtag(new ShareHashtag.Builder()
                .setHashtag(hashTag)
                .build())
                .build();
        shareDialog.show(content);
    }

    public static void shareImageCaptionHashTag(Uri uri,String caption, String hashTag) {
        SharePhoto shareImage = new SharePhoto.Builder()
                .setImageUrl(uri)
                .setCaption(caption)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(shareImage)
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag(hashTag)
                        .build())
                .build();
        shareDialog.show(content);
    }

    public static void shareImage(Uri uri) {
        SharePhoto shareImage = new SharePhoto.Builder()
                .setImageUrl(uri)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(shareImage)
                .build();
        shareDialog.show(content);
    }

    public static void shareImage(Uri uri, String caption) {
        SharePhoto shareImage = new SharePhoto.Builder()
                .setImageUrl(uri)
                .setCaption(caption)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(shareImage)
                .build();
        shareDialog.show(content);
    }

    public static void shareImageHashTag(Bitmap bitmap, String hashTag) {
        SharePhoto shareImage = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(shareImage)
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag(hashTag)
                        .build())
                .build();
        shareDialog.show(content);
    }

    public static void shareImageCaptionHashTag(Bitmap bitmap,String caption, String hashTag) {
        SharePhoto shareImage = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .setCaption(caption)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(shareImage)
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag(hashTag)
                        .build())
                .build();
        shareDialog.show(content);
    }

    public static void shareVideo(Uri uri) {
        ShareVideo shareVideo = new ShareVideo.Builder()
                .setLocalUrl(uri)
                .build();
        ShareVideoContent content = new ShareVideoContent.Builder()
                .setVideo(shareVideo)
                .build();
        shareDialog.show(content);
    }

    public static void shareMultipleImage(List<Bitmap> bitmapsList) {
        List<SharePhoto> sharePhotosList = new ArrayList<>();
        for (Bitmap bitmap : bitmapsList) {
            sharePhotosList.add(new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build());
        }
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhotos(sharePhotosList)
                .build();
        shareDialog.show(content);
    }

    public static void shareMultipleImage(List<Bitmap> bitmapsList, String caption) {
        List<SharePhoto> sharePhotosList = new ArrayList<>();
        for (Bitmap bitmap : bitmapsList) {
            sharePhotosList.add(new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .setCaption(caption)
                    .build());
        }
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhotos(sharePhotosList)
                .build();
        shareDialog.show(content);
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

}
