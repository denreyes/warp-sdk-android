package com.warp.android.http;

import com.warp.android.http.models.AuthRequest;
import com.warp.android.http.models.AuthResponse;
import com.warp.android.http.models.Location;
import com.warp.android.http.models.ResultList;
import com.warp.android.http.models.Status;
import com.warp.android.http.models.Result;
import com.warp.android.http.models.User;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface WarpService {

    @POST("login")
    Observable<Status<AuthResponse>> login(@Body AuthRequest request);

    @POST("logout")
    Observable<Status<AuthResponse>> logout(@Header("X-Warp-Session-Token") String token);

    @POST("users")
    Observable<Status<User>> register(@Body User user);

    @GET("users?include=[\"location.address\", \"location.city\", \"location.province\"]")
    Observable<Status<ArrayList<User>>> getUsers(@Header("X-Warp-Session-Token") String token, @QueryMap HashMap<String, Object> constraint);

    @GET("users/{id}?include=[\"location.address\",\"location.city\",\"location.province\",\"location.latitude\",\"location.longitude\"]")
    Observable<Status<User>> getUserById(@Path("id") String id);

    @POST("functions/{className}")
    Observable<Result> functions(@Header("X-Warp-Session-Token") String token,
                              @Path("className") String className,
                              @Body HashMap<String, Object> body);

    @POST("functions/{className}")
    Observable<ResultList> functionsList(@Header("X-Warp-Session-Token") String token,
                                         @Path("className") String className,
                                         @Body HashMap<String, Object> body);

    @POST("classes/location")
    Observable<Result> createLocation(@Body Location location);

    @POST("classes/{className}")
    Observable<Result> create(@Header("X-Warp-Session-Token") String token,
                              @Path("className") String className,
                              @Body HashMap<String, Object> body);

    @GET("classes/{className}")
    Observable<ResultList> retrieve(@Header("X-Warp-Session-Token") String token,
                                @Path("className") String endpoint,
                                @QueryMap HashMap<String, Object> constraint);

    @GET("classes/{className}/{id}")
    Observable<Result> first(@Header("X-Warp-Session-Token") String token,
                             @Path("className") String endpoint,
                             @Path("id") String id);

    @PUT("classes/{className}/{id}")
    Observable<Result> update(
            @Header("X-Warp-Session-Token") String token,
            @Path("className") String endpoint,
            @Path("id") String id,
            @Body HashMap<String, Object> body);

    @DELETE("classes/{className}/{id}")
    Observable<Result> delete(@Header("X-Warp-Session-Token") String token,
                              @Path("className") String endpoint,
                              @Path("id") String id);

}
