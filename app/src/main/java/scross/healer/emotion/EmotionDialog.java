package scross.healer.emotion;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scross.healer.HealerContext;
import scross.healer.MainActivity;
import scross.healer.R;
import scross.healer.SharedPreferenceUtil;
import scross.healer.media.MediaplayerActivity;
import scross.healer.networkService.EmotionEntityObject;
import scross.healer.networkService.NetworkApi;
import scross.healer.networkService.NetworkService;

/**
 * Created by gta2v on 2017-07-23.
 * 콘텐츠 시작 전/후 감정상태 다이얼로그
 */

public class EmotionDialog extends DialogFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    /* implements , RadioGroup.OnCheckedChangeListener*/

    SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());


    private EmotionEntityObject entityObject;
//    private LinearLayout emotion1, emotion2, emotion3, emotion4, emotion5, emotion6, emotion7;
    private ToggleButton iv1, iv2, iv4, iv3,  iv5, iv6, iv7;
    private Button btn1, btn2;

    int emotionValue =0;
    int state;
    String lastDay;


    NetworkService apiService;

    public static EmotionDialog newInstance(int state) {

        EmotionDialog emotionDialog = new EmotionDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        emotionDialog.setArguments(bundle);
//        Log.e("st emotionDialog get1", state+"");



        return emotionDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.CustomDialog);

        Bundle bundle = getArguments();
        state = bundle.getInt("state");

        Log.e("st emotionDialog get2", state+"");



    }




    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.6f;
        window.setAttributes(params);
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_emotion_dialog, container, false);


        if (entityObject == null) {
            entityObject = new EmotionEntityObject();
        }
/*

        emotion1 = (LinearLayout) view.findViewById(R.id.emotion_btn1);
        emotion2 = (LinearLayout) view.findViewById(R.id.emotion_btn2);
        emotion3 = (LinearLayout) view.findViewById(R.id.emotion_btn3);
        emotion4 = (LinearLayout) view.findViewById(R.id.emotion_btn4);
        emotion5 = (LinearLayout) view.findViewById(R.id.emotion_btn5);
        emotion6 = (LinearLayout) view.findViewById(R.id.emotion_btn6);
        emotion7 = (LinearLayout) view.findViewById(R.id.emotion_btn7);
*/

        btn1 = (Button) view.findViewById(R.id.emotion_success_btn);
        btn2 = (Button) view.findViewById(R.id.emotion_cancel_btn);


        iv1 = (ToggleButton) view.findViewById(R.id.emotion_img1);
        iv2 = (ToggleButton) view.findViewById(R.id.emotion_img2);
        iv3 = (ToggleButton) view.findViewById(R.id.emotion_img3);
        iv4 = (ToggleButton) view.findViewById(R.id.emotion_img4);
        iv5 = (ToggleButton) view.findViewById(R.id.emotion_img5);
        iv6 = (ToggleButton) view.findViewById(R.id.emotion_img6);
        iv7 = (ToggleButton) view.findViewById(R.id.emotion_img7);


        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        iv5.setOnClickListener(this);
        iv6.setOnClickListener(this);
        iv7.setOnClickListener(this);


        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

//        ERadioGroup er = new ERadioGroup(iv1, iv2, iv3, iv4, iv5, iv6, iv7);


