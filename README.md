# EasySocialLogin
Social Login is complete package of social login integration.

SocialLoginIntegration
-------------------------------

Android Project with helper library for login through facebook, twitter and google.


**I have used following steps:**
![demo](https://github.com/webaddicted/EasySocialLogin/blob/master/screenshot/home.png)
![demo](https://github.com/webaddicted/EasySocialLogin/blob/master/screenshot/google.png)
![demo](https://github.com/webaddicted/EasySocialLogin/blob/master/screenshot/google_info.png)
![demo](https://github.com/webaddicted/EasySocialLogin/blob/master/screenshot/fb.png)
![demo](https://github.com/webaddicted/EasySocialLogin/blob/master/screenshot/fb_info.png)
![demo](https://github.com/webaddicted/EasySocialLogin/blob/master/screenshot/twitter_info.png)



Steps Follow : 
--------------

**Step 1 : integrate firebase in project.**

**Step 2 : create project in developer facebook & app.twitter.com site.**

**Step 3 : get securit key and fill in firebase auth deshboard and also add key in project.**

**Step 4 : initalize social login library in application class .**

            AppClass.init(getApplicationContext());

**Step 5 : add dependency in gradle file..**

        buildscript {
            repositories {
                maven { url 'https://maven.fabric.io/public' }
            }

            dependencies {
                classpath 'io.fabric.tools:gradle:1.+'
            }
        }
        apply plugin: 'com.android.application'
        apply plugin: 'io.fabric'

        repositories {
            maven { url 'https://maven.fabric.io/public' }
        }

        dependencies {
             implementation 'com.google.android.gms:play-services-auth:16.0.0'
                compile('com.twitter.sdk.android:twitter:1.11.0@aar') {
                    transitive = true;
                }
        }

**Step 6 : Add it in your root build.gradle at the end of repositories:**

        allprojects {
            repositories {
                ...
                maven { url 'https://jitpack.io' }
            }
        }

Step 7 GOOGLE STEPS -
---------------------

**On button click**

         public void onGoogle(View v) {
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

**In Activity Result**

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


**check user login status using**

        GoogleAuth.isLogin(this);

**Logout login by using**

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

  ## Same step for FACEBOOK, FBSHARE & TWITTER

           public void facebookLogin(View v) {
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

            public void twitterLogin(View v) {
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
                public void onFBShare() {
                    Log.d(TAG, "onFBShare: " + myLogo.toString());
                    FBShare.init(SocialActivity.this, this).shareImage(myLogo); 
                 }


**On Activity result is same for all**

**Logout steps is also same for all**

