package scross.healer.profile;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scross.healer.HealerContext;
import scross.healer.MainActivity;
import scross.healer.R;
import scross.healer.account.SignupActivity;
import scross.healer.databinding.FragmentTimelineBinding;
import scross.healer.networkService.NetworkApi;
import scross.healer.networkService.NetworkService;
import scross.healer.networkService.object.ProfileUpdate;
import scross.healer.networkService.object.TimelineObject;
import scross.healer.timeline.TimelineFragment;

/**
 * Created by hanee on 2017-07-18.
 */

public class ProfileDialogFragment extends DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    final Calendar myCalendar = Calendar.getInstance();
    public static String selectGender = "";
    NetworkService apiService;
    String imageUrl;
    String getBirth;


    ImageView profileUpdateUserPhoto;
    Button profileSuccessBtn;
    Button profileCancelBtn;
    TextView updateBirth;

    FragmentTimelineBinding binding;
    ProfileUpdate profileUpdate;


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String str = (String) adapterView.getSelectedItem();
                selectGender = str;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public class ClickTest{
        public void onClick(View view){

            switch (view.getId()){
                case R.id.profile_cancel_btn:
                    Toast.makeText(getActivity().getApplicationContext(),"눌 림", Toast.LENGTH_SHORT).show();

                    dismiss();
                    break;
                case R.id.profile_success_btn:
                    Toast.makeText(getActivity().getApplicationContext(),"눌 림", Toast.LENGTH_SHORT).show();

                    network2();
                    break;

            }
        }
    }


    @Override
    public void onClick(View view) {
/*        switch (view.getId()) {
            case R.id.profile_cancel_btn:
                Toast.makeText(getActivity().getApplicationContext(),"눌 림", Toast.LENGTH_SHORT).show();

                dismiss();
                break;
            case R.id.profile_success_btn:
                Toast.makeText(getActivity().getApplicationContext(),"눌 림", Toast.LENGTH_SHORT).show();

                network2();
                break;


        }*/
    }

    public static ProfileDialogFragment newInstance() {

        ProfileDialogFragment profileDialog = new ProfileDialogFragment();
        return profileDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.CustomDialog);

        network();


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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timeline, container,false); // 데이터 바인딩 2
        View view = binding.getRoot(); // 데이터 바인딩 3

/*
        profileUpdateUserPhoto = (ImageView) view.findViewById(R.id.profile_update_user_photo);
        profileSuccessBtn = (Button) view.findViewById(R.id.profile_success_btn);
        profileCancelBtn =  (Button) view.findViewById(R.id.profile_cancel_btn);

        profileCancelBtn.setOnClickListener(this);
        profileSuccessBtn.setOnClickListener(this);
*/


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy년 MM월 dd일";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                updateBirth.setText(sdf.format(myCalendar.getTime()));

            }
        };

        updateBirth = (TextView) view.findViewById(R.id.profile_update_birth);
        updateBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dp = new DatePickerDialog(getActivity(),R.style.MyDialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dp.show();
            }
        });


        Spinner spinner = (Spinner) view.findViewById(R.id.profile_update_sex);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.sex_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(this);



        return  view;
    }

    public void network(){

        apiService = NetworkApi.getInstance(getActivity()).getServce();
        Call<ResponseBody> getTimeline = apiService.timeline();
        getTimeline.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.body()!= null){
                    try{
                        JSONObject data = new JSONObject(response.body().string());
                        String code = data.get("code").toString();
                        if(code.equals("1")){


                            JSONObject results = data.getJSONObject("results");

                            imageUrl = results.getString("profile");

                            String myFormat = "yyyy년 MM월 dd일";
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                            getBirth = sdf.format(results.getString("birth"));

                            profileUpdate = new ProfileUpdate(results.getString("name"), getBirth, results.getString("gender"));



                 /*           if(!results.isNull("profile")){//프로필 이미지 널이 아닌 경우
                                Glide.with(HealerContext.getContext()).load(imageUrl).into(profileUpdateUserPhoto);
                            }

*/

                        }else{
                            Toast.makeText(getActivity().getApplicationContext(), data.get("results").toString(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "서버오류입니다", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void network2(){

        String str= getBirth.toString().replaceAll("년","");
        str = str.replaceAll("월","");
        str = str.replaceAll("일", "");
        str = str.replaceAll(" ", "");
        int birth = Integer.valueOf(str);


        Call<ResponseBody> postRate = apiService.editProfile(birth, selectGender);
        postRate.enqueue(new Callback<ResponseBody>() {



            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null) { //JSONObject(response.body().string()) 이게 내가 보낸 json 받는 부분임
                        String code = new JSONObject(response.body().string()).get("code").toString();
                        if (code.equals("1")) {
                            Toast.makeText(HealerContext.getContext(), "프로필 수정이 완료되었습니다!", Toast.LENGTH_SHORT).show();


                            dismiss();
                        } else {
                            Toast.makeText(HealerContext.getContext(), "프로필 수정에 실패했습니다", Toast.LENGTH_SHORT).show();
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
