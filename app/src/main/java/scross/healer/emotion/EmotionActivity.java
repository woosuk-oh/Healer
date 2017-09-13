package scross.healer.emotion;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import scross.healer.BaseActivity;
import scross.healer.HealerContext;
import scross.healer.MainActivity;
import scross.healer.R;
import scross.healer.SharedPreferenceUtil;
import scross.healer.camera.TakePictureActivity;
import scross.healer.media.MediaplayerActivity;

/**
 * Created by hanee on 2017-08-01.
 */

public class EmotionActivity extends BaseActivity{
    public EmotionActivity() {
    }

    int state;
    int lastDay;

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id., fragment);
        fragmentTransaction.commit();




        Intent intent = getIntent();
        state = intent.getExtras().getInt("state");
        Log.e("state emotion get",state+"");






    }

    @Override
    protected void onResume() {
        super.onResume();



        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());

        if (sharedPreferenceUtil.getProcess() != state) {

            state = sharedPreferenceUtil.getProcess();
            Log.e("sharedPreference!!!!: ", state+" EmotionActivity onResume");

        }

        lastDay = sharedPreferenceUtil.getLastDay();
        Log.e("sharedPreference!!!!: ", lastDay+" EmotionActivity onResume LastDAY ?");


        if (state == 2 || state == 5) {

            //액티비티에서 다이얼로그 프래그먼트로 값 전달하기
            Bundle bundle = new Bundle();
            bundle.putInt("state", state);
            EmotionDialog dialog = new EmotionDialog();
            dialog.setArguments(bundle);

            dialog.newInstance(state);
            dialog.show(getFragmentManager(), "emotion dialog");
//            finish();

        }else if (state ==3){

           /* Intent intent1 = new Intent(getApplication(), MediaplayerActivity.class);
            intent1.putExtra("state", state);
            startActivity(intent1);*/
            finish();
            // 미디어플레이어에서 뒤로가기 하면 다시 스타트액티비티 되서 꼬임. 그냥 꺼버려야됨.

        }/*else if(state == 6 && lastDay < 8 ){
            Intent intent1 = new Intent(getApplication(), MainActivity.class);
            intent1.putExtra("state", state);
            Toast.makeText(HealerContext.getContext(), "금일 과정은 모두 종료되었습니다. 내일 다시 진행해주세요",Toast.LENGTH_LONG);

            startActivity(intent1);
            finish();
        }*/else{
            Toast.makeText(HealerContext.getContext(), "에러. 뒤로가기를 눌러주세요!",Toast.LENGTH_LONG);

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
