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
import scross.healer.networkService.NetworkApi;
import scross.healer.networkService.NetworkService;
import scross.healer.profile.ProfileDialogFragment;

/**
 * Created by hanee on 2017-07-18.
 */

public class SurvayFragment extends BaseFragment implements View.OnClickListener{


    NetworkService apiService;
    Button beforeSurvayBtn;
    Button afterSurvayBtn;
    Button emailSend;
//    private WebView mWebView;
    int phone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar

        inflater.inflate(R.menu.actionbar_menu_survay, menu);



        //TODO 네트워크 통신해서 이메일로 사진 전송해야됨!
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survay, container, false);

    /*    mWebView = (WebView)view.findViewById(R.id.webview);

        mWebView.setVisibility(View.GONE);
*/
        beforeSurvayBtn = (Button) view.findViewById(R.id.before_survay_btn);
        afterSurvayBtn = (Button) view.findViewById(R.id.after_survay_btn);

        beforeSurvayBtn.setOnClickListener(this);
        afterSurvayBtn.setOnClickListener(this);


        return view;
    }

    public SurvayFragment() {
        super();
    }


    public void Network(){
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());
        phone = sharedPreferenceUtil.getPhoneNum();

        apiService = NetworkApi.getInstance(getActivity()).getServce();

        Call<ResponseBody> surveyBefore = apiService.surveyBefore(phone);
        surveyBefore.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null) { //JSONObject(response.body().string()) 이게 내가 보낸 json 받는 부분임
                        String code = new JSONObject(response.body().string()).get("code").toString();
                        if (code.equals("1")) {

                            Toast.makeText(HealerContext.getContext(), "설문에 참여해주셔서 감사합니다!", Toast.LENGTH_SHORT).show();



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
    public void Network2(){
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());
        phone = sharedPreferenceUtil.getPhoneNum();

        apiService = NetworkApi.getInstance(getActivity()).getServce();

        Call<ResponseBody> surveyAfter = apiService.surveyAfter(phone);
        surveyAfter.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null) { //JSONObject(response.body().string()) 이게 내가 보낸 json 받는 부분임
                        String code = new JSONObject(response.body().string()).get("code").toString();
                        if (code.equals("1")) {

                            Toast.makeText(HealerContext.getContext(), "설문에 참여해주셔서 감사합니다!", Toast.LENGTH_SHORT).show();



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
        switch (view.getId())
        {
            case R.id.before_survay_btn:
/*

                mWebView.setVisibility(View.VISIBLE);
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.loadUrl("http://google.com");
                mWebView.setWebViewClient(new WebViewClientClass());
                mWebView.setVerticalScrollBarEnabled(true);

*/

                SurveyWebView.newInstance("https://goo.gl/forms/AFrF3wbgOI3qrUgK2");
                /*
                Bundle bundle = new Bundle();
                bundle.putString("setUrl", "https://goo.gl/forms/AFrF3wbgOI3qrUgK2");
                SurveyWebView sw1= new SurveyWebView();
                sw1.setArguments(bundle);*/


                startFragment(getFragmentManager(), SurveyWebView.class);



//                Network();
                break;

            case R.id.after_survay_btn:

                SurveyWebView sw2= new SurveyWebView();
                Bundle bundle1 = new Bundle();
                bundle1.putString("setUrl", "https://goo.gl/forms/XoVCQLiYsBV8JWi23");
                sw2.setArguments(bundle1);


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


}
