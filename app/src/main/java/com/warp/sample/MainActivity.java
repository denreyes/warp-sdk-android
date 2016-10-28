package com.warp.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.warp.android.Warp;
import com.warp.android.WarpSessionKey;
import com.warp.android.WarpUser;

public class MainActivity extends AppCompatActivity {

    private EditText etUrl, etWarpKey, etUsername, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUrl = (EditText) findViewById(R.id.etUrl);
        etWarpKey = (EditText) findViewById(R.id.etWarpKey);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        etUrl.setText("http://stg.api.swiperxapp.com/api/1/");
        etWarpKey.setText("130rfenj1389eu398uhfr3198f");

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Warp.init(MainActivity.this, etUrl.getText().toString(), etWarpKey.getText().toString(), "v1", "", true);
                WarpUser.LoginWithUser(etUsername.getText().toString(), etPassword.getText().toString(), new WarpUser.Result() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onSuccess(WarpUser user) {
                        WarpSessionKey sessionKey = WarpUser.getCurrentUser();
                        Toast.makeText(MainActivity.this, "USER ID:" + String.valueOf(sessionKey.getUserId()) +
                                "\nSESSION TOKEN: " + sessionKey.getSessionToken(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
            }
        });
    }
}
