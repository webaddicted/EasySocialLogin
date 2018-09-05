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
                "\n\nGoogle name -> " + userModel.getName() +
                        "\n\nGoogle email -> " + userModel.emailId +
                        "\n\nGoogle birthday -> " + userModel.birthday +
                        "\n\nGoogle Photo -> " + userModel.getImage() +
                        "\n\nGoogle id -> " + userModel.getuId() +
                        "\n\nGoogle token -> " + userModel.getAccess_token());
    }
}
