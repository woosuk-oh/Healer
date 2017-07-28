package scross.healer.splash;

import android.Manifest;
import android.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import scross.healer.R;
import scross.healer.account.LoginActivity;
import scross.healer.camera.TakePictureActivity;

/**
 * Created by hanee on 2017-07-18.
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*

        startActivity(new Intent(this, MainActivity.class));
        finish();
*/





        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();       }
        }, 1000);

    }
    Handler handler = new Handler();


}

