package scross.healer.survay;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scross.healer.BaseActivity;
import scross.healer.BaseFragment;
import scross.healer.HealerContext;
import scross.healer.MainActivity;
import scross.healer.R;
import scross.healer.SharedPreferenceUtil;
import scross.healer.emotion.EmotionDialog;
import scross.healer.networkService.GetNowDayProcess;
import scross.healer.networkService.NetworkApi;
import scross.healer.networkService.NetworkService;
import scross.healer.profile.ProfileDialogFragment;

/**
 * Created by hanee on 2017-07-18.
 */

public class SurvayFragment extends BaseFragment implements View.OnClickListener {
    SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());


    private final static String LINK1 = "https://goo.gl/forms/AFrF3wbgOI3qrUgK2";
    private final static String LINK2 = "https://goo.gl/forms/XoVCQLiYsBV8JWi23";

    Boolean beforeSuveySuc = false;
    Boolean afterSuveySuc = false;

    NetworkService apiService;
    Button beforeSurvayBtn;
    Button afterSurvayBtn;
    Button emailSend;
    ImageView firstSurveyCheck;
    ImageView lastSurveyCheck;

    //    private WebView mWebView;
    int phone;
    String link;
    int finishCheck;
    int day;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        getLatestDay();
        /*
        GetNowDayProcess getNowDayProcess = new GetNowDayProcess();
        finishCheck = getNowDayProcess.getLatestDay();
        Log.e("finish Check: ", finishCheck+"");*/


        Network();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar

        inflater.inflate(R.menu.actionbar_menu_survay, menu);


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                ProfileDialogFragment dialog = new ProfileDialogFragment();
                dialog.show(getFragmentManager(), "Profile Update test");
                return true;
            case R.id.email_send:


                if (day == 9) {
                    Network2();
                } else {
                    Toast.makeText(HealerContext.getContext(), "모든 컨텐츠를 아직 완료하지 않았습니다.", Toast.LENGTH_SHORT).show();
                }


                return true;

            default:
                return true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survay, container, false);

    /*    mWebView = (WebView)view.findViewById(R.id.webview);

        mWebView.setVisibility(View.GONE);
*/

        firstSurveyCheck = (ImageView) view.findViewById(R.id.first_survay_check);
        lastSurveyCheck = (ImageView) view.findViewById(R.id.last_survay_check);
        beforeSurvayBtn = (Button) view.findViewById(R.id.before_survay_btn);
        afterSurvayBtn = (Button) view.findViewById(R.id.after_survay_btn);

        beforeSurvayBtn.setOnClickListener(this);
        afterSurvayBtn.setOnClickListener(this);

