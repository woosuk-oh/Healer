package scross.healer.account;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import scross.healer.BaseActivity;
import scross.healer.MainActivity;
import scross.healer.R;

/**
 * Created by hanee on 2017-07-18.
 */

public class SignupActivity extends BaseActivity {
    //   private BackPressCloseHandler backPressCloseHandler;

    EditText birth;
    Button submit;
    final Calendar myCalendar = Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy.MM.dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                birth.setText(sdf.format(myCalendar.getTime()));

            }
        };


        birth = (EditText) findViewById(R.id.user_birth);


        birth.setOnClickListener(new View.OnClickListener() { //edittext 클릭 시 datepickerdialog
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(SignupActivity.this,R.style.MyDialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setMinDate(myCalendar.getTimeInMillis()); //날짜제한
                dp.show();
            }
        });



        submit = (Button) findViewById(R.id.signup_submit_btn);

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
