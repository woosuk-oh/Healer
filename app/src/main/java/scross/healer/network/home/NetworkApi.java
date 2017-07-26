package scross.healer.network.home;

import android.net.Network;

import java.net.NetworkInterface;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by hoyeonlee on 2017. 7. 26..
 */

public class NetworkApi {
    private static NetworkApi instance = null;
    public static final String BASE_URL = "http://52.78.146.211/";
    private static NetworkInterface networkService;

    public static NetworkApi getInstance() {
        if (instance == null) {
            instance = new NetworkApi();
        }

        return instance;
    }

    public NetworkApi(){
        buildRetrofit();
    }
    private void buildRetrofit() {
        this.networkService = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(NetworkInterface.class);
        // Build your services once
    }
    public NetworkInterface getServce() {
        return this.networkService;
    }



}
