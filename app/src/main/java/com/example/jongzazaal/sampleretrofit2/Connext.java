package com.example.jongzazaal.sampleretrofit2;

import com.example.jongzazaal.sampleretrofit2.model.Dep;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import rx.Observable;

/**
 * Created by jongzazaal on 6/2/2017.
 */

public interface Connext {
    interface Presenter{
        void getRetrofit();
        void getRetrofitByRx();
        void setBaseUrl(String baseUrl);
        Observable<JsonObject> getRetrofitByRxCustomCreate();
        String getBaseUrl();
        Observable<Dep> zipRx();

    }
}
