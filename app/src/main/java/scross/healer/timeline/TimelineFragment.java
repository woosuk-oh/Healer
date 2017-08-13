package scross.healer.timeline;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
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

import com.bumptech.glide.Glide;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scross.healer.BaseFragment;
import scross.healer.HealerContext;
import scross.healer.MainActivity;
import scross.healer.R;
import scross.healer.SharedPreferenceUtil;
import scross.healer.camera.CameraActivity;
import scross.healer.emotion.EmotionActivity;
import scross.healer.media.MediaplayerActivity;
import scross.healer.networkService.GetNowDayProcess;
import scross.healer.networkService.NetworkApi;
import scross.healer.networkService.NetworkService;
import scross.healer.profile.ProfileDialogFragment;
import scross.healer.survay.SurvayFragment;

/**
 * Created by hanee on 2017-07-18.
 */

public class TimelineFragment extends BaseFragment implements View.OnClickListener {

    public TimelineFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    int stateProcess;
    NetworkService apiService;
    TextView name;
    TextView birth;
    TextView gender;
    TextView emotion;
    LinearLayout contentsLayout;
    CircleImageView profileImage;
    int completeColor;

    SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        JodaTimeAndroid.init(getActivity());

        network();getProcess();
/*
        GetNowDayProcess getNowDayProcess = new GetNowDayProcess();
        stateProcess = getNowDayProcess.getProcess();


        Log.e("timeline state: ", stateProcess+"");*/
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.e("ddd", "타임라인 onResume");

        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());
        stateProcess = sharedPreferenceUtil.getProcess();


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

    /**
     * 프레그먼트에서의 데이터 바인딩
     **/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e("ddd", "onCreateView");

        View view = inflater.inflate(R.layout.fragment_timeline2, container, false);
        name = view.findViewById(R.id.timeline_user_name);
        birth = view.findViewById(R.id.timeline_user_birth);
        gender = view.findViewById(R.id.timeline_user_sex);
        emotion = view.findViewById(R.id.timeline_user_emotion);
        contentsLayout = view.findViewById(R.id.layout_contents);
        profileImage = view.findViewById(R.id.timeline_user_profile_image);
        completeColor = getColor(getActivity(), R.color.color5);
        return view;
    }


    //TODO 텍스트가 25자 이상이면 ...으로 표시


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
           /* case R.id.timeline_content6:

                 Intent intent = new Intent(getActivity(), CameraActivity.class);
            startActivity(intent);

                break;*/


            case R.id.timeline_content_arrow1:
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                startActivity(intent);

        }


    }


    public void network() {

        apiService = NetworkApi.getInstance(getActivity()).getServce();
        Call<ResponseBody> getTimeline = apiService.timeline();
        getTimeline.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.body() != null) {
                    try {
                        JSONObject data = new JSONObject(response.body().string());
                        String code = data.get("code").toString();
                        if (code.equals("1")) {

                            JSONObject results = data.getJSONObject("results");
                            name.setText(results.getString("name"));
                            birth.setText(results.getString("birth"));
                            gender.setText(" / " + results.getString("gender"));




                            if(!results.isNull("profile")){//프로필 이미지 널이 아닌 경우
                                Glide.with(HealerContext.getContext()).load(results.getString("profile")).into(profileImage);
                            }


                            if (!results.isNull("emotion")) {
                                switch (results.getInt("emotion")){
                                    case 1:
                                        emotion.setText("활기참");

                                        break;
                                    case 2:
                                        emotion.setText("평온함");

                                        break;
                                    case 3:
                                        emotion.setText("행복함");

                                        break;
                                    case 4:
                                        emotion.setText("보통");

                                        break;
                                    case 5:
                                        emotion.setText("우울함");

                                        break;
                                    case 6:
                                        emotion.setText("화가남");

                                        break;
                                    case 7:
                                        emotion.setText("불안함");

                                        break;
                                }

                            }
                            int lastDay1 = results.getInt("lastday");
//                            Log.e("Server!!!", "서버콜 라스트데이: "+lastDay1);
                            bindingData(results);


                            /*    SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());  */
                            sharedPreferenceUtil.setLastDay(lastDay1);
                            Log.e("Shared!!!", "타임라인 라스트데이: "+sharedPreferenceUtil.getLastDay());
/*

                            if(sharedPreferenceUtil.getLastDay() != lastDay) {
                                lastDay = sharedPreferenceUtil.getLastDay();
                            }
                            Log.e("SharedPreference!!!!: " , lastDay+ " 라스트데이.");
*/







                            //TODO switch case문으로 finish_date 값 null 이면 "시작하지 않음"으로 처리


//
//                            timelineObject = new TimelineObject(results.getString("name"),results.getString("birth"), userEmotion, results.getString("gender"),
//                                    "finish_date1", "finish_date2", "finish_date3", "finish_date4", "finish_date5", "finish_date6",
//                                    "finish_date7", "finish_date8", "finish_date9","finish_date10","after_motion1", "after_motion2", "after_motion3", "after_motion4",
//                                    "after_motion5", "after_motion6", "after_motion7", "after_motion8", "after_motion9","before_motion1","before_motion2","before_motion3","before_motion4","before_motion5",
//                                    "before_motion6","before_motion7","before_motion8","before_motion9"
//                            ); // 데이터 바인딩 4

//                            binding.setTimeline(timelineObject); // 데이터 바인딩 5


                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), data.get("results").toString(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "서버오류입니다", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void bindingData(JSONObject results) {
        try {
            int surveyBefore = results.getInt("survey_before");
            int surveyAfter = results.getInt("survey_after");
            int lastDay = results.getInt("lastday");
            JSONArray info = results.getJSONArray("info");

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            LinearLayout beforeSurveyItem = (LinearLayout) inflater.inflate(R.layout.item_timeline_survey, null);
            TextView beforeName = beforeSurveyItem.findViewById(R.id.timeline_survey_t);
            ImageView beforeIcon = beforeSurveyItem.findViewById(R.id.timeline_icon);
            TextView beforeDate = beforeSurveyItem.findViewById(R.id.timeline_survey_date);
            ImageView beforeArrowButton = beforeSurveyItem.findViewById(R.id.timeline_survey_arrow);
            if (surveyBefore == 1) {
                beforeIcon.setBackground(getDrawable(getActivity(), R.drawable.projectcomplete));
                beforeName.setTextColor(completeColor);
                beforeDate.setTextColor(completeColor);
                DateTime time = DateTime.parse(results.getString("survey_before_date"));
                beforeDate.setText(time.plusHours(9).toString("yyyy.MM.dd"));
            }else{
                beforeArrowButton.setVisibility(View.VISIBLE);
                beforeArrowButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity)getActivity()).fragment = new SurvayFragment();
                        ((MainActivity)getActivity()).ChangeFragment();
                    }
                });
            }
            contentsLayout.addView(beforeSurveyItem);
            for (int i = 0; i < 8; i++) {
                final int day = i + 1;
                final LinearLayout contentItem = (LinearLayout) inflater.inflate(R.layout.item_timeline_content, null);
                ImageView icon = contentItem.findViewById(R.id.timeline_icon);
                final TextView name = contentItem.findViewById(R.id.timeline_content_name);
                TextView state = contentItem.findViewById(R.id.timeline_content_state);
                TextView date = contentItem.findViewById(R.id.timeline_content_date);
                TextView detail = contentItem.findViewById(R.id.timeline_content_detail_btn);
                ImageView arrowButton = contentItem.findViewById(R.id.timeline_content_arrow);
                LinearLayout arrowButton2 = contentItem.findViewById(R.id.timeline_content_arrow_linear);
                final LinearLayout detailLayout = contentItem.findViewById(R.id.timeline_content_detail);
                TextView detailcontent1 = detailLayout.findViewById(R.id.content_detail_state1);
                TextView detailcontent2 = detailLayout.findViewById(R.id.content_detail_state2);
                TextView detailcontent3 = detailLayout.findViewById(R.id.content_detail_state3);
                ImageView detailicon3 = detailLayout.findViewById(R.id.content_detail_icon3);
                TextView detailcontent4 = detailLayout.findViewById(R.id.content_detail_state4);
                ImageView detailicon4 = detailLayout.findViewById(R.id.content_detail_icon4);
                switch (day) {
                    case 1:
                        name.setText("도입-자기 관찰하기");


                        break;
                    case 2:
                        name.setText("초기 트라우마-부정적 기억");
                        break;
                    case 3:
                        name.setText("초기 트라우마-긍정적 기억");
                        break;
                    case 4:
                        name.setText("초기 트라우마-감정 조절 배우기");
                        break;
                    case 5:
                        name.setText("빅 트라우마-트라우마 정화I");
                        break;
                    case 6:
                        name.setText("빅 트라우마-트라우마 정화II");
                        break;
                    case 7:
                        name.setText("빅 트라우마-자아 대면하기");
                        break;
                    case 8:
                        name.setText("빅 트라우마-자아 회복하기");
                        break;
                    default:
                        name.setText("빅 트라우마-자아 회복하기");
                        break;
                }

                if (day <= lastDay) {
                    detail.setVisibility(View.VISIBLE);
                    state.setVisibility(View.VISIBLE);
                    icon.setBackground(getDrawable(getActivity(), R.drawable.projectcomplete));
                    name.setTextColor(completeColor);
                    detail.setTextColor(completeColor);
                    state.setTextColor(completeColor);
                    date.setTextColor(completeColor);
                    JSONObject data = info.getJSONObject(i);
                    if (data != null) {
                        DateTime time = DateTime.parse(data.getString("day_latest"));
                        date.setText(time.plusHours(9).toString("yyyy.MM.dd"));
                        if (data.getInt("content_complete") == 1) {
                            detailcontent1.setText("완료");
                        }
                        if (!data.isNull("picture_1") && !data.isNull("picture_4")) {
                            detailcontent2.setText("모두 완료");
                        }
                        if (!data.isNull("emotion_2")) {
                            switch (data.getInt("emotion_2")) {
                                case 1:
                                    detailicon3.setBackground(getDrawable(getActivity(), R.drawable.selactivity));
                                    detailcontent3.setText("활기참");
                                    break;
                                case 2:
                                    detailicon3.setBackground(getDrawable(getActivity(), R.drawable.selcalm));
                                    detailcontent3.setText("평온함");
                                    break;
                                case 3:
                                    detailicon3.setBackground(getDrawable(getActivity(), R.drawable.selhappy));
                                    detailcontent3.setText("행복함");
                                    break;
                                case 4:
                                    detailicon3.setBackground(getDrawable(getActivity(), R.drawable.selsoso));
                                    detailcontent3.setText("보통");
                                    break;
                                case 5:
                                    detailicon3.setBackground(getDrawable(getActivity(), R.drawable.selsadly));
                                    detailcontent3.setText("우울함");
                                    break;
                                case 6:
                                    detailicon3.setBackground(getDrawable(getActivity(), R.drawable.selangry));
                                    detailcontent3.setText("화가남");
                                    break;
                                case 7:
                                    detailicon3.setBackground(getDrawable(getActivity(), R.drawable.selanxious));
                                    detailcontent3.setText("불안함");
                                    break;
                                default:
                                    detailicon3.setBackground(getDrawable(getActivity(), R.drawable.selanxious));
                                    detailcontent3.setText("불안함");
                                    break;

                            }
                        }
                        if (!data.isNull("emotion_5")) {
                            switch (data.getInt("emotion_5")) {
                                case 1:
                                    detailicon4.setBackground(getDrawable(getActivity(), R.drawable.selactivity));
                                    detailcontent4.setText("활기참");
                                    break;
                                case 2:
                                    detailicon4.setBackground(getDrawable(getActivity(), R.drawable.selcalm));
                                    detailcontent4.setText("평온함");
                                    break;
                                case 3:
                                    detailicon4.setBackground(getDrawable(getActivity(), R.drawable.selhappy));
                                    detailcontent4.setText("행복함");
                                    break;
                                case 4:
                                    detailicon4.setBackground(getDrawable(getActivity(), R.drawable.selsoso));
                                    detailcontent4.setText("보통");
                                    break;
                                case 5:
                                    detailicon4.setBackground(getDrawable(getActivity(), R.drawable.selsadly));
                                    detailcontent4.setText("우울함");
                                    break;
                                case 6:
                                    detailicon4.setBackground(getDrawable(getActivity(), R.drawable.selangry));
                                    detailcontent4.setText("화가남");
                                    break;
                                case 7:
                                    detailicon4.setBackground(getDrawable(getActivity(), R.drawable.selanxious));
                                    detailcontent4.setText("불안함");
                                    break;
                                default:
                                    detailicon4.setBackground(getDrawable(getActivity(), R.drawable.selanxious));
                                    detailcontent4.setText("불안함");
                                    break;

                            }
                        }
                    }
                    detail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (detailLayout.getVisibility() == View.GONE) {
                                detailLayout.setVisibility(View.VISIBLE);
                            } else {
                                detailLayout.setVisibility(View.GONE);
                            }
                        }
                    });
                    if (day == lastDay) {
                        arrowButton2.setVisibility(View.VISIBLE);
                        icon.setImageDrawable(getDrawable(getActivity(), R.drawable.projectprogress));
                        //detail.setVisibility(View.GONE);
                        state.setText("진행중");
                        final int nowstate = data.getInt("state");



                        arrowButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = null;
                                switch (stateProcess){

                                    case 0:
                                        /** 프래그먼트에서 프래그먼트로 . (extends BaseFragment 필수)**/
                                        startFragment(getFragmentManager(), SurvayFragment.class);

                                        break;

                                    case 1:
                                        intent = new Intent(getActivity(), CameraActivity.class);

                                        break;

                                    case 2:
                                        intent = new Intent(getActivity(), EmotionActivity.class);

                                        break;
                                    case 3:
                                        intent = new Intent(getActivity(), MediaplayerActivity.class);

                                        break;
                                    case 4:
                                        intent = new Intent(getActivity(), CameraActivity.class);

                                        break;
                                    case 5:
                                        intent = new Intent(getActivity(), EmotionActivity.class);

                                        break;
                                    case 6:
                                        Toast.makeText(HealerContext.getContext(), "테스트 수정필요!",Toast.LENGTH_LONG);
                                        break;



                                }

                                intent.putExtra("day", day);
                                intent.putExtra("state", stateProcess);
                                startActivity(intent);


                               /* SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());

                                if(sharedPreferenceUtil.getProcess() != stateProcess)
                                    sharedPreferenceUtil.setProcess(stateProcess);
                                Log.e("shared Timeline: ", sharedPreferenceUtil.getProcess()+" , 449번줄");*/
                            }
                        });
                    }
                }
                contentsLayout.addView(contentItem);
            }
            LinearLayout afterSurveyItem = (LinearLayout) inflater.inflate(R.layout.item_timeline_survey2, null);
            ImageView afterIcon = (ImageView) afterSurveyItem.findViewById(R.id.timeline_icon2);
            TextView afterDate = afterSurveyItem.findViewById(R.id.timeline_survey_date2);
            TextView afterName = afterSurveyItem.findViewById(R.id.timeline_survey_t2);
            ImageView afterArrowButton = afterSurveyItem.findViewById(R.id.timeline_survey_arrow2);
            if (surveyAfter == 1) {
                afterIcon.setBackground(getDrawable(getActivity(), R.drawable.projectcomplete));

//                Toast.makeText(HealerContext.getContext(), "모든 컨텐츠가 완료되었습니다. 서베이에서 우측 상단의 이메일 전송 버튼을 눌러주세요.", Toast.LENGTH_SHORT).show();
                afterName.setTextColor(completeColor);
                afterDate.setTextColor(completeColor);
                DateTime time = DateTime.parse(results.getString("survey_after_date"));
                afterDate.setText(time.plusHours(9).toString("yyyy.MM.dd"));
            }else if(lastDay == 9 && surveyAfter == 0){

                afterArrowButton.setVisibility(View.VISIBLE);
                afterArrowButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity)getActivity()).fragment = new SurvayFragment();
                        ((MainActivity)getActivity()).ChangeFragment();
                    }
                });
            }

            contentsLayout.addView(afterSurveyItem);
        } catch (JSONException e) {
            Toast.makeText(getActivity(), "다시 시도해주세요. JSON오류입니다", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    public int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.M) {
            return context.getColor(id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Drawable getDrawable(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    public void getProcess(){

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

                            stateProcess= results.getInt("latest_state");


                            SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());

                            sharedPreferenceUtil.setProcess(stateProcess);
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
    }



}

