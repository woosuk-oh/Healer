package scross.healer.camera;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import scross.healer.BaseActivity;
import scross.healer.HealerContext;
import scross.healer.R;
import scross.healer.SharedPreferenceUtil;

/**
 * Created by gta2v on 2017-07-23.
 */

public class CameraActivity extends BaseActivity{


    private Button cameraStart;
    private ImageView cameraCheck;
    private TextView contentName;
    private TextView contentDay;
    private TextView contentBody;
    private TextView contentExplain;
    private Context context;
    private LinearLayout cameraBackground;

    final int ANIMATION_TIME1 = 500;
    final int ANIMATION_TIME2 = 650;
    final int ANIMATION_TIME3 = 1300;
    final int ANIMATION_TIME4 = 1950;



    // TODO 네트워크로 부터 state받아와야함. daystate는 메인액티비티에서 받아오고 인텐트로 전달받기!
    int state;
    int dayState = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_before_shoot_picture);


        Intent intent = getIntent();
        dayState = intent.getExtras().getInt("day");




        Log.e("dayState", dayState + "");
        state = intent.getExtras().getInt("state");
        Log.e("state Camera get", state + "");


        cameraStart = (Button) findViewById(R.id.camera_start);
        contentDay = (TextView) findViewById(R.id.content_day);
        contentName = (TextView) findViewById(R.id.content_name);
        contentBody = (TextView) findViewById(R.id.content_body);
        contentExplain = (TextView) findViewById(R.id.content_explain);
        cameraBackground = (LinearLayout) findViewById(R.id.camera_background);

        cameraCheck = (ImageView) findViewById(R.id.camera_check);

        //TODO 애니메이션 마저하기!
        RunAnimation();


        if (state == 1) {// 네트워크 통신해서 받아와야함. 1단계 임(감정상태 선택전, 컨텐츠 듣기전, ).

            contentExplain.setText("시작 전/후로 웃는 얼굴을 촬영 합니다.\n카메라를 보고 '치~즈'라고\n말하며 사진 촬영을 해주세요.");

            cameraStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(getApplication(), TakePictureActivity.class);
                    intent.putExtra("state", state);
                    Log.e("state Camera put", state + "");

                    SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());

                    if (sharedPreferenceUtil.getProcess() != state) {
                        sharedPreferenceUtil.setProcess(state);

//            state = sharedPreferenceUtil.getProcess();
                    }
                    Log.e("SharedPreference!!!!: ", state + " 프로세스.");


                    startActivity(intent);
                    finish();
                }
            });
            // 몇일차인지에 따라 컨텐츠 제목이랑 일자 수정!
            switch (dayState) {

                case 1:
                    cameraCheck.setVisibility(View.INVISIBLE);
                    contentDay.setText("1일차");
                    contentName.setText("도입");
                    contentBody.setText("자기 관찰하기");
                    cameraBackground.setBackgroundResource(R.drawable.day1);
                    break;
                case 2:
                    cameraCheck.setVisibility(View.INVISIBLE);
                    contentDay.setText("2일차");
                    contentName.setText("초기 트라우마");
                    contentBody.setText("부정적 기억 내보내기");
                    cameraBackground.setBackgroundResource(R.drawable.day2);

                    break;
                case 3:
                    cameraCheck.setVisibility(View.INVISIBLE);
                    contentDay.setText("3일차");
                    contentName.setText("초기 트라우마");
                    contentBody.setText("긍정적 정서 떠올리기");
                    cameraBackground.setBackgroundResource(R.drawable.day3);


                    break;
                case 4:
                    cameraCheck.setVisibility(View.INVISIBLE);
                    contentDay.setText("4일차");
                    contentName.setText("초기 트라우마");
                    contentBody.setText("감정 조절 배우기");
                    cameraBackground.setBackgroundResource(R.drawable.day4);


                    break;
                case 5:
                    cameraCheck.setVisibility(View.INVISIBLE);
                    contentDay.setText("5일차");
                    contentName.setText("빅 트라우마");
                    contentBody.setText("트라우마 정화 I");
                    cameraBackground.setBackgroundResource(R.drawable.day5);

                    break;
                case 6:
                    cameraCheck.setVisibility(View.INVISIBLE);
                    contentDay.setText("6일차");
                    contentName.setText("빅 트라우마");
                    contentBody.setText("트라우마 정화 II");
                    cameraBackground.setBackgroundResource(R.drawable.day6);

                    break;
                case 7:
                    cameraCheck.setVisibility(View.INVISIBLE);
                    contentDay.setText("7일차");
                    contentName.setText("빅 트라우마");
                    contentBody.setText("자아 대면하기");
                    cameraBackground.setBackgroundResource(R.drawable.day7);

                    break;
                case 8:
                    cameraCheck.setVisibility(View.INVISIBLE);
                    contentDay.setText("8일차");
                    contentName.setText("마무리");
                    contentBody.setText("자아 회복하기");
                    cameraBackground.setBackgroundResource(R.drawable.day8);

                    break;


            }

        } else if (state == 4) {// ?일차 4단계. 컨텐츠 듣기후,
            cameraCheck.setVisibility(View.VISIBLE);
            contentExplain.setText("수고하셨습니다.\n웃는 얼굴을 다시 한 번 촬영 합니다.\n전과 같이 '치~즈'를 외쳐주세요.");
            final Animation a3 = AnimationUtils.loadAnimation(this, R.anim.scale);
            a3.setStartOffset(ANIMATION_TIME1);
            a3.reset();
            cameraCheck.startAnimation(a3);



            switch (dayState) {

                case 1:
                    contentDay.setText("1일차");
                    contentName.setText("도입");
                    contentBody.setText("자기 관찰하기");
                    cameraBackground.setBackgroundResource(R.drawable.day1);

                    break;
                case 2:
                    contentDay.setText("2일차");
                    contentName.setText("초기 트라우마");
                    contentBody.setText("부정적 기억 내보내기");
                    cameraBackground.setBackgroundResource(R.drawable.day2);

                    break;
                case 3:
                    contentDay.setText("3일차");
                    contentName.setText("초기 트라우마");
                    contentBody.setText("긍정적 정서 떠올리기");
                    cameraBackground.setBackgroundResource(R.drawable.day3);

                    break;
                case 4:
                    contentDay.setText("4일차");
                    contentName.setText("초기 트라우마");
                    contentBody.setText("감정 조절 배우기");
                    cameraBackground.setBackgroundResource(R.drawable.day4);

                    break;
                case 5:
                    contentDay.setText("5일차");
                    contentName.setText("빅 트라우마");
                    contentBody.setText("트라우마 정화 I");
                    cameraBackground.setBackgroundResource(R.drawable.day5);

                    break;
                case 6:
                    contentDay.setText("6일차");
                    contentName.setText("빅 트라우마");
                    contentBody.setText("트라우마 정화 II");
                    cameraBackground.setBackgroundResource(R.drawable.day6);

                    break;
                case 7:
                    contentDay.setText("7일차");
                    contentName.setText("빅 트라우마");
                    contentBody.setText("자아 대면하기");
                    cameraBackground.setBackgroundResource(R.drawable.day7);

                    break;
                case 8:
                    contentDay.setText("8일차");
                    contentName.setText("마무리");
                    contentBody.setText("자아 회복하기");
                    cameraBackground.setBackgroundResource(R.drawable.day8);

                    break;


            }

            cameraStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(HealerContext.getContext(), TakePictureActivity.class);
                    intent.putExtra("state", 4);


                    SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());

                    if (sharedPreferenceUtil.getProcess() != state) {
                        sharedPreferenceUtil.setProcess(state);

//            state = sharedPreferenceUtil.getProcess();
                    }
                    Log.e("SharedPreference!!!!: ", state + " 프로세스.");


                    startActivity(intent);
                    finish();
                }
            });
        }

    }

    private void RunAnimation()
    {
        final Animation a = AnimationUtils.loadAnimation(this, R.anim.anim);
        final Animation a1 = AnimationUtils.loadAnimation(this, R.anim.anim);
        final Animation a2 = AnimationUtils.loadAnimation(this, R.anim.anim);
        a.reset();
        a1.reset();
        a2.reset();

        a.setStartOffset(ANIMATION_TIME2);
        a1.setStartOffset(ANIMATION_TIME3);
        a2.setStartOffset(ANIMATION_TIME4);


        final TextView tv = (TextView) findViewById(R.id.content_day);
        final TextView contentName = (TextView) findViewById(R.id.content_name);
        final TextView contentBody = (TextView) findViewById(R.id.content_body);
        final TextView contentExplain = (TextView) findViewById(R.id.content_explain);




        tv.startAnimation(a);
        contentName.startAnimation(a1);
        contentBody.startAnimation(a1);
        contentExplain.startAnimation(a2);





    }

}
