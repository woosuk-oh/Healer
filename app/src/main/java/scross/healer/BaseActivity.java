package scross.healer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

public class BaseActivity extends AppCompatActivity {


//    BackPressCloseHandler backPressCloseHandler;
    int currentState; //현재까지 완료한 단계.

    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null){
            // not connected to the internet
          //  Toast.makeText(this, "네트워크가 연결되지 않았습니다. 네트워크 연결 확인해주세요", Toast.LENGTH_SHORT).show();
            Log.d("onCreate", "onCreate에서 네트워크 OFF 확인되어 finish함.");



            AlertDialog.Builder builder = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this

// 여기서 부터는 알림창의 속성 설정
            builder.setTitle("알림")        // 제목 설정
                    .setMessage("네트워크가 OFF 되어 있습니다. 네트워크를 확인해주세요.")        // 메세지 설정
                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                    .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                        // 확인 버튼 클릭시 설정
                        public void onClick(DialogInterface dialog, int whichButton){
                            dialog.dismiss();
                            finish();
                        }
                    });


            AlertDialog dialog = builder.create();    // 알림창 객체 생성
            dialog.show();    // 알림창 띄우기

         //   finish();
        }
    }


    private ProgressDialog mProgressDialog;


    public void showDialog(){
        if(mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public void hideDialog(){
        if(mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        backPressCloseHandler.onBackPressed();

    }

/*


    public class BackPressCloseHandler {

        public long backKeyPressedTime = 0;
        public Toast toast;

        public Activity activity;

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
    }*/

}
