package com.warp.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.warp.android.Warp;
import com.warp.android.http.WarpInterface;
import com.warp.android.utils.WarpLocation;
import com.warp.android.utils.WarpObject;
import com.warp.android.http.models.Result;
import com.warp.android.utils.WarpSessions;
import com.warp.android.utils.WarpUser;

public class MainActivity extends AppCompatActivity {

    private EditText etUrl, etWarpKey, etUsername, etPassword, etEmail, etAddress, etCity, etProvince;
    private Button btnLogin, btnRegister, btnGetUser, btnGetUserById, btnLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etUrl = (EditText) findViewById(R.id.etUrl);
        etWarpKey = (EditText) findViewById(R.id.etWarpKey);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);

        etAddress = (EditText) findViewById(R.id.etAddress);
        etCity = (EditText) findViewById(R.id.etCity);
        etProvince = (EditText) findViewById(R.id.etProvince);

        etUrl.setText("http://stg.api.swiperxapp.com/api/1/");
        etWarpKey.setText("130rfenj1389eu398uhfr3198f");

        Warp.init(MainActivity.this, etUrl.getText().toString(), etWarpKey.getText().toString(), "1.0.0", true);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WarpUser.register(MainActivity.this, etUsername.getText().toString(), etPassword.getText().toString(),
                        etEmail.getText().toString(), new WarpInterface() {
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
            }
        });

        btnGetUser = (Button) findViewById(R.id.btnGetUser);
        btnGetUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WarpObject.Builder()
                        .setClassName("users")
                        .findById(WarpSessions.getUserId(MainActivity.this) + "?include=[\"location.address\", \"location.city\", \"location.province\"]", new WarpInterface() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onSuccess(Result result) {
                                Toast.makeText(MainActivity.this, result.getResult().toString(), Toast.LENGTH_LONG);
                            }
                        });
            }
        });

        btnGetUserById = (Button) findViewById(R.id.btnGetUserById);
        btnGetUserById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WarpObject.Builder()
                        .setClassName("users")
                        .findById(WarpSessions.getUserId(MainActivity.this) + "?include=[\"location.address\", \"location.city\", \"location.province\"]", new WarpInterface() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onSuccess(Result result) {
                                Toast.makeText(MainActivity.this, result.getResult().toString(), Toast.LENGTH_LONG);
                            }
                        });
            }
        });

        btnLocation = (Button) findViewById(R.id.btnLocation);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WarpLocation.saveLocation(MainActivity.this, etAddress.getText().toString(), etCity.getText().toString(),
                        etProvince.getText().toString(), new WarpInterface() {
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
            }
        });
    }
}
