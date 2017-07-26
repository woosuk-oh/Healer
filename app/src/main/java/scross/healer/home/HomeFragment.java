package scross.healer.home;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.NetworkInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import scross.healer.BaseFragment;
import scross.healer.R;
import scross.healer.network.home.NetworkApi;
import scross.healer.profile.ProfileDialogFragment;
import scross.healer.timeline.TimelineFragment;

/**
 * Created by hanee on 2017-07-25.
 */

public class HomeFragment extends BaseFragment {

    public HomeFragment() {
        super();
    }
    NetworkInterface apiService;
    TextView progressView;

    private RelativeLayout programRate;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        apiService = NetworkApi.getInstance().getServce();
//        Call<ResponseBody> getMain = apiService.main();
//        getMain.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.body()!= null){
//                    try{
//                        JSONObject data = new JSONObject(response.body().string());
//                        String code = data.get("code").toString();
//                        if(code.equals("1")){
//                            JSONObject results = data.getJSONObject("results");
//                            progressView.setText(results.get("progressRate")+"%");
//                        }else{
//                            Toast.makeText(getActivity().getApplicationContext(), data.get("results").toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }else{
//                    Toast.makeText(getActivity().getApplicationContext(), "서버오류입니다", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
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
