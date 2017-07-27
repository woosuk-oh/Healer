package scross.healer.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.NetworkInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scross.healer.BaseActivity;
import scross.healer.MainActivity;
import scross.healer.R;
import scross.healer.networkService.NetworkApi;
import scross.healer.networkService.NetworkService;

/**
 * Created by hanee on 2017-07-18.
 */

public class LoginActivity extends BaseActivity {
    Handler mHandler = new Handler(Looper.getMainLooper());

    Button loginBtn;
    TextView signupBtn;
    EditText phoneInput;
    EditText passwordInput;
    NetworkService apiService;
   // private BackPressCloseHandler backPressCloseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        apiService = NetworkApi.getInstance(this).getServce();

        loginBtn = (Button) findViewById(R.id.login_btn);
        phoneInput = (EditText) findViewById(R.id.phone_input);
        passwordInput = (EditText) findViewById(R.id.password_input);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);

                if(phoneInput.getText().length() < 10){
                    Toast.makeText(LoginActivity.this, "핸드폰 번호를 제대로 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(passwordInput.getText().length() == 0){
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else {
                    int phone = Integer.valueOf(phoneInput.getText().toString());
                    String password = passwordInput.getText().toString();
                    Call<ResponseBody> postRate = apiService.login(phone, password);
                    postRate.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if (response.body() != null) { //JSONObject(response.body().string()) 이게 내가 보낸 json 받는 부분임
                                    String code = new JSONObject(response.body().string()).get("code").toString();
                                    if (code.equals("1")) {
                                        Toast.makeText(LoginActivity.this, "성공", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(LoginActivity.this, "로그인에 실패했습니다", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(LoginActivity.this, "서버오류입니다.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "서버오류입니다.", Toast.LENGTH_SHORT).show();
                            Log.d("value", t.getMessage());

                        }
                    });
                }


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
