package com.deepaksharma.deepaksharma.authgoogle2.ui.base;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import com.deepaksharma.deepaksharma.authgoogle2.Permission.PermissionListener;
import com.deepaksharma.deepaksharma.authgoogle2.Permission.Permissions;
import com.deepaksharma.deepaksharma.authgoogle2.R;
import com.deepaksharma.deepaksharma.authgoogle2.utils.PathUtil;
import com.deepaksharma.deepaksharma.authgoogle2.utils.Utilites;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepaksharma on 4/9/18.
 */

public abstract class BaseActivity extends AppCompatActivity implements PermissionListener {
    private static final String TAG = BaseActivity.class.getSimpleName();
    private static final int SELECT_FILE = 1;

    protected abstract void onSelectedFile(File file);

    protected void selectFile() {
        List<String> customPermission = new ArrayList<>();
        customPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        customPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (Permissions.checkAndRequestPermission(BaseActivity.this, customPermission, this))
            galleryCall();
    }

    private void galleryCall() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            switch (requestCode) {
                case SELECT_FILE:
                    onSelectedFile(PathUtil.getPath(BaseActivity.this, uri));
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            Utilites.showToast(getString(R.string.result_cancel));
        } else {
            Utilites.showToast(getString(R.string.something_went_worng));
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Permissions.checkResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionGranted(List<String> mCustomPermission) {
        switch (mCustomPermission.get(0)) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                galleryCall();
                break;
        }
    }

    @Override
    public void onPermissionDenied(List<String> mCustomPermission) {
    }
}
