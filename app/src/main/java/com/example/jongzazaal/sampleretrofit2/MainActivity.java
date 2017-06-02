package com.example.jongzazaal.sampleretrofit2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jongzazaal.sampleretrofit2.model.Dep;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "RX";
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new Presenter("http://192.168.1.42:8091/");
//        presenter.getRetrofit();

//        presenter.getRetrofitByRx();
        presenter.zipRx().subscribe(new Observer<Dep>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Dep dep) {
                Log.d("TAG", "onNext: "+dep.getDes().toString()+"//"+dep.getDes2().toString());
                Toast.makeText(MainActivity.this, dep.getDes().toString()+"//"+dep.getDes2().toString(), Toast.LENGTH_SHORT).show();
            }
        });


//        Map<String, Object> map = new HashMap<>();
//        map.put("name", "suchaj");
//        map.put("age", "11");
//        Call<JsonObject> postRepos = service.postDes(map);
//        postRepos.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
//            }
//        });


    }
}
