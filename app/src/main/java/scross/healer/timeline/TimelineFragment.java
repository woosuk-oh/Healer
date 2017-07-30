package scross.healer.timeline;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scross.healer.BaseFragment;
import scross.healer.R;
import scross.healer.camera.CameraActivity;
import scross.healer.databinding.FragmentTimelineBinding;
import scross.healer.networkService.NetworkApi;
import scross.healer.networkService.NetworkService;
import scross.healer.networkService.object.TimelineObject;
import scross.healer.profile.ProfileDialogFragment;

/**
 * Created by hanee on 2017-07-18.
 */

public class TimelineFragment extends BaseFragment implements View.OnClickListener {

    public TimelineFragment() {
        super();
    }

    boolean surveyBefore;
    boolean surveyAfter;
    int lastDay=1;



    FragmentTimelineBinding binding; // 데이터 바인딩 셋팅 (바인딩으로 인해 자동으로 생성된 클래스.)
    TimelineObject timelineObject; // 데이터 바인딩 셋팅 (레이아웃에 넣을 데이터 객체. 네트워크로부터 받아온 데이터는 여기에 넣으면 됨.)

    NetworkService apiService;
    private LinearLayout content1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        network();
        Log.e("ddd","onCreate");

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.actionbar_menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                ProfileDialogFragment dialog = new ProfileDialogFragment();
                dialog.show(getFragmentManager(), "Profile Update test");
                return true;

