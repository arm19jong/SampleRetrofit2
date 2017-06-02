package com.example.jongzazaal.sampleretrofit2;

import android.util.Log;
import android.widget.Toast;

import com.example.jongzazaal.sampleretrofit2.model.Dep;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import static com.example.jongzazaal.sampleretrofit2.MainActivity.TAG;

/**
 * Created by jongzazaal on 6/2/2017.
 */

public class Presenter implements Connext.Presenter {
    private String baseUrl;

    public Presenter(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    @Override
    public void getRetrofit() {
//        http://192.168.1.42:8091/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        final MyService service = retrofit.create(MyService.class);
        int id = 24;

        Call<JsonObject> repos = service.getDesByID(id);
        repos.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String s = response.body().toString();
                Log.d("TAG", "onResponse: "+s);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    public void getRetrofitByRx() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
//        JsonObject service = retrofit.create(MyService.class);
        MyService myService = retrofit.create(MyService.class);
        Observable<JsonObject> observable  = myService.getDesByIDRx(3)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Subscription subscription = observable.subscribe(new Observer<JsonObject>() {
            @Override
            public void onCompleted() {
                Log.d("TAG", "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG", "onError: "+e.getMessage());
//                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(JsonObject jsonObject) {
//                Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();

                Log.d("TAG", "onNext: "+jsonObject.toString());
            }
        });
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Observable<JsonObject> getRetrofitByRxCustomCreate() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
//        JsonObject service = retrofit.create(MyService.class);
//        Observable<JsonObject> des = Observable.create(new Observable.OnSubscribe<JsonObject>() {
//            @Override
//            public void call(Subscriber<? super JsonObject> subscriber) {
//                JsonArray jsonArray = retrofit.create(MyService.class).listRepos3();
//                Log.d("TAG", "call: ");
//                for(int i=0; i<jsonArray.size(); i++){
//                    subscriber.onNext(jsonArray.get(i).getAsJsonObject());
//                }
//                subscriber.onCompleted();
//            }
//        });
        Observable<JsonObject> observable = Observable.create(new Observable.OnSubscribe<JsonObject>() {
            @Override
            public void call(final Subscriber<? super JsonObject> subscriber) {
//                    List<PostDetail> postDetailList = getPostDetailFromServer();
                    MyService myService = retrofit.create(MyService.class);
                    Call<JsonArray> observable  = myService.listRepos3();
                    observable.enqueue(new Callback<JsonArray>() {
                        @Override
                        public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                            for (int i=0; i<response.body().size(); i++){
                                subscriber.onNext(response.body().get(i).getAsJsonObject());
                            }
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onFailure(Call<JsonArray> call, Throwable t) {
                            subscriber.onError(t);
                        }
                    });
                    Log.d(TAG, "call: ");

//                    for(int i=0; i<5; i++) {
//                        JsonObject j = new JsonObject();
//                        j.addProperty("a",  i);
//                        subscriber.onNext(j);
//                    }

//                    for(int i=0; i<5; i++) {
//                        JsonObject j = new JsonObject();
//                        j.addProperty("a",  i);
//                        subscriber.onNext(j);
//                    }
//                    subscriber.onCompleted();


        }});
        return observable;
//        Subscription subscription = observable.subscribe(new Observer<JsonObject>() {
//            @Override
//            public void onCompleted() {
//                Log.d("TAG", "onCompleted: ");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d("TAG", "onError: "+e.getMessage());
////                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNext(JsonObject jsonObject) {
////                Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
//
//                Log.d("TAG", "onNext: "+jsonObject.toString());
//            }
//        });
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public Observable<Dep> zipRx() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
//        JsonObject service = retrofit.create(MyService.class);
        Observable<JsonObject> des = retrofit
                .create(MyService.class)
                .getDesByIDRx(3)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<JsonObject> des2 = retrofit
                .create(MyService.class)
                .getDesByIDRx(5)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<Dep> combined = Observable.zip(des, des2, new Func2<JsonObject, JsonObject, Dep>() {
            @Override
            public Dep call(JsonObject jsonObject, JsonObject jsonObject2) {
                return new Dep(jsonObject, jsonObject2);
            }
        });

        return combined;
    }
}
