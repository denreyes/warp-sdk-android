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
	//Initialize WARP (Context, Endpoint, Warp API Key, Android Application Version, Debug Mode)
	Warp.init(MainActivity.this, "http://api.warp.com/api/1/", "s1dfscv3xc2v132", "1.0.0", true);
	
    //Warp Example Login
    WarpUser.login(MainActivity.this, etUsername.getText().toString(), etPassword.getText().toString(), new WarpInterface() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onSuccess(Result result) {
                        Toast.makeText(getApplicationContext(), result.getResult().toString(), Toast.LENGTH_LONG).show();
                    }
                });
```
