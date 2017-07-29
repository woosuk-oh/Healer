package scross.healer.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import scross.healer.R;
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


    private RelativeLayout programRate;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        apiService = NetworkApi.getInstance(getActivity()).getServce();
        Call<ResponseBody> getMain = apiService.main();
        getMain.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.body()!= null){
                    try{
                        JSONObject data = new JSONObject(response.body().string());
                        String code = data.get("code").toString();
                        if(code.equals("1")){
                            JSONObject results = data.getJSONObject("results");
                            progressView.setText(results.get("progressRate")+"%");
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
        /*Intent intent = new Intent(getApplicationContext(), MediaplayerActivity.class);
        startActivity(intent);*/
        programRate =  (RelativeLayout) view.findViewById(R.id.program_in);

        programRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(getFragmentManager(), TimelineFragment.class);
            }
        });

        DateFormat df = new SimpleDateFormat("dd MM");
        String date = df.format(Calendar.getInstance().getTime());

        homeToday = (TextView) view.findViewById(R.id.home_today);
        homeUserImage = (ImageView) view.findViewById(R.id.home_user_image);
        homeUserState = (TextView) view.findViewById(R.id.home_user_state);
        homeUserName = (TextView) view.findViewById(R.id.home_user_name);



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
