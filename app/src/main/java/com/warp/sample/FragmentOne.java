package com.warp.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.warp.android.http.interfaces.WarpInterface;
import com.warp.android.http.interfaces.WarpUserInterface;
import com.warp.android.utils.WarpCallException;
import com.warp.android.utils.WarpUser;
import com.warp.android.utils.WarpUserService;

/**
 * Created by TrieNoir on 25/01/2017.
 */

public class FragmentOne extends Fragment {

    private EditText etUsername, etPassword, etEmail;
    private Button btnLogin, btnRegister;
    private boolean isLoggedIn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        init(view);

        return view;
    }

    private void init(View view) {
        etUsername = (EditText) view.findViewById(R.id.etUsername);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        etEmail = (EditText) view.findViewById(R.id.etEmail);

        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnRegister = (Button) view.findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isLoggedIn) {
                    WarpUserService
                            .create(getContext())
                            .login(etUsername.getText().toString(),
                                    etPassword.getText().toString(),
                                    new WarpUserInterface() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(WarpCallException e) {
                                            Log.e("MainActivity.class", "error: " + e.getMessage());
                                            Log.e("MainActivity.class", "error: " + e.getReason());
                                        }

                                        @Override
                                        public void onSuccess(WarpUser warpUser) {
                                            Log.e("MainActivity.class", "result: " + warpUser);
                                            isLoggedIn = true;
                                        }
                                    });
                } else {

                    WarpUserService
                            .create(getContext())
                            .logout(new WarpInterface() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(WarpCallException e) {

                                }

                                @Override
                                public void onSuccess() {
                                    isLoggedIn = false;
                                }
                            });

                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WarpUserService
                        .create(getContext())
                        .register(etUsername.getText().toString(), etUsername.getText().toString(),
                                etEmail.getText().toString(), new WarpUserInterface() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(WarpCallException e) {

                                    }

                                    @Override
                                    public void onSuccess(WarpUser warpUser) {

                                    }
                                });

            }
        });
    }

}
