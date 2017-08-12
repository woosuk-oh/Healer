package scross.healer.media;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scross.healer.HealerContext;
import scross.healer.MainActivity;
import scross.healer.R;
import scross.healer.SharedPreferenceUtil;
import scross.healer.camera.CameraActivity;
import scross.healer.camera.TakePictureActivity;
import scross.healer.emotion.EmotionActivity;
import scross.healer.networkService.NetworkApi;
import scross.healer.networkService.NetworkService;

import static android.view.View.GONE;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static scross.healer.HealerContext.getContext;

public class MediaplayerActivity extends Activity implements OnErrorListener,
        OnBufferingUpdateListener, OnCompletionListener,
        MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {
    private static final String TAG = "VideoPlayer";

    private MediaPlayer mp;
    private String mPath;
    private Button mPlay;
    private Button mPause;
    private Button btnt;
    private ImageButton mReset;
    private ImageButton mStop;
    private String current;
    private AudioManager audioManager;
    private TextView tv;
    private TextView tv2;
    private TextView contentName;
    private TextView contentBody;
    private TextView contentDay;
    private LinearLayout mediaBackground;
    private Button mediaSkipBtn;


    String mediaEndCheck;
    int savetime = 0;
    int tempTime = 0;
    int stateProcess;
    int lastDay = 0;

    NetworkService apiService;


    int networkChangereceive;

    public static String BROADCAST_ACTION =
            "android.net.conn.CONNECTIVITY_CHANGE";


    SimpleDateFormat mmss = new SimpleDateFormat("mm:ss");
//    MediaplayerNetworkCheckHelper mNetworkStateReceiver = new MediaplayerNetworkCheckHelper();


    SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());

    int networkCheck = sharedPreferenceUtil.getSaveNetworkType();

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

    /*    IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_ACTION);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(br, filter);*/


        setContentView(R.layout.activity_audioplayer);

        apiService = NetworkApi.getInstance(this).getServce();
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(this);


//        if (sharedPreferenceUtil.getSaveTime() != 0) {
        savetime = sharedPreferenceUtil.getSaveTime();
        stateProcess = sharedPreferenceUtil.getProcess();
//        sharedPreferenceUtil.setLastDay(1);
        lastDay = sharedPreferenceUtil.getLastDay();

        Log.e("쉐어드프리페이런스: ", savetime + "세이브타임.");
        Log.e("쉐어드프리페이런스: ", stateProcess + "Mediaplayer 프로세스 onCreate..");
        Log.e("쉐어드프리페이런스: ", lastDay + " Meiaplayer 라스트데이 onCreate.");

