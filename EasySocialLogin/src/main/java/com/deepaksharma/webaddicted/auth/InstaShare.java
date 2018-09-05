package com.deepaksharma.webaddicted.auth;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.deepaksharma.webaddicted.utils.AppClass;

import java.io.File;

/**
 * Created by deepaksharma on 5/9/18.
 */

public class InstaShare {

    private static boolean isInstaInstalled(Context context) {
        boolean app_installed = false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo("com.instagram.android", 0);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
            Toast.makeText(context, "Instagram application is not installed in your device.", Toast.LENGTH_SHORT).show();
        }
        return app_installed;
    }

    public static void sharePost(Context context, File file) {
        if (isInstaInstalled(context)) {
            Uri uri = Uri.fromFile(file);
//            Uri photoURI = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri); // set uri
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Insta testing...."); // set uri
            shareIntent.setPackage("com.instagram.android");
            context.startActivity(shareIntent);
        }
    }
}
