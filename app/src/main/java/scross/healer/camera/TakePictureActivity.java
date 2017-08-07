package scross.healer.camera;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.cameraview.AspectRatio;
import com.google.android.cameraview.CameraView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scross.healer.HealerContext;
import scross.healer.MainActivity;
import scross.healer.R;
import scross.healer.SharedPreferenceUtil;
import scross.healer.emotion.EmotionActivity;
import scross.healer.media.MediaplayerActivity;
import scross.healer.networkService.NetworkApi;
import scross.healer.networkService.NetworkService;
import scross.healer.profile.ProfileDialogFragment;
import scross.healer.timeline.TimelineFragment;

import static android.support.v4.content.FileProvider.getUriForFile;
/*

*
 * Created by gta2v on 2017-07-26.

*/


public class TakePictureActivity  extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback,
        AspectRatioFragment.Listener {

    int state;
    String lastDay;
    SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());




    NetworkService apiService;
    private static final String TAG = "TakePictureActivity";

    private static final int REQUEST_CAMERA_PERMISSION = 1;

    private static final String FRAGMENT_DIALOG = "dialog";

    private static final int[] FLASH_OPTIONS = {
            CameraView.FLASH_AUTO,
            CameraView.FLASH_OFF,
            CameraView.FLASH_ON,
    };

    private static final int[] FLASH_ICONS = {
            R.drawable.ic_flash_auto,
            R.drawable.ic_flash_off,
            R.drawable.ic_flash_on,
    };

    private static final int[] FLASH_TITLES = {
            R.string.flash_auto,
            R.string.flash_off,
            R.string.flash_on,
    };

    private int mCurrentFlash;

    private CameraView mCameraView;

    private Handler mBackgroundHandler;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.take_picture:
                    if (mCameraView != null) {
                        mCameraView.takePicture();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takepicure);
        mCameraView = (CameraView) findViewById(R.id.camera);
        if (mCameraView != null) {
            mCameraView.addCallback(mCallback);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.take_picture);
        if (fab != null) {
            fab.setOnClickListener(mOnClickListener);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        apiService = NetworkApi.getInstance(this).getServce();
        state = sharedPreferenceUtil.getProcess();
        lastDay = String.valueOf(sharedPreferenceUtil.getLastDay());

        //TODO 스테이트 인텐트로 받아와서 1이나 4인 경우에만 이모션 넘어가고, 아니면 메인액티비티로 보내기!! (예외처리)
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            mCameraView.start();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ConfirmationDialogFragment
                    .newInstance(R.string.camera_permission_confirmation,
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_CAMERA_PERMISSION,
                            R.string.camera_permission_not_granted)
                    .show(getSupportFragmentManager(), FRAGMENT_DIALOG);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    protected void onPause() {
        mCameraView.stop();
        super.onPause();
        Log.e("fffff","꺼짐 Pause");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBackgroundHandler != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                mBackgroundHandler.getLooper().quitSafely();
            } else {
                mBackgroundHandler.getLooper().quit();
            }
            mBackgroundHandler = null;
        }

        Log.e("fffff","꺼짐 Destory");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (permissions.length != 1 || grantResults.length != 1) {
                    throw new RuntimeException("Error on requesting camera permission.");
                }
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, R.string.camera_permission_not_granted,
                            Toast.LENGTH_SHORT).show();
                }
                // No need to start camera here; it is handled by onResume
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aspect_ratio:
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (mCameraView != null
                        && fragmentManager.findFragmentByTag(FRAGMENT_DIALOG) == null) {
                    final Set<AspectRatio> ratios = mCameraView.getSupportedAspectRatios();
                    final AspectRatio currentRatio = mCameraView.getAspectRatio();
                    AspectRatioFragment.newInstance(ratios, currentRatio)
                            .show(fragmentManager, FRAGMENT_DIALOG);
                }
                return true;
            case R.id.switch_flash:
                if (mCameraView != null) {
                    mCurrentFlash = (mCurrentFlash + 1) % FLASH_OPTIONS.length;
                    item.setTitle(FLASH_TITLES[mCurrentFlash]);
                    item.setIcon(FLASH_ICONS[mCurrentFlash]);
                    mCameraView.setFlash(FLASH_OPTIONS[mCurrentFlash]);
                }
                return true;
            case R.id.switch_camera:
                if (mCameraView != null) {
                    int facing = mCameraView.getFacing();
                    mCameraView.setFacing(facing == CameraView.FACING_FRONT ?
                            CameraView.FACING_BACK : CameraView.FACING_FRONT);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAspectRatioSelected(@NonNull AspectRatio ratio) {
        if (mCameraView != null) {
            Toast.makeText(this, ratio.toString(), Toast.LENGTH_SHORT).show();
            mCameraView.setAspectRatio(ratio);
        }
    }

    private Handler getBackgroundHandler() {
        if (mBackgroundHandler == null) {
            HandlerThread thread = new HandlerThread("background");
            thread.start();
            mBackgroundHandler = new Handler(thread.getLooper());
        }
        return mBackgroundHandler;
    }

    private CameraView.Callback mCallback
            = new CameraView.Callback() {

        @Override
        public void onCameraOpened(CameraView cameraView) {
            Log.d(TAG, "onCameraOpened");
        }

        @Override
        public void onCameraClosed(CameraView cameraView) {
            Log.d(TAG, "onCameraClosed");
        }

        @Override
        public void onPictureTaken(CameraView cameraView, final byte[] data) {
            Log.d(TAG, "onPictureTaken " + data.length);
            Toast.makeText(cameraView.getContext(), R.string.picture_taken, Toast.LENGTH_SHORT)
                    .show();
            getBackgroundHandler().post(new Runnable() {
                @Override
                public void run() {
                    final File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                            "picture.jpg");
//                    FileProvider.getUriForFile(HealerContext.getContext(), getApplicationContext().getPackageName()+".fileprovider", file);
                    OutputStream os = null;
                    try {
                        os = new FileOutputStream(file);
                        os.write(data);
                        os.close();
                    } catch (IOException e) {
                        Log.w(TAG, "Cannot write to " + file, e);
                    } finally {
                        if (os != null) {
                            try {
                                os.close();
                            } catch (IOException e) {
                                // Ignore
                            }
                        }
                    }
                    RequestBody reqFile = RequestBody.create(MediaType.parse("image/jpg"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), reqFile);
/*                    TimelineFragment tf = new TimelineFragment();
                    tf.getArguments().g*/

                    if (state == 1) {

                        Call<ResponseBody> process1 = apiService.process1(lastDay, body);
                        process1.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    if (response.body() != null) { //JSONObject(response.body().string()) 이게 내가 보낸 json 받는 부분임
                                        String code = new JSONObject(response.body().string()).get("code").toString();
                                        if (code.equals("1")) {
                                            Toast.makeText(TakePictureActivity.this, "업로드 중입니다..", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(TakePictureActivity.this, "성공", Toast.LENGTH_SHORT).show();
                                            //파일처리부분

                                       /* Intent intent1 = getIntent();
                                        int state1 = intent1.getExtras().getInt("state");

                                        /** get단계 **/
                                            Intent intent1 = getIntent();
                                            state = intent1.getExtras().getInt("state");
                                            TakePictureActivity.this.finish();
                                            Log.e("state takepic get", state + "");


                                            state = state + 1;


                                            /** put단계 **/
                                            Intent intent = new Intent(getApplicationContext(), EmotionActivity.class);


                                            intent.putExtra("state", state);
                                            Log.e("state takepic put", state + "");


                                            SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());

                                            if (sharedPreferenceUtil.getProcess() != state) {
                                                sharedPreferenceUtil.setProcess(state);

//            state = sharedPreferenceUtil.getProcess();
                                            }
                                            Log.e("SharedPreference!!!!: ", sharedPreferenceUtil.getProcess() + " TakePictureActivity.");

                                            startActivity(intent);
                                            finish();

                                        } else {
                                            Toast.makeText(TakePictureActivity.this, "앱을 끄고 다시 시작해주세요.", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        Toast.makeText(TakePictureActivity.this, "서버오류입니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (IOException e) {
                                    Toast.makeText(TakePictureActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    Toast.makeText(TakePictureActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(TakePictureActivity.this, "서버 오류입니다. 잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        });

                        //이미지 처리 부분

                    } else if(state == 4){
                        Call<ResponseBody> process4 = apiService.process4(lastDay, body);
                        process4.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    if (response.body() != null) { //JSONObject(response.body().string()) 이게 내가 보낸 json 받는 부분임
                                        String code = new JSONObject(response.body().string()).get("code").toString();
                                        if (code.equals("1")) {
                                            Toast.makeText(TakePictureActivity.this, "성공", Toast.LENGTH_SHORT).show();
                                            //파일처리부분

                                       /* Intent intent1 = getIntent();
                                        int state1 = intent1.getExtras().getInt("state");

                                        /** get단계 **/
                                            Intent intent1 = getIntent();
                                            state = intent1.getExtras().getInt("state");
                                            TakePictureActivity.this.finish();
                                            Log.e("state takepic get", state + "");


                                            state = state + 1;


                                            /** put단계 **/
                                            Intent intent = new Intent(getApplicationContext(), EmotionActivity.class);


                                            intent.putExtra("state", state);
                                            Log.e("state takepic put", state + "");


                                            SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());

                                            if (sharedPreferenceUtil.getProcess() != state) {
                                                sharedPreferenceUtil.setProcess(state);

//            state = sharedPreferenceUtil.getProcess();
                                            }
                                            Log.e("SharedPreference!!!!: ", sharedPreferenceUtil.getProcess() + " TakePictureActivity.");

                                            startActivity(intent);


                                        } else {
                                            Toast.makeText(TakePictureActivity.this, "앱을 끄고 다시 시작해주세요.", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        Toast.makeText(TakePictureActivity.this, "서버오류입니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (IOException e) {
                                    Toast.makeText(TakePictureActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    Toast.makeText(TakePictureActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(TakePictureActivity.this, "서버 오류입니다. 잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            });
        }

    };

    public static class ConfirmationDialogFragment extends DialogFragment {

        private static final String ARG_MESSAGE = "message";
        private static final String ARG_PERMISSIONS = "permissions";
        private static final String ARG_REQUEST_CODE = "request_code";
        private static final String ARG_NOT_GRANTED_MESSAGE = "not_granted_message";

        public static ConfirmationDialogFragment newInstance(@StringRes int message,
                                                             String[] permissions, int requestCode, @StringRes int notGrantedMessage) {
            ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_MESSAGE, message);
            args.putStringArray(ARG_PERMISSIONS, permissions);
            args.putInt(ARG_REQUEST_CODE, requestCode);
            args.putInt(ARG_NOT_GRANTED_MESSAGE, notGrantedMessage);
            fragment.setArguments(args);
            return fragment;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Bundle args = getArguments();
            return new AlertDialog.Builder(getActivity())
                    .setMessage(args.getInt(ARG_MESSAGE))
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String[] permissions = args.getStringArray(ARG_PERMISSIONS);
                                    if (permissions == null) {
                                        throw new IllegalArgumentException();
                                    }
                                    ActivityCompat.requestPermissions(getActivity(),
                                            permissions, args.getInt(ARG_REQUEST_CODE));
                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getActivity(),
                                            args.getInt(ARG_NOT_GRANTED_MESSAGE),
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                    .create();
        }

    }

}