//        }

        // Set up the play/pause/reset/stop buttons
        mPlay = (Button) findViewById(R.id.play);
        mPause = (Button) findViewById(R.id.pause);
        tv = (TextView) findViewById(R.id.current_time);
        tv2 = (TextView) findViewById(R.id.time_remaining);
        contentDay = (TextView) findViewById(R.id.media_content_day);
        contentName = (TextView) findViewById(R.id.media_content_name);
        contentBody = (TextView) findViewById(R.id.media_content_body);
        mediaBackground = (LinearLayout) findViewById(R.id.media_background);

        mediaSkipBtn = (Button) findViewById(R.id.media_skip_btn);

        //skip 버튼!!
        mediaSkipBtn.setVisibility(View.VISIBLE);

        mediaSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());

                stateProcess = sharedPreferenceUtil.getProcess();
                stateProcess = stateProcess +1;
                sharedPreferenceUtil.setProcess(stateProcess);
                Log.e("쉐어드프리페어런스","Mediaplayer Skip getProcess: "+sharedPreferenceUtil.getProcess());

                savetime = 0;
                sharedPreferenceUtil.setSaveTime(savetime);
                Log.e("쉐어드프리페어런스","Mediaplayer Skip getSaveTime: "+sharedPreferenceUtil.getSaveTime());
                if (mp != null) {
                    mp.pause();
                    mp.stop();
                    mp.reset();

                    //리셋은 다시 재생 가능하도록 대기상태 완전 끝낼때는 release() 해줘야됨. 현재는 문제 없는듯
                }

                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                intent.putExtra("state", stateProcess);
                intent.putExtra("day", lastDay);
                startActivity(intent);
                finish();

            }
        });

        switch (lastDay) {
            case 1:
                contentDay.setText("1일차");
                contentName.setText("도입");
                contentBody.setText("자기 관찰하기");
                mediaBackground.setBackgroundResource(R.drawable.day1);
                mPath = "https://s3.ap-northeast-2.amazonaws.com/healerc/med-1.mp3";
                break;
            case 2:
                contentDay.setText("2일차");
                contentName.setText("초기 트라우마");
                contentBody.setText("부정적 기억 내보내기");
                mediaBackground.setBackgroundResource(R.drawable.day2);

                mPath = "https://s3.ap-northeast-2.amazonaws.com/healerc/med-2.mp3";
                break;
            case 3:
                contentDay.setText("3일차");
                contentName.setText("초기 트라우마");
                contentBody.setText("긍정적 정서 떠올리기");
                mediaBackground.setBackgroundResource(R.drawable.day3);

                mPath = "https://s3.ap-northeast-2.amazonaws.com/healerc/med-3.mp3";

                break;
            case 4:
                contentDay.setText("4일차");
                contentName.setText("초기 트라우마");
                contentBody.setText("감정 조절 배우기");
                mediaBackground.setBackgroundResource(R.drawable.day4);

                mPath = "https://s3.ap-northeast-2.amazonaws.com/healerc/med-4.mp3";

                break;
            case 5:
                contentDay.setText("5일차");
                contentName.setText("빅 트라우마");
                contentBody.setText("트라우마 정화 I");
                mediaBackground.setBackgroundResource(R.drawable.day5);

                mPath = "https://s3.ap-northeast-2.amazonaws.com/healerc/med-5.mp3";

                break;
            case 6:
                contentDay.setText("6일차");
                contentName.setText("빅 트라우마");
                contentBody.setText("트라우마 정화 II");
                mediaBackground.setBackgroundResource(R.drawable.day6);

                mPath = "https://s3.ap-northeast-2.amazonaws.com/healerc/med-6.mp3";

                break;
            case 7:
                contentDay.setText("7일차");
                contentName.setText("빅 트라우마");
                contentBody.setText("자아 대면하기");
                mediaBackground.setBackgroundResource(R.drawable.day7);

                mPath = "https://s3.ap-northeast-2.amazonaws.com/healerc/med-7.mp3";

                break;
            case 8:
                contentDay.setText("8일차");
                contentName.setText("마무리");
                contentBody.setText("자아 회복하기");
                mediaBackground.setBackgroundResource(R.drawable.day8);

                mPath = "https://s3.ap-northeast-2.amazonaws.com/healerc/med-8.mp3";

                break;

        }



        ConnectivityManager manager =
                (ConnectivityManager) MediaplayerActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ni = manager.getActiveNetworkInfo();
        String networkTypeName = ni.getTypeName();


        if (networkCheck == 1) { // 환경설정에서 wifi환경에서만 실행 체크한 경우.
            if (networkTypeName.equals("MOBILE")) {

                Toast.makeText(this, "WIFI에 연결된 상태에서만 재생 가능하도록 설정되어 있습니다.", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        }

//        mReset = (ImageButton) findViewById(R.id.reset);
//        mStop = (ImageButton) findViewById(R.id.stop);

        mPause.setVisibility(GONE);


        mPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                ConnectivityManager manager =
                        (ConnectivityManager) MediaplayerActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo ni = manager.getActiveNetworkInfo();
                String networkTypeName = ni.getTypeName();


                if (networkCheck == 1) { // 환경설정에서 wifi환경에서만 실행 체크한 경우.
                    if (networkTypeName.equals("MOBILE")) {
                        Log.d("network type", "Network - > (모바일)" + networkTypeName);
                        Toast.makeText(HealerContext.getContext(), "WIFI 연결상태가 아닙니다. 연결 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("network type", "Network - > (와이파이)" + networkTypeName);
                        mPause.setVisibility(view.VISIBLE);
                        mPlay.setVisibility(GONE);

                        //재생
                        if (savetime == 0) {

                            playVideo(0);
                        } else {
                            playVideo(savetime);
                        }

                    }
                } else { //네트워크 연결 관련 설정안한 경우 체크하지 않고 바로 재생

                    mPause.setVisibility(view.VISIBLE);
                    mPlay.setVisibility(GONE);

                    //재생
                    if (savetime == 0) {
                        playVideo(0);
                    } else {
                        playVideo(savetime);
                    }

                }


            }
        });
        mPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mp != null) {
                    mp.pause();
                    mPlay.setVisibility(view.VISIBLE);
                    mPause.setVisibility(GONE);
                }
            }
        });


    }