/*        int surveyState = sharedPreferenceUtil.getSurveyState();

        if(surveyState == 1 || surveyState == 2){

            beforeSurvayBtn.setVisibility(View.GONE);

            beforeSurvayBtn.setEnabled(false);
            firstSurveyCheck.setVisibility(View.VISIBLE);
        }
        if(surveyState == 2){
            afterSurvayBtn.setVisibility(View.GONE);
            afterSurvayBtn.setEnabled(false);
            lastSurveyCheck.setVisibility(View.VISIBLE);

        }*/

        return view;
    }

    public SurvayFragment() {
        super();
    }


    @Override
    public void onResume() {
        super.onResume();

        int surveyState = sharedPreferenceUtil.getSurveyState();
/*
        if(surveyState == 1 || surveyState == 2){

            beforeSurvayBtn.setVisibility(View.GONE);

            beforeSurvayBtn.setEnabled(false);
        }
        if(surveyState == 2){
            afterSurvayBtn.setBackgroundResource(R.drawable.after_survey_suc);
            afterSurvayBtn.setEnabled(false);

        }*/


        beforeSurvayBtn.setEnabled(false);
        afterSurvayBtn.setEnabled(false);

/*

        if(surveyState == 1 || surveyState == 2){

            beforeSurvayBtn.setVisibility(View.GONE);

            beforeSurvayBtn.setEnabled(false);
            firstSurveyCheck.setVisibility(View.VISIBLE);
        }
        if(surveyState == 2){
            afterSurvayBtn.setVisibility(View.GONE);
            afterSurvayBtn.setEnabled(false);
            lastSurveyCheck.setVisibility(View.VISIBLE);

        }*/

    }

    public void Network() {

        apiService = NetworkApi.getInstance(getActivity()).getServce();

        Call<ResponseBody> surveyState = apiService.surveyState();
        surveyState.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null) { //JSONObject(response.body().string()) 이게 내가 보낸 json 받는 부분임

                        JSONObject data = new JSONObject(response.body().string());

                        String code = data.get("code").toString();
                        if (code.equals("1")) {

                            JSONObject results = data.getJSONObject("results");

                            beforeSuveySuc = results.getBoolean("survey_before");
                            Log.e("before survey: ", "" + results.getBoolean("survey_before"));
                            afterSuveySuc = results.getBoolean("survey_after");

                            if (beforeSuveySuc == true) {
//                                sharedPreferenceUtil.setSurveyState(1);


                                beforeSurvayBtn.setVisibility(View.GONE);

                                firstSurveyCheck.setVisibility(View.VISIBLE);
                            } else {
                                beforeSurvayBtn.setEnabled(true);
                            }
                            if (afterSuveySuc == true) {
//                                sharedPreferenceUtil.setSurveyState(2);
                                afterSurvayBtn.setVisibility(View.GONE);
                                afterSurvayBtn.setEnabled(false);
                                lastSurveyCheck.setVisibility(View.VISIBLE);
                            } else {
                                afterSurvayBtn.setEnabled(true);
                            }


                        } else {
                            Toast.makeText(HealerContext.getContext(), "설문 내용 저장에 실패하였습니다. 다시 시도 해주세요", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(HealerContext.getContext(), "서버오류입니다.", Toast.LENGTH_SHORT).show();
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

    public void Network2() {

        apiService = NetworkApi.getInstance(getActivity()).getServce();

        Call<ResponseBody> SendEmail = apiService.SendEmail();
        SendEmail.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null) { //JSONObject(response.body().string()) 이게 내가 보낸 json 받는 부분임
                        String code = new JSONObject(response.body().string()).get("code").toString();
                        if (code.equals("1")) {


                        } else {
                            Toast.makeText(HealerContext.getContext(), "설문 내용 저장에 실패하였습니다. 다시 시도 해주세요", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(HealerContext.getContext(), "서버오류입니다.", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.before_survay_btn:
/*

                mWebView.setVisibility(View.VISIBLE);
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.loadUrl("http://google.com");
                mWebView.setWebViewClient(new WebViewClientClass());
                mWebView.setVerticalScrollBarEnabled(true);

*//*

                String getUrl1 = "https://goo.gl/forms/AFrF3wbgOI3qrUgK2";

                setLink("https://goo.gl/forms/AFrF3wbgOI3qrUgK2");
*/

                sharedPreferenceUtil.setSurveyLink(LINK1);
/*
                Bundle bundle = new Bundle();
                bundle.putString("getUrl1", "https://goo.gl/forms/AFrF3wbgOI3qrUgK2");
                SurveyWebView webView = new SurveyWebView();
                webView.setArguments(bundle);*/
/*
                webView.newInstance(getUrl1);
                SurveyWebView.newInstance("https://goo.gl/forms/AFrF3wbgOI3qrUgK2");*/
                /*
                Bundle bundle = new Bundle();
                bundle.putString("setUrl", "https://goo.gl/forms/AFrF3wbgOI3qrUgK2");
                SurveyWebView sw1= new SurveyWebView();
                sw1.setArguments(bundle);*/

                startFragment(getFragmentManager(), SurveyWebView.class);


//                Network();
                break;

            case R.id.after_survay_btn:

/*
                SurveyWebView sw2 = new SurveyWebView();
                Bundle bundle1 = new Bundle();
                bundle1.putString("setUrl", "https://goo.gl/forms/XoVCQLiYsBV8JWi23");
                sw2.setArguments(bundle1);
*/

                sharedPreferenceUtil.setSurveyLink(LINK2);

                startFragment(getFragmentManager(), SurveyWebView.class);
//                Network2();
                break;
        }

    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);


            return true;
        }
    }


    public void getLatestDay() {
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
    }

}
