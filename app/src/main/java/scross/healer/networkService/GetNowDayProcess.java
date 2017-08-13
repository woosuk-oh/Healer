/*
package scross.healer.networkService;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scross.healer.BaseActivity;
import scross.healer.HealerContext;
import scross.healer.SharedPreferenceUtil;
import scross.healer.media.MediaplayerActivity;

*/
/**
 * Created by gta2v on 2017-08-13.
 *//*


public class GetNowDayProcess {
    NetworkService apiService;

    int state;
    int day;

    public GetNowDayProcess() {


    }
    public int getProcess(){
        apiService = NetworkApi.getInstance(HealerContext.getContext()).getServce();

        Call<ResponseBody> nowState = apiService.nowState();


        nowState.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null) { //JSONObject(response.body().string()) 이게 내가 보낸 json 받는 부분임
                        JSONObject data = new JSONObject(response.body().string());
                        String code = data.get("code").toString();
                        JSONObject results = data.getJSONObject("results");

                        if (code.equals("1")) {

                            state = results.getInt("latest_state");


                            SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());

                            sharedPreferenceUtil.setProcess(state);
                            Log.e("Get Now Process: ", sharedPreferenceUtil.getProcess() + " , 서버 리스폰스.");

                        }

                    } else {
                        Toast.makeText(HealerContext.getContext(), "일시적인 네트워크 문제입니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(HealerContext.getContext(), "서버오류입니다.", Toast.LENGTH_SHORT).show();
                Log.d("value", t.getMessage());

            }
        });

        return state;
    }

    public int getLatestDay(){
        apiService = NetworkApi.getInstance(HealerContext.getContext()).getServce();

        Call<ResponseBody> nowState = apiService.nowState();


        nowState.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null) { //JSONObject(response.body().string()) 이게 내가 보낸 json 받는 부분임
                        JSONObject data = new JSONObject(response.body().string());
                        String code = data.get("code").toString();
                        JSONObject results = data.getJSONObject("results");

                        if (code.equals("1")) {

                            day = results.getInt("latest_day");


                            SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());

                            sharedPreferenceUtil.setLastDay(day);
                            Log.e("Get Now Day: ", sharedPreferenceUtil.getLastDay() + " , 겟 데이.");
                        }

                    } else {
                        Toast.makeText(HealerContext.getContext(), "일시적인 네트워크 문제입니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(HealerContext.getContext(), "서버오류입니다.", Toast.LENGTH_SHORT).show();
                Log.d("value", t.getMessage());

            }
        });


    return day;
    }

*/
/*
    public int getState() {
        getProcess();

        return state;
    }

    public int getDay() {

        getLatestDay();

        return day;
    }*//*

}
*/
