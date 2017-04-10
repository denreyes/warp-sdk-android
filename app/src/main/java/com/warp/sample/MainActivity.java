package com.warp.sample;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.warp.android.Warp;
import com.warp.android.http.interfaces.WarpInterface;
import com.warp.android.http.interfaces.WarpObjectInterface;
import com.warp.android.http.interfaces.WarpResultInterface;
import com.warp.android.http.interfaces.WarpUserInterface;
import com.warp.android.http.models.Result;
import com.warp.android.utils.DataList;
import com.warp.android.utils.WarpCallException;
import com.warp.android.utils.WarpFile;
import com.warp.android.utils.WarpFunctionService;
import com.warp.android.utils.WarpObject;
import com.warp.android.utils.WarpQueryService;
import com.warp.android.utils.WarpSessions;
import com.warp.android.utils.WarpUser;
import com.warp.android.utils.WarpUserService;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private EditText etUrl, etWarpKey, etUsername, etPassword, etEmail, etAddress, etCity, etProvince, etUserId;
    private Button btnLogin, btnRegister, btnGetUser, btnGetUserById, btnLocation, btnUpload;
    private ImageView ivUpload;
    private LinearLayout llTokenFeature;
    private TextView tvUserId, tvSessionToken;

    private Uri uri;

    private boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUserId = (TextView) findViewById(R.id.tvUserId);
        tvSessionToken = (TextView) findViewById(R.id.tvSessionToken);

        etUrl = (EditText) findViewById(R.id.etUrl);
        etWarpKey = (EditText) findViewById(R.id.etWarpKey);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);

        etUserId = (EditText) findViewById(R.id.etUserId);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etCity = (EditText) findViewById(R.id.etCity);
        etProvince = (EditText) findViewById(R.id.etProvince);

//        etUrl.setText("http://stg.api.swiperxapp.com/api/2/");
//        etWarpKey.setText("130rfenj1389eu398uhfr3198f");
        etUrl.setText("http://stg.ph.api.snaprx.mclinica.com/api/");
        etWarpKey.setText("fcA0hFedYhiamqnGUS56CneI2uJ5lY");

        Warp.init(MainActivity.this, etUrl.getText().toString(), etWarpKey.getText().toString(), "1.0.0", true);

        llTokenFeature = (LinearLayout) findViewById(R.id.llTokenFeatures);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnGetUser = (Button) findViewById(R.id.btnGetUser);
        btnGetUserById = (Button) findViewById(R.id.btnGetUserById);
        btnLocation = (Button) findViewById(R.id.btnLocation);
        ivUpload = (ImageView) findViewById(R.id.ivUpload);
        btnUpload = (Button) findViewById(R.id.btnUpload);

        adjust();

//        CustomDialog.showProgress(this, "sandking", false, false);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLoggedIn) {

                    WarpUserService
                            .create(MainActivity.this)
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

                                            adjust();
                                        }
                                    });

                } else {

                    WarpUserService
                            .create(MainActivity.this)
                            .logout(new WarpInterface() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(WarpCallException e) {

                                }

                                @Override
                                public void onSuccess() {
                                    adjust();
                                }
                            });
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WarpUserService
                        .create(MainActivity.this)
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

        btnGetUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                WarpQueryService.create(MainActivity.this)
//                        .setClassName("product")
//                        .sort("id", Sort.ASC)
//                        .find(new WarpQueryInterface() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(WarpCallException e) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(ArrayList<WarpObject> warpObjects) {
//                                for (WarpObject warpObject : warpObjects) {
//                                    Log.e("MainActivity.class", "resultId: " + warpObject);
//                                }
////                                Log.e("MainActivity.class", "resultId: " + warpObjects);
////                                Product product = (Product) warpObjects.get(0).toObject(Product.class);
////                                Log.e("MainActivity.class", "resultPid: " + product);
//                            }
//                        });

                WarpFunctionService.create(MainActivity.this)
                        .put("id", 82)
                        .put("updated_at", "2014-09-12T00:00:00Z")
                        .functionsList("product-sync", new WarpResultInterface() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(WarpCallException e) {
                                Log.e("MainActivity.class", "e: " + e);
                            }

                            @Override
                            public void onSuccess(Result result) {
                                Log.e("MainActivity.class", "result: " + result.getResult());
                                DataList updateArray = result.getResult().getNestedArray("update");
                                DataList newArray = result.getResult().getNestedArray("new");

                                Log.e("MainActivity.class", "update: " + updateArray);
                                Log.e("MainActivity.class", "new: " + newArray);

                                Log.e("MainActivity.class", "new1: " + newArray.get(1));
                            }
                        });


