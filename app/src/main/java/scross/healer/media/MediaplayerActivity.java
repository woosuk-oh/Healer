package scross.healer.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
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
    String mediaEndCheck;
    int savetime = 0;
    int tempTime =0;
    int stateProcess;
    int lastDay = 0;

    NetworkService apiService;

    SimpleDateFormat mmss = new SimpleDateFormat("mm:ss");


    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_audioplayer);

        apiService = NetworkApi.getInstance(this).getServce();
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(this);


//        if (sharedPreferenceUtil.getSaveTime() != 0) {
            savetime = sharedPreferenceUtil.getSaveTime();
            stateProcess = sharedPreferenceUtil.getProcess();
//        sharedPreferenceUtil.setLastDay(1);
            lastDay = sharedPreferenceUtil.getLastDay();

            Log.e("쉐어드프리페이런스: ",savetime+ "세이브타임.");
            Log.e("쉐어드프리페이런스: ", stateProcess+ "Mediaplayer 프로세스 onCreate..");
            Log.e("쉐어드프리페이런스: ",lastDay+ " Meiaplayer 라스트데이 onCreate.");

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


//        mReset = (ImageButton) findViewById(R.id.reset);
//        mStop = (ImageButton) findViewById(R.id.stop);

        mPause.setVisibility(GONE);


        mPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mPause.setVisibility(view.VISIBLE);
                mPlay.setVisibility(GONE);
                if (savetime == 0) {
                    playVideo(0);
                } else {
                    playVideo(savetime);
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
            // TODO Auto-generated method stub
            // TODO Auto-generated method stub

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

                    /*mp.prepareAsync();
                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mp.seekTo(savetime);
                            mp.start();
                        }
                    });*/

                    if(savetime != 0) {
                        mp.seekTo(savetime);
                    }
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
                                                if(mp.getCurrentPosition() != 0){
                                                    savetime = mp.getCurrentPosition();
                                                }


                                            }
                                        });
                                        tv2.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                tv2.setText("남은시간: " + mmss.format(mp.getDuration() - mp.getCurrentPosition()));



                                                SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());


//                                                if(mp.getCurrentPosition() >= savetime){ //TODO 테스트용. 수정필요
                                                    if(mp.getCurrentPosition() == mp.getDuration()){
//                                                    Toast.makeText(MediaplayerActivity.this, "재생 끝", Toast.LENGTH_SHORT).show();

                                                    Log.e("SharedPreference!!!!: ", sharedPreferenceUtil.getProcess() + " MediaPlayer onCreate.");

                                                    if(stateProcess == 3) {

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

//            state = sharedPreferenceUtil.getProcess();
                                                                            }

                                                                            Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                                                                            intent.putExtra("state", stateProcess);
                                                                            intent.putExtra("day", lastDay);
                                                                            startActivity(intent);


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
//                                                    else {
//                                                        Toast.makeText(MediaplayerActivity.this, "잘못된 경로입니다. 타임라인을 확인해주세요!", Toast.LENGTH_SHORT).show();
//
//                                                        Intent intent = new Intent(getApplication(), MainActivity.class);
//                                                        startActivity(intent);
//
//                                                    }


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
            Log.e("savetime!!!!!",""+savetime);
        }

//        onPause();

        if (mp != null) {
            mp.pause();
            mp.stop();
            mp.reset();

            //TODO release() 해줘야됨
        }
        super.onBackPressed();
        finish();

    }
}



/*



    SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());


                                        if(mp.getCurrentPosition() == mp.getDuration()){
                                                Toast.makeText(MediaplayerActivity.this, "재생 끝", Toast.LENGTH_SHORT).show();

                                                Log.e("SharedPreference!!!!: ", sharedPreferenceUtil.getProcess() + " MediaPlayer onCreate.");

                                                if(stateProcess == 4){

                                                stateProcess = stateProcess +1;
                                                if (sharedPreferenceUtil.getProcess() != stateProcess) {
                                                sharedPreferenceUtil.setProcess(stateProcess);

//            state = sharedPreferenceUtil.getProcess();
                                                }

                                                Intent intent = new Intent(getApplicationContext(), EmotionActivity.class);
        intent.putExtra("state",stateProcess);
        startActivity(intent);

        //camera
        }else{
        Toast.makeText(MediaplayerActivity.this, "잘못된 경로입니다. 타임라인을 확인해주세요!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplication(), MainActivity.class);
        startActivity(intent);

        }


        }*/
