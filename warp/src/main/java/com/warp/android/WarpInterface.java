package com.warp.android;

import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface WarpInterface {

    /*   Queries                            */
    @POST("classes/{className}")
    Observable<WarpObjectResult> save(
            @Path("className") String className,
            @Body JsonObject keys
    );

    @PUT("classes/{className}/{id}")
    Observable<WarpObjectResult> save(
            @Path("className") String className,
            @Path("id") int id,
            @Body JsonObject keys
    );

    @DELETE("classes/{className}/{id}")
    Observable<WarpObjectResult> remove(
            @Path("className") String className,
            @Path("id") int id
    );

    @GET("classes/{className}/{id}")
    Observable<WarpObjectResult> fetch(
            @Path("className") String className,
            @Path("id") int id
    );

    @GET("classes/{className}")
    Observable<WarpQueryResult> find(
            @Path("className") String className,
            @Query(value = "where", encoded = true) String where,
            @Query(value = "include", encoded = true) String include,
            @Query(value = "order", encoded = true) String order,
            @Query("skip") int skip,
            @Query("limit") int limit
    );

    /*   Custom                             */
    @POST("functions/{className}-{action}")
    Observable<WarpQueryResult> customQuery(
            @Path("className") String className,
            @Path("action") String action,
            @Body JsonObject keys
    );

    @POST("functions/{className}-{action}")
    Observable<WarpObjectResult> customObject(
            @Path("className") String className,
            @Path("action") String action,
            @Body JsonObject keys
    );

    /*   Users                              */
    @POST("login")
    Observable<WarpUserResult<JsonObject>> login(
            @Header("X-Warp-Origin") String origin,
            @Body JsonObject keys
    );

    @GET("users/me")
    Observable<WarpUserResult<JsonObject>> fetchCurrentUser();

    @POST("users")
    Observable<WarpUserResult<JsonObject>> signUp(
            @Body JsonObject keys
    );

    @GET("logout")
    Observable<WarpUserResult<JsonObject>> logout();

    /*   Files                              */
    @Multipart
    @POST("files")
    Observable<WarpFileResult> upload(@PartMap Map<String, RequestBody> parameters);
}
