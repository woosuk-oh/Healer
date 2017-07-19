package scross.healer.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import scross.healer.BaseActivity;
import scross.healer.MainActivity;
import scross.healer.R;

/**
 * Created by hanee on 2017-07-18.
 */

public class LoginActivity extends BaseActivity {
    Handler mHandler = new Handler(Looper.getMainLooper());

    Button loginBtn;
    TextView signupBtn;
   // private BackPressCloseHandler backPressCloseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


                /*

                인텐트에 값 넣어 넘기기

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, ShoppingListDetailActivity.class); //찜리스트 디테일페이지로이동
                    intent.putExtra("listCode", shopList.listCode); //쇼핑리스트 listCode 넘겨줌
                    intent.putExtra("countryName", shopList.countryNameKor.toString());
                    intent.putExtra("targetUserCode",targetUserCode);
                    intent.putExtra("goodsCount",shopList.goodsCount);
                    intent.putExtra("type",1);

                    Log.e("shopListCode", shopList.listCode.toString());
                    Log.e("shopLisCount", shopList.goodsCount.toString());
                    context.startActivity(intent);

                }
            });
        }*/




            }
        });

        /** 회원가입 페이지로 이동 **/
        signupBtn = (TextView)  findViewById(R.id.signup_button);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });



    }


/*

        *//**
     * 자동로그인 기능
     **//*
        private void goLoginActivity() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //  Log.w("고로그인 이메일",em);
                    NetworkManager.getInstance().getLogin(MyApplication.getContext(), em, pw, new NetworkManager.OnResultListener<UserLoginResult>() {

                        @Override
                        public void onSuccess(Request request, UserLoginResult result) {
                            PropertyManager.getInstance().setId(result.getResult().getMem_id());
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFail(Request request, IOException exception) {
                            Toast.makeText(getApplication(), "이메일과 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });


//                if (PropertyManager.getInstance().getId()!=null){
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }else{
//                    startActivity(new Intent(SplashActivity.this, StartActivity.class));
                    finish();
                    // }


                }
            }, 2000);*/





    /** 백 버튼 핸들러 **/
/*


    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    public class BackPressCloseHandler {

        private long backKeyPressedTime = 0;
        private Toast toast;

        private Activity activity;

        public BackPressCloseHandler(Activity context) {
            this.activity = context;
        }

        public void onBackPressed() {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();
                showGuide();
                return;
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                SystemExit();
            }
        }

        public void SystemExit() {
            activity.moveTaskToBack(true);
            activity.finish();
            toast.cancel();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }

        public void showGuide() {
            toast = Toast.makeText(activity, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
*/

}
