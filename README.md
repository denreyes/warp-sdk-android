Warp SDK Android
================

See [warp-server](https://github.com/jakejosol/warp-server) for more info about the implementation.

Gradle

```java
	repositories {
    	maven { url "https://jitpack.io" }
    }
```

```java
	compile 'com.github.ekimual:warp-sdk-android:0.0.1'
```

Sample Usage

```java
	//Initialize WARP
	Warp.init(MainActivity.this, etUrl.getText().toString(), etWarpKey.getText().toString(), "", "", true);
	WarpUser.LoginWithUser(etUsername.getText().toString(), etPassword.getText().toString(), new WarpUser.Result() {
            @Override
           public void onCompleted() { }

           @Override
           public void onSuccess(WarpUser user) {
              WarpSessionKey sessionKey = WarpUser.getCurrentUser();
              Toast.makeText(MainActivity.this, "USER ID:" + String.valueOf(sessionKey.getUserId()) +
                 "\nSESSION TOKEN: " + sessionKey.getSessionToken(), Toast.LENGTH_LONG).show();
           }

           @Override
           public void onError(Throwable e) { }
        });
```
