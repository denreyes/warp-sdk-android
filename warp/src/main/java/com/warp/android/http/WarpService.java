package com.warp.android.http;

import com.warp.android.http.models.AuthRequest;
import com.warp.android.http.models.AuthResponse;
import com.warp.android.http.models.Location;
import com.warp.android.http.models.Status;
import com.warp.android.http.models.Result;
import com.warp.android.http.models.User;

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

    @POST("classes/location")
    Observable<Result> createLocation(@Body Location location);

    @POST("{className}")
    Observable<Result> create(@Header("X-Warp-Session-Token") String token,
                              @Path("className") String className,
                              @Body HashMap<String, Object> body);

    @GET("{className}")
    Observable<Result> retrieve(@Header("X-Warp-Session-Token") String token,
                                @Path("className") String endpoint,
                                @QueryMap HashMap<String, Object> constraint);

    @GET("{className}/{id}")
    Observable<Result> first(@Header("X-Warp-Session-Token") String token,
                             @Path("className") String endpoint,
                             @Path("id") String id);

    @PUT("{className}/{id}")
    Observable<Result> update(
            @Header("X-Warp-Session-Token") String token,
            @Path("className") String endpoint,
            @Path("id") String id,
            @Body HashMap<String, Object> body);

    @DELETE("{className}/{id}")
    Observable<Result> delete(@Header("X-Warp-Session-Token") String token,
                              @Path("className") String endpoint,
                              @Path("id") String id);

}
