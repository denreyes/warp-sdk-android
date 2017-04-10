package com.warp.android.http;

import com.warp.android.http.models.AuthRequest;
import com.warp.android.http.models.AuthResponse;
import com.warp.android.http.models.Location;
import com.warp.android.http.models.Result;
import com.warp.android.http.models.ResultList;
import com.warp.android.http.models.Status;
import com.warp.android.http.models.User;
import com.warp.android.utils.DataMap;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface WarpService {

    @POST("login")
    Observable<Status<AuthResponse>> login(@Header("X-Warp-Origin") String origin,
                                           @Body AuthRequest request);

    @GET("logout")
    Observable<Status<AuthResponse>> logout(@Header("X-Warp-Session-Token") String token);

    @POST("users")
    Observable<Status<User>> register(@Body User user);

    @GET("users")
    Observable<ResultList> getUsers(@Header("X-Warp-Session-Token") String token, @QueryMap DataMap constraint);

    @GET("users/{id}")
    Observable<Result> getUserById(@Path("id") int id);

    @PUT("users/{id}")
    Observable<Result> save(@Header("X-Warp-Session-Token") String token, @Path("id") int id, @Body DataMap body);

    @POST("functions/{action}")
    Observable<Result> functions(@Header("X-Warp-Session-Token") String token,
                                 @Path("action") String action,
                                 @Body DataMap body);

    @POST("functions/{action}")
    Observable<Result> functionsList(@Header("X-Warp-Session-Token") String token,
                                     @Path("action") String acion,
                                     @Body DataMap body);

    @POST("classes/location")
    Observable<Result> createLocation(@Body Location location);

    @POST("classes/{className}")
    Observable<Result> create(@Header("X-Warp-Session-Token") String token,
                              @Path("className") String className,
                              @Body DataMap body);

    @GET("classes/{className}")
    Observable<ResultList> retrieve(@Header("X-Warp-Session-Token") String token,
                                    @Path("className") String endpoint,
                                    @QueryMap DataMap constraint);

    @GET("classes/{className}/{id}")
    Observable<Result> first(@Header("X-Warp-Session-Token") String token,
                             @Path("className") String endpoint,
                             @Path("id") int id);

    @PUT("classes/{className}/{id}")
    Observable<Result> update(
            @Header("X-Warp-Session-Token") String token,
            @Path("className") String endpoint,
            @Path("id") int id,
            @Body DataMap body);

    @DELETE("classes/{className}/{id}")
    Observable<Result> delete(@Header("X-Warp-Session-Token") String token,
                              @Path("className") String endpoint,
                              @Path("id") int id);

    @Multipart
    @POST("files")
    Observable<Result> uploadFile(@Header("X-Warp-Session-Token") String token,
                                  @PartMap DataMap parameters);

    @DELETE("files")
    Observable<Result> deleteFile(@Header("X-Warp-Session-Token") String token,
                                  @Body String fileKey);

}
