package scross.healer.account;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scross.healer.BaseActivity;
import scross.healer.HealerContext;
import scross.healer.MainActivity;
import scross.healer.R;
import scross.healer.networkService.NetworkApi;
import scross.healer.networkService.NetworkService;

/**
 * Created by hanee on 2017-07-18.
 */

public class SignupActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    //   private BackPressCloseHandler backPressCloseHandler;
    public static String select_item = "";

    EditText birth;
    Button submit;
    EditText phoneInput;
    EditText userName;
    EditText pw1;
    EditText pw2;
    CheckBox checkSelect;
    TextView signupPhoneCheck1;
    TextView signupPhoneCheck2;

    String seletedGender;
    int check=0;

    final Calendar myCalendar = Calendar.getInstance();

    NetworkService apiService;


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        seletedGender = (String) adapterView.getSelectedItem();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        apiService = NetworkApi.getInstance(this).getServce();


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyyMMdd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                birth.setText(sdf.format(myCalendar.getTime()));

            }
        };

        Spinner spinner = (Spinner) findViewById(R.id.signup_user_sex);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(SignupActivity.this, R.array.sex_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(this);


        phoneInput = (EditText) findViewById(R.id.phone_number);
        userName = (EditText) findViewById(R.id.user_name);
        pw1 = (EditText) findViewById(R.id.password1);
        pw2 = (EditText) findViewById(R.id.password2);
        checkSelect = (CheckBox) findViewById(R.id.signup_check);
        signupPhoneCheck1 = (TextView) findViewById(R.id.signup_phone_check1);
        signupPhoneCheck2 = (TextView) findViewById(R.id.signup_phone_check2);

        birth = (EditText) findViewById(R.id.user_birth);

        birth.setInputType(InputType.TYPE_NULL); // 입력제한

        birth.setOnClickListener(new View.OnClickListener() { //edittext 클릭 시 datepickerdialog
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(SignupActivity.this, R.style.MyDialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dp.show();
            }
        });


        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.e("i1: ", i1 + "");
                if (i1 == 13) {

                    networkCall();

                  /*  signupPhoneCheck1.postDelayed(new Runnable() {
                        public void run() {
                            if(check == 2){
                                signupPhoneCheck2.setVisibility(View.VISIBLE);
                                signupPhoneCheck1.setVisibility(View.GONE);
                            }else if(check == 1){
                                signupPhoneCheck1.setVisibility(View.VISIBLE);
                                signupPhoneCheck2.setVisibility(View.GONE);
                            }

                    *//*        signupPhoneCheck1.setVisibility(View.VISIBLE);
                            signupPhoneCheck2.setVisibility(View.GONE);*//*

                        }
                    }, 2000);
*/
                }else{
                    signupPhoneCheck1.setVisibility(View.GONE);
                    signupPhoneCheck2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        phoneInput.addTextChangedListener(tw);


        phoneInput.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
        phoneInput.addTextChangedListener(new PhoneNumberFormattingTextWatcher()); // 폰번호에 하이픈 붙이기

        Log.e("phoneNum:", phoneInput.getText() + "");


        submit = (Button) findViewById(R.id.signup_submit_btn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (phoneInput.getText().length() < 11) {
                    Toast.makeText(SignupActivity.this, "핸드폰 번호를 제대로 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (pw1.getText().length() == 0 || pw2.getText().length() == 0) {
                    Toast.makeText(SignupActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } /*else if (pw1.getText().toString() != pw2.getText().toString()) {
                    Toast.makeText(SignupActivity.this, "비밀번호가 맞지 않습니다.", Toast.LENGTH_SHORT).show();


                }*/
                /*else if(seletedGender == null){
                    Toast.makeText(SignupActivity.this, "성별을 선택해주세요", Toast.LENGTH_SHORT).show();


                } */
                else if (userName.length() < 2) {
                    Toast.makeText(SignupActivity.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();

                } else {
                    if (checkSelect.isChecked()) {


                        int phone = Integer.valueOf(phoneInput.getText().toString().replaceAll("-", ""));
                        Log.e("phone", phone + "");

                        String name = userName.getText().toString();
                        String password = pw1.getText().toString();
                        int birthday = Integer.valueOf(birth.getText().toString());
                        String gender = seletedGender;

                        Call<ResponseBody> postRate = apiService.signup(phone, name, password, birthday, gender);
                        postRate.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    if (response.body() != null) { //JSONObject(response.body().string()) 이게 내가 보낸 json 받는 부분임
                                        String code = new JSONObject(response.body().string()).get("code").toString();
                                        if (code.equals("1")) {
                                            Toast.makeText(SignupActivity.this, "성공", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);


                                            startActivity(intent);
                                            SignupActivity.this.finish();
                                        } else {
                                            String msg = new JSONObject(response.body().string()).get("results").toString();
                                            Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        Toast.makeText(SignupActivity.this, "서버오류입니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(SignupActivity.this, "서버오류입니다.", Toast.LENGTH_SHORT).show();
                                Log.d("value", t.getMessage());

                            }
                        });

                    } else {
                        Toast.makeText(SignupActivity.this, "동의해주셔야 가입이 가능합니다..", Toast.LENGTH_SHORT).show();

                    }
                }


            }
        });


    }

    public void networkCall() {
        int phone = Integer.valueOf(phoneInput.getText().toString().replaceAll("-", ""));
        Log.e("phone: ", phone + "");

        Call<ResponseBody> postRate = apiService.checkPhone(phone);
        postRate.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null) { //JSONObject(response.body().string()) 이게 내가 보낸 json 받는 부분임
                        String code = new JSONObject(response.body().string()).get("code").toString();
                        if (code.equals("1")) {

                            signupPhoneCheck2.setVisibility(View.VISIBLE);
                            signupPhoneCheck1.setVisibility(View.GONE);

//                            Toast.makeText(SignupActivity.this, "사용가능.", Toast.LENGTH_SHORT).show();


                        } else {
                            signupPhoneCheck1.setVisibility(View.VISIBLE);
                            signupPhoneCheck2.setVisibility(View.GONE);


//                            Toast.makeText(SignupActivity.this, "이미 가입된 연락처.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(SignupActivity.this, "서버 오류.", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "서버오류입니다.", Toast.LENGTH_SHORT).show();
                Log.d("value", t.getMessage());

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
