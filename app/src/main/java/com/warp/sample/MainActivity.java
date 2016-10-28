package com.warp.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.warp.android.Warp;
import com.warp.android.http.WarpCallback;
import com.warp.android.utils.WarpObject;
import com.warp.android.utils.WarpResult;
import com.warp.android.utils.WarpSessions;
import com.warp.android.utils.WarpUser;

public class MainActivity extends AppCompatActivity {

    private EditText etUrl, etWarpKey, etUsername, etPassword;
    private Button btnLogin, btnGetUser;

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

        Warp.initialize(MainActivity.this, etUrl.getText().toString(), etWarpKey.getText().toString(), "1.0.0", true);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WarpUser.login(MainActivity.this, etUsername.getText().toString(), etPassword.getText().toString(), new WarpCallback() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onSuccess(WarpResult result) {
                        Toast.makeText(getApplicationContext(), result.getResult().toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnGetUser = (Button) findViewById(R.id.btnGetUser);
        btnGetUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WarpObject object = new WarpObject.Builder()
                        .setClassName("users")
                        .findById(WarpSessions.getUserId(MainActivity.this) + "?include=[\"location.address\", \"location.city\", \"location.province\"]", new WarpCallback() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onSuccess(WarpResult result) {
                                Toast.makeText(MainActivity.this, result.getResult().size(), Toast.LENGTH_LONG);
                            }
                        });
            }
        });
    }
}
