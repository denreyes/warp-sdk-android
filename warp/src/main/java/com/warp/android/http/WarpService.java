package com.warp.android.http;

import com.warp.android.utils.WarpResult;

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

    @POST("{className}")
    Observable<WarpResult> insert(@Header("X-Warp-Session-Token") String token,
                                  @Path("className") String className,
                                  @Body HashMap<String, Object> body);

    @GET("{className}")
    Observable<WarpResult> findAll(@Header("X-Warp-Session-Token") String token,
                                   @Path("className") String endpoint,
                                   @QueryMap HashMap<String, Object> constraint);

    @GET("{className}/{id}")
    Observable<WarpResult> first(@Header("X-Warp-Session-Token") String token,
                                 @Path("className") String endpoint,
                                 @Path("id") String id);

    @PUT("{className}/{id}")
    Observable<WarpResult> update(
            @Header("X-Warp-Session-Token") String token,
            @Path("className") String endpoint,
            @Path("id") String id,
            @Body HashMap<String, Object> body);

    @DELETE("{className}/{id}")
    Observable<WarpResult> delete(@Header("X-Warp-Session-Token") String token,
                                  @Path("className") String endpoint,
                                  @Path("id") String id);

}
