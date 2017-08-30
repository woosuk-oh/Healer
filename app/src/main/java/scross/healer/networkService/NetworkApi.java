package scross.healer.networkService;

import android.content.Context;


import okhttp3.OkHttpClient;
import java.net.NetworkInterface;
import java.util.concurrent.TimeUnit;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by hoyeonlee on 2017. 7. 26..
 */

public class NetworkApi {
    private static NetworkApi instance = null;
    public static final String BASE_URL = "http://52.78.146.211/";
    private static NetworkService networkService;
    static Context context;
    public static NetworkApi getInstance(Context ct) {
        if (instance == null) {
            context = ct;
            instance = new NetworkApi();
        }
        return instance;
    }

    public NetworkApi(){
        buildRetrofit();
    }
    private void buildRetrofit() {
        AddCookiesInterceptor in1 = new AddCookiesInterceptor(context);
        ReceivedCookiesInterceptor in2 = new ReceivedCookiesInterceptor(context);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .addNetworkInterceptor(in1)
                .addInterceptor(in2)
                .build();

        this.networkService = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build().create(NetworkService.class);
        // Build your services once
    }
    public NetworkService getServce() {
        return this.networkService;
    }



}