/*
        rd = (RadioGroup) view.findViewById(R.id.emotion_radio_btn);
        rd.setOnCheckedChangeListener(this); // 라디오버튼을 눌렸을때의 반응*/

        lastDay = String.valueOf(sharedPreferenceUtil.getLastDay());


        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.emotion_img1:
                if(iv1.isChecked()){

                    iv2.setChecked(false);
                    iv3.setChecked(false);
                    iv4.setChecked(false);
                    iv5.setChecked(false);
                    iv6.setChecked(false);
                    iv7.setChecked(false);

                    emotionValue = 1;
                }else if(iv1.isChecked() == false){

                    iv2.setChecked(false);
                    iv3.setChecked(false);
                    iv4.setChecked(false);
                    iv5.setChecked(false);
                    iv6.setChecked(false);
                    iv7.setChecked(false);
                    emotionValue = 0;
                }
                break;



            case R.id.emotion_img2:
                if(iv2.isChecked()){

                    iv1.setChecked(false);
                    iv3.setChecked(false);
                    iv4.setChecked(false);
                    iv5.setChecked(false);
                    iv6.setChecked(false);
                    iv7.setChecked(false);

                    emotionValue = 2;
                }else if(iv2.isChecked() == false){

                    iv1.setChecked(false);
                    iv3.setChecked(false);
                    iv4.setChecked(false);
                    iv5.setChecked(false);
                    iv6.setChecked(false);
                    iv7.setChecked(false);
                    emotionValue = 0;

                }
                break;


            case R.id.emotion_img3:
                if(iv3.isChecked()){

                    iv1.setChecked(false);
                    iv2.setChecked(false);
                    iv4.setChecked(false);
                    iv5.setChecked(false);
                    iv6.setChecked(false);
                    iv7.setChecked(false);

                    emotionValue = 3;
                }else if(iv3.isChecked() == false){

                    iv1.setChecked(false);
                    iv2.setChecked(false);
                    iv4.setChecked(false);
                    iv5.setChecked(false);
                    iv6.setChecked(false);
                    iv7.setChecked(false);
                    emotionValue = 0;

                }
                break;

            case R.id.emotion_img4:
                if(iv4.isChecked() == true || iv4.isChecked()){
//                    Toast.makeText(HealerContext.getContext(), "버튼4 눌림1.", Toast.LENGTH_SHORT).show();

                    iv1.setChecked(false);
                    iv2.setChecked(false);
                    iv3.setChecked(false);
                    iv5.setChecked(false);
                    iv6.setChecked(false);
                    iv7.setChecked(false);

                    emotionValue = 4;

                }else if(iv4.isChecked() == false){
//                    Toast.makeText(HealerContext.getContext(), "버튼4 눌림2.", Toast.LENGTH_SHORT).show();

                    iv1.setChecked(false);
                    iv2.setChecked(false);
                    iv3.setChecked(false);
                    iv5.setChecked(false);
                    iv6.setChecked(false);
                    iv7.setChecked(false);
                    emotionValue = 0;

                }/*
                if(iv4.isClickable() || iv4.isClickable() == true){
                    Toast.makeText(HealerContext.getContext(), "isClickable true", Toast.LENGTH_SHORT).show();

                }else if(iv4.isClickable() == false){
                    Toast.makeText(HealerContext.getContext(), "isClickable false", Toast.LENGTH_SHORT).show();


                }
                if (iv4.isActivated() || iv4.isActivated() == true){
                    Toast.makeText(HealerContext.getContext(), "isActivated true", Toast.LENGTH_SHORT).show();

                }else if(iv4.isActivated() == false){
                    Toast.makeText(HealerContext.getContext(), "isActivated false", Toast.LENGTH_SHORT).show();

                }
                if(iv4.isEnabled() || iv4.isEnabled() == true){
                    Toast.makeText(HealerContext.getContext(), "isEnabled true", Toast.LENGTH_SHORT).show();

                }else if(iv4.isEnabled() == false){
                    Toast.makeText(HealerContext.getContext(), "isEnabled false", Toast.LENGTH_SHORT).show();

                }*/


                break;


            case R.id.emotion_img5:
                if(iv5.isChecked()){

                    iv2.setChecked(false);
                    iv3.setChecked(false);
                    iv4.setChecked(false);
                    iv1.setChecked(false);
                    iv6.setChecked(false);
                    iv7.setChecked(false);

                    emotionValue = 5;
                }else if(iv5.isChecked() == false){

                    iv2.setChecked(false);
                    iv3.setChecked(false);
                    iv4.setChecked(false);
                    iv1.setChecked(false);
                    iv6.setChecked(false);
                    iv7.setChecked(false);
                    emotionValue = 0;

                }
                break;


            case R.id.emotion_img6:
                if(iv6.isChecked()){

                    iv2.setChecked(false);
                    iv3.setChecked(false);
                    iv4.setChecked(false);
                    iv5.setChecked(false);
                    iv1.setChecked(false);
                    iv7.setChecked(false);

                    emotionValue = 6;
                }else if(iv6.isChecked() == false){

                    iv2.setChecked(false);
                    iv3.setChecked(false);
                    iv4.setChecked(false);
                    iv5.setChecked(false);
                    iv1.setChecked(false);
                    iv7.setChecked(false);
                    emotionValue = 0;

                }
                break;


            case R.id.emotion_img7:
                if(iv7.isChecked()){

                    iv2.setChecked(false);
                    iv3.setChecked(false);
                    iv4.setChecked(false);
                    iv5.setChecked(false);
                    iv6.setChecked(false);
                    iv1.setChecked(false);

                    emotionValue = 7;
                }else if(iv7.isChecked() == false){

                    iv2.setChecked(false);
                    iv3.setChecked(false);
                    iv4.setChecked(false);
                    iv5.setChecked(false);
                    iv6.setChecked(false);
                    iv1.setChecked(false);
                    emotionValue = 0;

                }
                break;



            case R.id.emotion_success_btn:



                network();

                break;

            case R.id.emotion_cancel_btn:
                dismiss();
                break;




        }
    }


    public void network(){

        if(emotionValue == 0){

            Toast.makeText(HealerContext.getContext(), "감정 선택을 해주세요!", Toast.LENGTH_SHORT).show();

        }else if(state == 2 ) {

            apiService = NetworkApi.getInstance(getActivity()).getServce();

                Call<ResponseBody> process2 = apiService.process2(lastDay, emotionValue);
            process2.enqueue(new Callback<ResponseBody>() {


                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.body() != null) { //JSONObject(response.body().string()) 이게 내가 보낸 json 받는 부분임
                                String code = new JSONObject(response.body().string()).get("code").toString();
                                if (code.equals("1")) {

                                    Toast.makeText(HealerContext.getContext(), "감정 선택이 완료되었습니다!", Toast.LENGTH_SHORT).show();




                                        state = state + 1;

                                        if (sharedPreferenceUtil.getProcess() != state) {
                                            sharedPreferenceUtil.setProcess(state);

//            state = sharedPreferenceUtil.getProcess();
                                        }
                                        Log.e("SharedPreference!!!!: ", sharedPreferenceUtil.getProcess() + " EmotionDialog. 감정선택 완료.");


                                        Intent intent1 = new Intent(HealerContext.getContext(), MediaplayerActivity.class);
                                        intent1.putExtra("state", state);
                                        startActivity(intent1);

                                        dismiss();






                                } else {
                                    Toast.makeText(HealerContext.getContext(), "감정 선택에 실패했습니다", Toast.LENGTH_SHORT).show();
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
        else if(state == 5){


            apiService = NetworkApi.getInstance(getActivity()).getServce();

            Call<ResponseBody> process5 = apiService.process5(lastDay, emotionValue);
            process5.enqueue(new Callback<ResponseBody>() {


                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.body() != null) { //JSONObject(response.body().string()) 이게 내가 보낸 json 받는 부분임
                            String code = new JSONObject(response.body().string()).get("code").toString();
                            if (code.equals("1")) {

                                Toast.makeText(HealerContext.getContext(), "감정 선택이 완료되었습니다!", Toast.LENGTH_SHORT).show();





                                    state = 1;

                                sharedPreferenceUtil.setProcess(state);

                                    Intent intent1 = new Intent(HealerContext.getContext(), MainActivity.class);
                                    intent1.putExtra("state", state);
                                    Toast.makeText(HealerContext.getContext(), "금일 과정은 모두 종료되었습니다. 내일 다시 진행해주세요",Toast.LENGTH_LONG);

                                    startActivity(intent1);
                                    dismiss();





                            } else {
                                Toast.makeText(HealerContext.getContext(), "감정 선택에 실패했습니다", Toast.LENGTH_SHORT).show();
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
    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
/*
        switch (i){
            case R.id.emotion_btn1:

                iv1.toggle();
                Toast.makeText(HealerContext.getContext(), "버튼1 눌림.", Toast.LENGTH_SHORT).show();

                if(iv1.isChecked() == true){
                    iv1.setBackgroundResource(R.drawable.selactivity);
                }else{
                    iv1.setBackgroundResource(R.drawable.activitymini);
                }


                *//*
                iv1.setChecked(true);
                iv2.setChecked(false);
                iv3.setChecked(false);
                iv4.setChecked(false);
                iv5.setChecked(false);
                iv6.setChecked(false);
                iv7.setChecked(false);*//*
                emotionValue = 1;
                break;
            case R.id.emotion_btn2:

                iv2.toggle();
                Toast.makeText(HealerContext.getContext(), "버튼2 눌림.", Toast.LENGTH_SHORT).show();
                if(iv2.isChecked() == true){
                    iv2.setBackgroundResource(R.drawable.selactivity);
                }else{
                    iv2.setBackgroundResource(R.drawable.activitymini);
                }
                *//*

                iv1.setChecked(false);
                iv2.setChecked(true);
                iv3.setChecked(false);
                iv4.setChecked(false);
                iv5.setChecked(false);
                iv6.setChecked(false);
                iv7.setChecked(false);*//*
                emotionValue = 2;
                break;
            case R.id.emotion_btn3:

                iv3.toggle();

                *//*
                iv1.setChecked(false);
                iv2.setChecked(false);
                iv3.setChecked(true);
                iv4.setChecked(false);
                iv5.setChecked(false);
                iv6.setChecked(false);
                iv7.setChecked(false);*//*
                emotionValue = 3;
                break;
            *//*case R.id.emotion_btn4:

                iv4.toggle();*//**//*
                iv1.setChecked(false);
                iv2.setChecked(false);
                iv3.setChecked(false);
                iv4.setChecked(true);
                iv5.setChecked(false);
                iv6.setChecked(false);
                iv7.setChecked(false);*//**//*
                emotionValue = 4;
                break;*//*
            case R.id.emotion_btn5:

                iv5.toggle();*//*
                iv1.setChecked(false);
                iv2.setChecked(false);
                iv3.setChecked(false);
                iv4.setChecked(false);
                iv5.setChecked(true);
                iv6.setChecked(false);
                iv7.setChecked(false);*//*
                emotionValue = 5;
                break;
            case R.id.emotion_btn6:
                iv6.toggle();*//*
                iv1.setChecked(false);
                iv2.setChecked(false);
                iv3.setChecked(false);
                iv4.setChecked(false);
                iv5.setChecked(false);
                iv6.setChecked(true);
                iv7.setChecked(false);*//*
                emotionValue = 6;
                break;
            case R.id.emotion_btn7:

                iv7.toggle();*//*
                iv1.setChecked(false);
                iv2.setChecked(false);
                iv3.setChecked(false);
                iv4.setChecked(false);
                iv5.setChecked(false);
                iv6.setChecked(false);
                iv7.setChecked(true);*//*
                emotionValue = 7;
                break;

        }*/
    }
}
