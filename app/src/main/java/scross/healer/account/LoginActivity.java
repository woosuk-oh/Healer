package scross.healer.account;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scross.healer.BaseActivity;
import scross.healer.HealerContext;
import scross.healer.MainActivity;
import scross.healer.R;
import scross.healer.SharedPreferenceUtil;
import scross.healer.home.HomeFragment;
import scross.healer.networkService.NetworkApi;
import scross.healer.networkService.NetworkService;

/**
 * Created by hanee on 2017-07-18.
 */

public class LoginActivity extends BaseActivity {
    Handler mHandler = new Handler(Looper.getMainLooper());
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    Button loginBtn;
    TextView signupBtn;
    EditText phoneInput;
    EditText passwordInput;

    int phone;
    String password;

    NetworkService apiService;

    // private BackPressCloseHandler backPressCloseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //권한획득
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    1);
        }


        setContentView(R.layout.activity_login);

        apiService = NetworkApi.getInstance(this).getServce();

        loginBtn = (Button) findViewById(R.id.login_btn);
        phoneInput = (EditText) findViewById(R.id.phone_input);

        /** (비밀번호 입력창) 텍스트 인풋 백그라운드가 있는 경우, password dot으로 표현!**/
        passwordInput = (EditText) findViewById(R.id.password_input);
        passwordInput.setTypeface(Typeface.DEFAULT);
        passwordInput.setTransformationMethod(new PasswordTransformationMethod());



        phoneInput.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
        phoneInput.addTextChangedListener(new PhoneNumberFormattingTextWatcher()); // 폰번호에 하이픈 붙이기


//
//        TextWatcher tw = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//
//                if (i1 == 13) {
//                    phoneInput.clearFocus();
//                    passwordInput.requestFocus();
//                }
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        };
//        phoneInput.addTextChangedListener(tw);

/*



        TextWatcher tw1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.e("onTextChanged 1", i+"");
                Log.e("onTextChanged 2", i1+"");
                Log.e("onTextChanged 3", i2+"");

                if(i == 3){
                    passwordInput.setFocusable(false);
                    loginBtn.setClickable(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        passwordInput.addTextChangedListener(tw1);*/


//        passwordInput.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD); //TODO 패스워드 *** 형식으로 바꿔야되는데 안먹음..
//        passwordInput.addTextChangedListener(new PasswordTransformationMethod());

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


                    phone = Integer.valueOf(phoneInput.getText().toString().replaceAll("-", ""));
                    password = passwordInput.getText().toString();
                    Log.e("password: ",""+ password);


                    int length = (int) Math.floor(Math.log10(phone) + 1);
                    Log.e("phone num", " "+length);

                    if(length == 10 || length == 11){

                        Log.e("phone: ",""+ phone);


                        Call<ResponseBody> postRate = apiService.login(phone, password);
                        postRate.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    if (response.body() != null) { //JSONObject(response.body().string()) 이게 내가 보낸 json 받는 부분임
                                        String code = new JSONObject(response.body().string()).get("code").toString();
                                        if (code.equals("1")) {
                                            Toast.makeText(LoginActivity.this, "성공", Toast.LENGTH_SHORT).show();


                                            SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());
                                            sharedPreferenceUtil.setLoginState(1);

                                            sharedPreferenceUtil.setPhoneNum(phone);
                                            sharedPreferenceUtil.setPassWord(password);

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
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

                    }else {
                        Toast.makeText(LoginActivity.this, "핸드폰번호를 확인해주세요!", Toast.LENGTH_SHORT).show();
                    }
                }




            }
        });

        /** 회원가입 페이지로 이동 **/
        signupBtn = (TextView) findViewById(R.id.signup_button);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());

        if(sharedPreferenceUtil.getLoginState() == 1){
            Log.e("shared state", ""+sharedPreferenceUtil.getLoginState());
            phone = sharedPreferenceUtil.getPhoneNum();
            password = sharedPreferenceUtil.getPassWord();
            Log.e("shared phone", ""+sharedPreferenceUtil.getPhoneNum());
            Log.e("shared password", ""+sharedPreferenceUtil.getPassWord());




            Call<ResponseBody> postRate = apiService.login(phone, password);
            postRate.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.body() != null) { //JSONObject(response.body().string()) 이게 내가 보낸 json 받는 부분임
                            String code = new JSONObject(response.body().string()).get("code").toString();
                            if (code.equals("1")) {
                                Toast.makeText(LoginActivity.this, "자동로그인 되었습니다.", Toast.LENGTH_SHORT).show();


                                SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());

                                sharedPreferenceUtil.setPhoneNum(phone);
                                sharedPreferenceUtil.setPassWord(password);

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
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


    }

    public void network(){


    }


/*

        */

    /**
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
    @Override
    public void onBackPressed() {


        if (getFragmentManager().getBackStackEntryCount() > 0) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_home, new HomeFragment());
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction.commit();
        } else {
            long tempTime = System.currentTimeMillis();
            long intervalTime = tempTime - backPressedTime;
            if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {


                /** 루트 액티비티가 안꺼져있는 경우. 프로세스까지 죽이는 방법! **/
                ActivityCompat.finishAffinity(this);
                System.runFinalizersOnExit(true);
                finish();
                System.exit(0);
            } else {
                backPressedTime = tempTime;
                Toast.makeText(getApplicationContext(), "뒤로가기를 한 번 더누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
