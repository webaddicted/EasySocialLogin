package com.deepaksharma.deepaksharma.authgoogle2.utils;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deepaksharma.deepaksharma.authgoogle2.GlobalClass;
import com.deepaksharma.deepaksharma.authgoogle2.ui.SocialActivity;

import java.io.File;

/**
 * Created by deepaksharma on 5/9/18.
 */

public class Utilites {
    public static void showImage(ImageView imageView, String url) {
        Glide.with(GlobalClass.getInstance()).load(url)
                .thumbnail(0.5f)
                .crossFade()
                .into(imageView);
    }
    public static void showImagefromLocal(ImageView imageView, File file) {
        Glide.with(GlobalClass.getInstance()).load(Uri.fromFile(file))
                .thumbnail(0.5f)
                .crossFade()
                .into(imageView);
    }
    public static void showToast(String message) {
        Toast.makeText(GlobalClass.getInstance(), message, Toast.LENGTH_SHORT).show();
    }
}