//                WarpQueryService
//                        .create(MainActivity.this)
//                        .setClassName("location")
//                        .include("location.address", "location.city", "location.province")
//                        .limit(3)
////                        .string();
//                        .find(new WarpQueryInterface() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(WarpCallException e) {
//                                e.printStackTrace();
//                            }
//
//                            @Override
//                            public void onSuccess(ArrayList<WarpObject> warpObjects) {
////                              Log.e("MainActivity.class", "result: " + warpObjects.get(2));
//                                Log.e("MainActivity.class", "result: " + warpObjects.get(0));
//
//                                WarpPointer province = warpObjects.get(0).getPointer("province");
//                                int id = province.getId();
//                                String className = province.getClassName();
//
//                                Log.e("MainActivity.class", "resultId: " + id);
//                                Log.e("MainActivity.class", "resultClassName: " + className);
//
//                                Log.e("MainActivity.class", "resultZipcode: " + warpObjects.get(0).getResult().get("zipcode"));
//
//                            }
//                        });
            }
        });

        btnGetUserById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //?include=["location.address", "location.city", "location.province"]

                ////@GET("users/{id}?include=[\"location.address\",\"location.city\",\"location.province\",\"location.latitude\",\"location.longitude\"]")

                WarpQueryService
                        .create(MainActivity.this)
                        .include("location.address", "location.city", "location.province",
                                "location.latitude", "location.longitude")
                        .findUserById(Integer.parseInt(etUserId.getText().toString()), new WarpObjectInterface() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(WarpCallException e) {

                            }

                            @Override
                            public void onSuccess(WarpObject warpObject) {
                                Log.e("MainActivity.class", "warpObject: " + warpObject);
                            }
                        });

            }
        });

//        btnLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                WarpLocation.saveLocation(MainActivity.this,
//                        etAddress.getText().toString(),
//                        etCity.getText().toString(),
//                        etProvince.getText().toString(),
//                        new WarpInterface<Result>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(WarpCallException e) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(Result result) {
//                                Toast.makeText(getApplicationContext(), result.getResult().toString(), Toast.LENGTH_LONG).show();
//                            }
//                        });
//            }
//        });

        ivUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(takePictureIntent, 1);
//                }
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    WarpFile.uploadFile(MainActivity.this,
                            new File(realPath(uri)),
                            null,
                            new WarpResultInterface() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(WarpCallException e) {

                                }

                                @Override
                                public void onSuccess(Result result) {
                                    Toast.makeText(MainActivity.this, result.getResult().toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 &&
                resultCode == RESULT_OK) {
            uri = data.getData();
            Log.e("MainActivity.class", "uri: " + uri);
            ivUpload.setImageURI(data.getData());
        }
    }

    private String realPath(Uri uri) {
        Cursor cursor = null;
        String path = null;
        String[] projection = { MediaStore.MediaColumns.DATA };
        try {
            cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                return cursor.getString(index);
            }
        }
        finally { if (cursor != null) cursor.close(); }
        return path;
    }

    private void adjust() {
        if (WarpSessions.isLoggedIn(this)) {
            btnLogin.setText("LOGOUT");
            isLoggedIn = true;
            llTokenFeature.setVisibility(View.VISIBLE);

            tvUserId.setText(String.valueOf(WarpSessions.getUserId(this)));
            tvSessionToken.setText(WarpSessions.getSessionToken(this));
        } else {
            btnLogin.setText("LOGIN");
            isLoggedIn = false;
            llTokenFeature.setVisibility(View.GONE);
        }
    }
}
