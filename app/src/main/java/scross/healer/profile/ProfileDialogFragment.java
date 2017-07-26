package scross.healer.profile;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import scross.healer.HealerContext;
import scross.healer.R;
import scross.healer.account.SignupActivity;
import scross.healer.timeline.TimelineEmotionDialog;
import scross.healer.timeline.TimelineFragment;

/**
 * Created by hanee on 2017-07-18.
 */

public class ProfileDialogFragment extends DialogFragment implements View.OnClickListener {
    final Calendar myCalendar = Calendar.getInstance();


    TextView updateBirth;

    @Override
    public void onClick(View view) {

    }

    public static ProfileDialogFragment newInstance() {

        ProfileDialogFragment profileDialog = new ProfileDialogFragment();
        return profileDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.CustomDialog);


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

        View view = inflater.inflate(R.layout.fragment_profile_update_dialog, container, false);



        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy.MM.dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                updateBirth.setText(sdf.format(myCalendar.getTime()));

            }
        };

        updateBirth = (TextView) view.findViewById(R.id.profile_update_birth);
        updateBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dp = new DatePickerDialog(HealerContext.getContext(),R.style.MyDialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dp.show();
            }
        });
        return  view;
    }
}