/*

    private View.OnClickListener pausePlay = new View.OnClickListener() {

        @Override
        public void onClick(View v) {


            if (!playPause) {
                btnt.setBackgroundResource(R.drawable.pausebtn);
                if (!intialStage) {

                    if (!mp.isPlaying())
                        playVideo();
                }
                playPause = true;
            } else {
                btnt.setBackgroundResource(R.drawable.playbtn);
                if (mp.isPlaying())
                    mp.pause();
                playPause = false;
            }
        }
    };*/

    private void playVideo(int loadtime) {
        try {
            final String path = mPath/*.getText().toString()*/;
            Log.v(TAG, "path: " + path);

            // If the path has not changed, just start the media player
            if (path.equals(current) && mp != null) {
                Log.e("saveTime" + savetime, "테스트: ");

                mp.start();


                return;
            }
            current = path;

            // Create a new media player and set the listeners
            mp = new MediaPlayer();
            mp.setOnErrorListener(this);
            mp.setOnBufferingUpdateListener(this);

            mp.setOnCompletionListener(this);

            mp.setOnPreparedListener(this);

            mp.setAudioStreamType(audioManager.STREAM_MUSIC);

            mp.setVolume(1, 1);


            // Set the surface for the video output

            // Set the data source in another thread
            // which actually downloads the mp3 or videos
            // to a temporary location
            Runnable r = new Runnable() {
                public void run() {
                    try {


                        mp.setDataSource(getApplicationContext(), Uri.parse(path));
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                    try {
                        mp.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.v(TAG, "Duration:  ===>" + mp.getDuration());


                    if (savetime != 0) {
                        mp.seekTo(savetime);
                    }
//                    mp.seekTo(990000);
                    mp.start();

                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mp != null && mp.isPlaying()) {
                                        tv.post(new Runnable() {
                                            @Override
                                            public void run() {

                                                tv.setText("재생시간: " + mmss.format(mp.getCurrentPosition()));
                                                if (mp.getCurrentPosition() != 0) {
                                                    savetime = mp.getCurrentPosition();
                                                }


                                            }
                                        });
                                        tv2.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                tv2.setText("남은시간: " + mmss.format(mp.getDuration() - mp.getCurrentPosition()));


                                                SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());


//                                                Log.e("남은시간: ", mp.getDuration() - mp.getCurrentPosition()+" ms");
                                                if (mp.getDuration() - mp.getCurrentPosition() < 1000) {
//                                                    Toast.makeText(MediaplayerActivity.this, "재생 끝", Toast.LENGTH_SHORT).show();

                                                    Log.e("SharedPreference!!!!: ", sharedPreferenceUtil.getProcess() + " MediaPlayer onCreate.");

                                                    if (stateProcess == 3) {

                                                        String dayString = String.valueOf(lastDay);
                                                        apiService = NetworkApi.getInstance(HealerContext.getContext()).getServce();

                                                        Call<ResponseBody> process3 = apiService.process3(dayString, 200);
                                                        process3.enqueue(new Callback<ResponseBody>() {


                                                            @Override
                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                try {
                                                                    if (response.body() != null) { //JSONObject(response.body().string()) 이게 내가 보낸 json 받는 부분임
                                                                        String code = new JSONObject(response.body().string()).get("code").toString();
                                                                        if (code.equals("1")) {

                                                                            Toast.makeText(HealerContext.getContext(), "금일 컨텐츠 듣기가 완료되었습니다!", Toast.LENGTH_SHORT).show();


                                                                            SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());


                                                                            stateProcess = stateProcess + 1;
                                                                            if (sharedPreferenceUtil.getProcess() != stateProcess) {
                                                                                sharedPreferenceUtil.setProcess(stateProcess);

                                                                            }

                                                                            sharedPreferenceUtil.setSaveTime(0);

                                                                            Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                                                                            intent.putExtra("state", stateProcess);
                                                                            intent.putExtra("day", lastDay);
                                                                            startActivity(intent);
                                                                            finish();


                                                                            //camera

                                                                        } else {
                                                                            Toast.makeText(HealerContext.getContext(), "컨텐츠 재생시간 저장에 실패했습니다", Toast.LENGTH_SHORT).show();
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
/*
                                                if(mp.getCurrentPosition() == mp.getDuration()){
                                                    Toast.makeText(MediaplayerActivity.this, "재생 끝", Toast.LENGTH_SHORT).show();
                                                }*/
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }, 0, 1000);

                }
            };
            new Thread(r).start();


        } catch (Exception e) {
            Log.e(TAG, "error: " + e.getMessage(), e);
            if (mp != null) {
                mp.stop();
                mp.release();
            }
        }


    }

    /**
     * If the user has specified a local url, then we download the
     * url stream to a temporary location and then call the setDataSource
     * for that local file
     *
     * @param path
     * @throws IOException
     */
    private void setDataSource(String path) throws IOException {
        if (!URLUtil.isNetworkUrl(path)) {
            mp.setDataSource(path);
        } else {
            URL url = new URL(path);
            URLConnection cn = url.openConnection();
            cn.connect();
            InputStream stream = cn.getInputStream();
            if (stream == null)
                throw new RuntimeException("stream is null");
            File temp = File.createTempFile("mediaplayertmp", "dat");
            String tempPath = temp.getAbsolutePath();
            FileOutputStream out = new FileOutputStream(temp);
            byte buf[] = new byte[128];
            do {
                int numread = stream.read(buf);
                if (numread <= 0)
                    break;
                out.write(buf, 0, numread);
            } while (true);
            mp.setDataSource(tempPath);
            try {
                stream.close();
            } catch (IOException ex) {
                Log.e(TAG, "error: " + ex.getMessage(), ex);
            }
        }
    }

    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        Log.e(TAG, "onError--->   what:" + what + "    extra:" + extra);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        return false;
    }

    public void onBufferingUpdate(MediaPlayer arg0, int percent) {
        Log.d(TAG, "onBufferingUpdate called --->   percent:" + percent);
    }

    public void onCompletion(MediaPlayer arg0) {
        Log.d(TAG, "onCompletion called");
    }

    public void onPrepared(MediaPlayer mediaplayer) {
        Log.d(TAG, "onPrepared called");
    }

    public void surfaceCreated(SurfaceHolder surfaceholder) {
        Log.d(TAG, "surfaceCreated called");
    }

    public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
        Log.d(TAG, "surfaceChanged called");
    }

    public void surfaceDestroyed(SurfaceHolder surfaceholder) {
        Log.d(TAG, "surfaceDestroyed called");
    }





    @Override
    public void onBackPressed() {
        if (savetime != 0) {
//            savetime = mp.getCurrentPosition();

            SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(this);
            sharedPreferenceUtil.setSaveTime(savetime);
            Log.e("savetime!!!!!", "" + savetime);
        }

//        onPause();

        if (mp != null) {
            mp.pause();
            mp.stop();
            mp.reset();

            //리셋은 다시 재생 가능하도록 대기상태 완전 끝낼때는 release() 해줘야됨. 현재는 문제 없는듯
        }
        super.onBackPressed();
        finish();

    }


    //TODO 브로드캐스트로 할 경우 밑에 완성시키기. 이상태로하면 퍼미션 디나이드 뜸

   /* public void sendBroadcast() {
        Intent broadcast = new Intent();
        broadcast.setAction(BROADCAST_ACTION);
        broadcast.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(broadcast);
    }


    @Override
    protected void onResume() {
        super.onResume();

      *//*  IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        try {
            registerReceiver(mNetworkStateReceiver, filter);
        } catch (Exception e) {
           e.printStackTrace();
        }*//*


        ConnectivityManager manager =
                (ConnectivityManager) MediaplayerActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ni = manager.getActiveNetworkInfo();
        String networkTypeName = ni.getTypeName();


        if (networkCheck == 1 && networkTypeName.equals("MOBILE")) {// 환경설정에서 와이파이만 연결로 체크한경우.
            // + 네트워크 상태가 모바일인 경우
            Log.d("network type", "Network - > (모바일)" + networkTypeName);
            Toast.makeText(HealerContext.getContext(), "WIFI 연결상태가 아닙니다. 연결 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

        sendBroadcast();


    }


    BroadcastReceiver br = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.w("Check", "Inside On Receiver");
            String action = intent.getAction();

            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager connectivityManager =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
                NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

              *//*  Toast.makeText(context, "Active Network Type : " + activeNetInfo.getTypeName(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Mobile Network Type : " + mobNetInfo.getTypeName(), Toast.LENGTH_SHORT).show();
*//*
                NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
                String networkTypeName = ni.getTypeName();

                if (networkTypeName.equals("MOBILE") && networkCheck == 1) {
                    Log.d("network type", "Network - > (모바일)" + networkTypeName);
                    Toast.makeText(HealerContext.getContext(), "WIFI 연결상태가 아닙니다. 연결 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();

                    mPlay.setEnabled(false);
                    mp.pause();



                } else {
                    Log.d("network type", "Network - > (와이파이)" + networkTypeName);
                    mPlay.setEnabled(true); //와이파이로 변경된 경우 버튼 작동 가능.

                }
            }

            Toast.makeText(getApplicationContext(), "메시지 받음",
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
      *//*  try {
            if (mNetworkStateReceiver != null) unregisterReceiver(mNetworkStateReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }*//*
        unregisterReceiver(br);

    }*/


}


