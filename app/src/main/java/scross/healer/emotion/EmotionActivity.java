package scross.healer.emotion;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import scross.healer.BaseActivity;
import scross.healer.R;

/**
 * Created by hanee on 2017-08-01.
 */

public class EmotionActivity extends BaseActivity{
    public EmotionActivity() {
    }

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);





        Intent intent = new Intent();

        int state = intent.getIntExtra("state", 3);

        if (state == 2) {
            EmotionDialog dialog = new EmotionDialog();
            dialog.show(getFragmentManager(), "emotion dialog");


        } else if (state == 5) {
            EmotionDialog dialog = new EmotionDialog();
            dialog.show(getFragmentManager(), "emotion dialog");
        }

        //TODO 받아온 state 다이얼로그에 전달하고, 다이얼로그에서 확인 버튼 누르면 네트워크 통신 후 state값 다음 화면으로 전달!.
        //TODO 프래그먼트 잘 붙었는지 확인.


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id., fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
