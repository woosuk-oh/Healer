package scross.healer.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scross.healer.BaseFragment;
import scross.healer.HealerContext;
import scross.healer.R;
import scross.healer.SharedPreferenceUtil;
import scross.healer.networkService.NetworkApi;
import scross.healer.networkService.NetworkService;
import scross.healer.timeline.TimelineFragment;

/**
 * Created by hanee on 2017-07-25.
 */

public class HomeFragment extends BaseFragment {

    public HomeFragment() {
        super();
    }
    NetworkService apiService;
    TextView progressView;
    TextView homeToday;
    ImageView homeUserImage;
    TextView homeUserState;
    TextView homeUserName;
    String imageUrl;
    String userEmotion;
    String userName;




    private RelativeLayout programRate;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Log.e("AAA","onCreate");



        apiService = NetworkApi.getInstance(getActivity()).getServce();
        Call<ResponseBody> getMain = apiService.main();
        getMain.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("AAA","Retrofit");

                if(response.body()!= null){
                    try{
                        JSONObject data = new JSONObject(response.body().string());
                        String code = data.get("code").toString();
                        if(code.equals("1")){

                            JSONObject results = data.getJSONObject("results");


//



                            if (!results.isNull("emotion")) {
                                userName = (results.getString("name"));
                                userEmotion = results.getString("emotion");
                                imageUrl = results.getString("profile");

                                progressView.setText(results.get("progressRate")+"%");


                                switch (results.getInt("emotion")){
                                    case 1:
                                        homeUserState.setText("활기참");

                                        break;
                                    case 2:
                                        homeUserState.setText("평온함");

                                        break;
                                    case 3:
                                        homeUserState.setText("행복함");

                                        break;
                                    case 4:
                                        homeUserState.setText("보통");

                                        break;
                                    case 5:
                                        homeUserState.setText("우울함");

                                        break;
                                    case 6:
                                        homeUserState.setText("화가남");

                                        break;
                                    case 7:
                                        homeUserState.setText("불안함");

                                        break;
                                }

                            }
                            homeUserName.setText("현재 "+userName+"님의 상태는");


                            if(userEmotion == "null" || userEmotion == null){
                                homeUserState.setText("알 수 없음");
                            }
                            if(imageUrl != "null"){
                                Glide.with(HealerContext.getContext()).load(imageUrl).into(homeUserImage);
                            }



                        }else{
                            Toast.makeText(getActivity().getApplicationContext(), data.get("results").toString(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "서버오류입니다", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        progressView = (TextView) view.findViewById(R.id.tv_progress);

        homeToday = (TextView) view.findViewById(R.id.home_today);
        homeUserImage = (ImageView) view.findViewById(R.id.home_user_image);
        homeUserState = (TextView) view.findViewById(R.id.home_user_state);
        homeUserName = (TextView) view.findViewById(R.id.home_user_name);

        programRate =  (RelativeLayout) view.findViewById(R.id.program_in);

        programRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(getFragmentManager(), TimelineFragment.class);
            }
        });
        Log.e("AAA","onCreateView");

        DateFormat df = new SimpleDateFormat("MM월 dd일");
        String date = df.format(Calendar.getInstance().getTime());



   /*     Log.e("aaa", homeUserState.getText()+"");
        Log.e("bbb", userEmotion+"");
*/
   Log.e("name",userName+"");
        homeToday.setText("오늘은 "+date);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
