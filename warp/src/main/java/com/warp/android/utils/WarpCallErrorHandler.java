package com.warp.android.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.warp.android.http.models.ErrorResponse;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by TrieNoir on 16/01/2017.
 */

public class WarpCallErrorHandler {
    public static WarpCallException getError(Throwable e) {
        ErrorResponse errorParser = new ErrorResponse();
        if(e instanceof HttpException) {
            ResponseBody body = ((HttpException) e).response().errorBody();
            Gson gson = new Gson();
            TypeAdapter<ErrorResponse> adapter = gson.getAdapter(ErrorResponse.class);
            try {
                errorParser = adapter.fromJson(body.string());
            } catch (IOException ex) {
                errorParser.setMessage(ex.getMessage());
            }
        } else {
            errorParser.setMessage(e.getMessage());
        }
        return new WarpCallException(e, errorParser.getStatus(), errorParser.getMessage());
    }
}
