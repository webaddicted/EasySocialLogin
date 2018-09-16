package com.deepaksharma.deepaksharma.authgoogle2.ui;

import android.arch.lifecycle.ViewModel;

import com.deepaksharma.deepaksharma.authgoogle2.databinding.ActivityMainBinding;
import com.deepaksharma.deepaksharma.authgoogle2.utils.Utilites;
import com.deepaksharma.webaddicted.vo.UserModel;

/**
 * Created by deepaksharma on 23/8/18.
 */

public class SocialViewModel extends ViewModel {

    public void loginInfo(ActivityMainBinding activityMainBinding, UserModel userModel){
        Utilites.showImage(activityMainBinding.imgUser, userModel.getImage());
        activityMainBinding.txtUserInfo.setText(
                "\n\nName -> " + userModel.getName() +
                        "\n\nEmail -> " + userModel.emailId +
                        "\n\nBirthday -> " + userModel.birthday +
                        "\n\nPhoto -> " + userModel.getImage() +
                        "\n\nId -> " + userModel.getuId() +
                        "\n\nToken -> " + userModel.getAccess_token());
    }
}
