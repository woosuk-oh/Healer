package scross.healer.timeline;

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

import com.google.gson.JsonObject;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.json.JSONArray;
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
import scross.healer.networkService.NetworkApi;
import scross.healer.networkService.NetworkService;
import scross.healer.profile.ProfileDialogFragment;

import static scross.healer.timeline.ContentNameHashMap.contentName;

/**
 * Created by hanee on 2017-07-18.
 */

public class TimelineFragment extends BaseFragment implements View.OnClickListener {

    public TimelineFragment() {
        super();
    }

    int lastDay = 1;
    NetworkService apiService;
    TextView name;
    TextView birth;
    TextView gender;
    TextView emotion;
    LinearLayout contentsLayout;
    int completeColor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        JodaTimeAndroid.init(getActivity());
        network();
        Log.e("ddd", "onCreate");

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
        completeColor = getColor(getActivity(), R.color.color5);
        return view;
    }


    //TODO 텍스트가 25자 이상이면 ...으로 표시
    //TODO 진행 완료한 컨텐츠는 content_name 옆에 +' 완료' 추가하기.
    //TODO 상세보기 누르면 왼쪽 아이콘이랑 오른쪽 리니어레이아웃 탑마진 주기


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
                            gender.setText(" / " + results.getString("gender") + "자");
                            if (!results.isNull("emotion")) {
                                emotion.setText(results.getInt("emotion") + "");
                            }
                            lastDay = results.getInt("lastday");
                            bindingData(results);


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
            if (surveyBefore == 1) {
                beforeIcon.setBackground(getDrawable(getActivity(), R.drawable.projectcomplete));
                beforeName.setTextColor(completeColor);
                beforeDate.setTextColor(completeColor);
                DateTime time = DateTime.parse(results.getString("survey_before_date"));
                beforeDate.setText(time.plusHours(9).toString("yyyy.MM.dd"));
            }
            contentsLayout.addView(beforeSurveyItem);
            for (int i = 0; i < 8; i++) {
                int day = i + 1;
                LinearLayout contentItem = (LinearLayout) inflater.inflate(R.layout.item_timeline_content, null);
                ImageView icon = contentItem.findViewById(R.id.timeline_icon);
                TextView name = contentItem.findViewById(R.id.timeline_content_name);
                TextView state = contentItem.findViewById(R.id.timeline_content_state);
                TextView date = contentItem.findViewById(R.id.timeline_content_date);
                TextView detail = contentItem.findViewById(R.id.timeline_content_detail_btn);
                ImageView arrowButton = contentItem.findViewById(R.id.timeline_content_arrow);
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
                    if(data != null){
                        DateTime time = DateTime.parse(data.getString("day_latest"));
                        date.setText(time.plusHours(9).toString("yyyy.MM.dd"));
                        if (data.getInt("content_complete") == 1) {
                            detailcontent1.setText("완료");
                        }
                        if (!data.isNull("picture_1") && !data.isNull("picture_4")) {
                            detailcontent2.setText("모두 완료");
                        }
                        if (!data.isNull("emotion_2")){
                            switch (data.getInt("emotion_2")){
                                case 1:
                                    detailicon3.setBackground(getDrawable(getActivity(),R.drawable.selactivity));
                                    detailcontent3.setText("활기참");
                                    break;
                                case 2:
                                    detailicon3.setBackground(getDrawable(getActivity(),R.drawable.selcalm));
                                    detailcontent3.setText("평온함");
                                    break;
                                case 3:
                                    detailicon3.setBackground(getDrawable(getActivity(),R.drawable.selhappy));
                                    detailcontent3.setText("행복함");
                                    break;
                                case 4:
                                    detailicon3.setBackground(getDrawable(getActivity(),R.drawable.selsoso));
                                    detailcontent3.setText("보통");
                                    break;
                                case 5:
                                    detailicon3.setBackground(getDrawable(getActivity(),R.drawable.selsadly));
                                    detailcontent3.setText("우울함");
                                    break;
                                case 6:
                                    detailicon3.setBackground(getDrawable(getActivity(),R.drawable.selangry));
                                    detailcontent3.setText("화가남");
                                    break;
                                case 7:
                                    detailicon3.setBackground(getDrawable(getActivity(),R.drawable.selanxious));
                                    detailcontent3.setText("불안함");
                                    break;
                                default:
                                    detailicon3.setBackground(getDrawable(getActivity(),R.drawable.selanxious));
                                    detailcontent3.setText("불안함");
                                    break;

                            }
                        }
                        if(!data.isNull("emotion_5")){
                            switch (data.getInt("emotion_5")){
                                case 1:
                                    detailicon4.setBackground(getDrawable(getActivity(),R.drawable.selactivity));
                                    detailcontent4.setText("활기참");
                                    break;
                                case 2:
                                    detailicon4.setBackground(getDrawable(getActivity(),R.drawable.selcalm));
                                    detailcontent4.setText("평온함");
                                    break;
                                case 3:
                                    detailicon4.setBackground(getDrawable(getActivity(),R.drawable.selhappy));
                                    detailcontent4.setText("행복함");
                                    break;
                                case 4:
                                    detailicon4.setBackground(getDrawable(getActivity(),R.drawable.selsoso));
                                    detailcontent4.setText("보통");
                                    break;
                                case 5:
                                    detailicon4.setBackground(getDrawable(getActivity(),R.drawable.selsadly));
                                    detailcontent4.setText("우울함");
                                    break;
                                case 6:
                                    detailicon4.setBackground(getDrawable(getActivity(),R.drawable.selangry));
                                    detailcontent4.setText("화가남");
                                    break;
                                case 7:
                                    detailicon4.setBackground(getDrawable(getActivity(),R.drawable.selanxious));
                                    detailcontent4.setText("불안함");
                                    break;
                                default:
                                    detailicon4.setBackground(getDrawable(getActivity(),R.drawable.selanxious));
                                    detailcontent4.setText("불안함");
                                    break;

                            }
                        }
                    }
                    detail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(detailLayout.getVisibility() == View.GONE){
                                detailLayout.setVisibility(View.VISIBLE);
                            }else{
                                detailLayout.setVisibility(View.GONE);
                            }
                        }
                    });
                    if (day == lastDay) {
                        arrowButton.setVisibility(View.VISIBLE);
                        icon.setImageDrawable(getDrawable(getActivity(), R.drawable.projectprogress));
                        //detail.setVisibility(View.GONE);
                        state.setText("진행중");
                        final int nowstate = data.getInt("state");
                        arrowButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = null;
                                switch (nowstate) {
                                    case 1:
                                        intent = new Intent(getActivity(), CameraActivity.class);
                                        break;
                                    case 2:
                                        intent = new Intent(getActivity(), CameraActivity.class);
                                        break;
                                    case 3:
                                        intent = new Intent(getActivity(), CameraActivity.class);
                                        break;
                                    case 4:
                                        intent = new Intent(getActivity(), CameraActivity.class);
                                        break;
                                    case 5:
                                        intent = new Intent(getActivity(), CameraActivity.class);
                                        break;
                                    case 6:
                                        intent = new Intent(getActivity(), CameraActivity.class);
                                        break;
                                    default:
                                        intent = new Intent(getActivity(), CameraActivity.class);
                                        break;
                                }
                                startActivity(intent);
                            }
                        });
                    }
                }
                contentsLayout.addView(contentItem);
            }
            LinearLayout afterSurveyItem = (LinearLayout) inflater.inflate(R.layout.item_timeline_survey, null);
            ImageView afterIcon = beforeSurveyItem.findViewById(R.id.timeline_icon);
            TextView afterDate = beforeSurveyItem.findViewById(R.id.timeline_survey_date);
            TextView afterName = beforeSurveyItem.findViewById(R.id.timeline_survey_t);
            if (surveyAfter == 1) {
                afterIcon.setBackground(getDrawable(getActivity(), R.drawable.projectcomplete));
                afterName.setTextColor(completeColor);
                afterDate.setTextColor(completeColor);
                DateTime time = DateTime.parse(results.getString("survey_after_date"));
                afterDate.setText(time.plusHours(9).toString("yyyy.MM.dd"));
            }

            contentsLayout.addView(afterSurveyItem);
        } catch (JSONException e) {
            Toast.makeText(getActivity(), "다시 시도해주세요. JSON오류입니다", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    public int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.M) {
            return context.getColor(id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public Drawable getDrawable(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }
}

