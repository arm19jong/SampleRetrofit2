package com.example.jongzazaal.sampleretrofit2;

import com.example.jongzazaal.sampleretrofit2.model.Dep;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jongzazaal on 5/30/2017.
 */

public interface MyService {

    @GET("users/{user}")
    Observable<JSONObject> listRepos(@Path("user") String user);

    @GET("departments/{user}")
    Call<Dep> listRepos2(@Path("user") String user);

    @GET("departments")
    Call<JsonArray> listRepos3();

    @GET("des/{id}")
    Call<JsonObject> getDesByID(@Path("id") int id);

    @POST("des")
    Call<JsonObject> postDes(@Body Map<String, Object> map);

    @GET("des/{id}")
    Observable<JsonObject> getDesByIDRx(@Path("id") int id);
}