            default:
                return true;
        }
    }

    /** 프레그먼트에서의 데이터 바인딩 **/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e("ddd","onCreateView");


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timeline, container,false); // 데이터 바인딩 2
        View view = binding.getRoot(); // 데이터 바인딩 3

        ImageView timelineStateIcon1;
        ImageView timelineStateIcon2;
        ImageView timelineStateIcon3;
        ImageView timelineStateIcon4;
        ImageView timelineStateIcon5;
        ImageView timelineStateIcon6;
        ImageView timelineStateIcon7;
        ImageView timelineStateIcon8;
        ImageView timelineStateIcon9;
        timelineStateIcon1 = (ImageView) view.findViewById(R.id.timeline_state_icon1); //사전 서베이
        timelineStateIcon2 = (ImageView) view.findViewById(R.id.timeline_state_icon2); //사후 서베이
        timelineStateIcon3 = (ImageView) view.findViewById(R.id.timeline_state_icon3);
        timelineStateIcon4 = (ImageView) view.findViewById(R.id.timeline_state_icon4);
        timelineStateIcon5 = (ImageView) view.findViewById(R.id.timeline_state_icon5);
        timelineStateIcon6 = (ImageView) view.findViewById(R.id.timeline_state_icon6);
        timelineStateIcon7 = (ImageView) view.findViewById(R.id.timeline_state_icon7);
        timelineStateIcon8 = (ImageView) view.findViewById(R.id.timeline_state_icon8);
        timelineStateIcon9 = (ImageView) view.findViewById(R.id.timeline_state_icon9);

        TextView timelineContentName1;
        TextView timelineContentName2;
        TextView timelineContentName3;
        TextView timelineContentName4;
        TextView timelineContentName5;
        TextView timelineContentName6;
        TextView timelineContentName7;
        TextView timelineContentName8;
        TextView timelineContentName9;
        TextView timelineContentName10;
        timelineContentName1 = (TextView) view.findViewById(R.id.timeline_content_name1);
        timelineContentName2 = (TextView) view.findViewById(R.id.timeline_content_name2);
        timelineContentName3 = (TextView) view.findViewById(R.id.timeline_content_name3);
        timelineContentName4 = (TextView) view.findViewById(R.id.timeline_content_name4);
        timelineContentName5 = (TextView) view.findViewById(R.id.timeline_content_name5);
        timelineContentName6 = (TextView) view.findViewById(R.id.timeline_content_name6);
        timelineContentName7 = (TextView) view.findViewById(R.id.timeline_content_name7);
        timelineContentName8 = (TextView) view.findViewById(R.id.timeline_content_name8);
        timelineContentName9 = (TextView) view.findViewById(R.id.timeline_content_name9);
        timelineContentName10 = (TextView) view.findViewById(R.id.timeline_content_name10);



        if(surveyBefore == true){
            timelineStateIcon1.setBackgroundResource(R.drawable.projectcomplete);

        }else{
            timelineStateIcon1.setBackgroundResource(R.drawable.projectprogress);
            timelineContentName1.setTextColor(Color.GRAY);


        }

        if(surveyAfter == true){
            timelineStateIcon2.setBackgroundResource(R.drawable.projectcomp lete);

        }else {
            timelineStateIcon1.setBackgroundResource(R.drawable.projectprogress);

        }


        switch (lastDay){
            case 1:
                timelineStateIcon3.setBackgroundResource(R.drawable.projectprogress);
                break;
            case 2:
                timelineStateIcon3.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon4.setBackgroundResource(R.drawable.projectprogress);

                break;
            case 3:
                timelineStateIcon3.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon4.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon5.setBackgroundResource(R.drawable.projectprogress);

                break;
            case 4:
                timelineStateIcon3.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon4.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon5.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon6.setBackgroundResource(R.drawable.projectprogress);

                break;
            case 5:
                timelineStateIcon3.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon4.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon5.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon6.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon7.setBackgroundResource(R.drawable.projectprogress);

                break;
            case 6:
                timelineStateIcon3.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon4.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon5.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon6.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon7.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon8.setBackgroundResource(R.drawable.projectprogress);

                break;
            case 7:
                timelineStateIcon3.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon4.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon5.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon6.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon7.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon8.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon9.setBackgroundResource(R.drawable.projectprogress);

                break;
            case 8:
                timelineStateIcon3.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon4.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon5.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon6.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon7.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon8.setBackgroundResource(R.drawable.projectcomplete);
                timelineStateIcon9.setBackgroundResource(R.drawable.projectcomplete);
                break;
            case 9:
                break;

            case 10:
                break;
        }

        content1 = (LinearLayout) view.findViewById(R.id.timeline_content6);
        content1.setOnClickListener(this);


        return view;


        //TODO 텍스트가 25자 이상이면 ...으로 표시
        //TODO 진행 완료한 컨텐츠는 content_name 옆에 +' 완료' 추가하기.
        //TODO 상세보기 누르면 왼쪽 아이콘이랑 오른쪽 리니어레이아웃 탑마진 주기
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.timeline_content6:

                 Intent intent = new Intent(getActivity(), CameraActivity.class);
            startActivity(intent);

                break;

        }
    }



    public void network(){

        apiService = NetworkApi.getInstance(getActivity()).getServce();
        Call<ResponseBody> getTimeline = apiService.timeline();
        getTimeline.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.body()!= null){
                    try{
                        JSONObject data = new JSONObject(response.body().string());
                        String code = data.get("code").toString();
                        if(code.equals("1")){


                            String userEmotion;
                            JSONObject results = data.getJSONObject("results");

                            userEmotion = results.getString("emotion");
                            if(results.getString("emotion").equals("null")){
                                    userEmotion = "감정 상태 : 알 수 없음";
                            }else{
                                userEmotion = "감정 상태 : "+userEmotion;
                            }

                            lastDay = results.getInt("lastday");


                            //TODO switch case문으로 finish_date 값 null 이면 "시작하지 않음"으로 처리


                            timelineObject = new TimelineObject(results.getString("name"),results.getString("birth"), userEmotion, results.getString("gender"),
                                    "finish_date1", "finish_date2", "finish_date3", "finish_date4", "finish_date5", "finish_date6",
                                    "finish_date7", "finish_date8", "finish_date9","finish_date10"

                            ); // 데이터 바인딩 4




                            Log.e("ddd","network");

                            Log.e("dddd",timelineObject.getName()+"");
                            Log.e("dddd",timelineObject.getBirth()+"");
                            Log.e("dddd",timelineObject.getEmotion()+"");



                            binding.setTimeline(timelineObject); // 데이터 바인딩 5


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

    @Override
    public void onDetach() {
        super.onDetach();
    }
}



    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);


        setContentView(R.layout.activity_timeline_body);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon
        toolbar.setTitle("타임라인");*/


/*
        ActionBar actionBar = getActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#550000ff")));
        actionBar.setTitle("타임라인");
        actionBar.setDisplayShowTitleEnabled(true);*/



