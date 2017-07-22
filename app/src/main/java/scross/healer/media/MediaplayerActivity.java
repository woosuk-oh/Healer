package scross.healer.media;

import android.app.Activity;
import android.content.Context;
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
import android.widget.TextView;

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

import scross.healer.R;

import static android.view.View.GONE;

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

    SimpleDateFormat mmss = new SimpleDateFormat("mm:ss");



    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_audioplayer);

        // Set up the play/pause/reset/stop buttons
        mPath = "https://s3.ap-northeast-2.amazonaws.com/healerc/med-1.mp3";
        mPlay = (Button) findViewById(R.id.play);
        mPause = (Button) findViewById(R.id.pause);
        tv = (TextView) findViewById(R.id.current_time);
        tv2 = (TextView) findViewById(R.id.time_remaining);



//        mReset = (ImageButton) findViewById(R.id.reset);
//        mStop = (ImageButton) findViewById(R.id.stop);

        mPause.setVisibility(GONE);

        mPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mPause.setVisibility(view.VISIBLE);
                mPlay.setVisibility(GONE);
                playVideo();
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

    private void playVideo() {
        try {
            final String path = mPath/*.getText().toString()*/;
            Log.v(TAG, "path: " + path);

            // If the path has not changed, just start the media player
            if (path.equals(current) && mp != null) {
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

            mp.setVolume(1,1);

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


                                            }
                                        });
                                    } else {
                             /*       timer.cancel();
                                    timer.purge();*/
                                    }
                                }
                            });
                        }
                    }, 0, 1000);
                    Timer timer2 = new Timer();
                    timer2.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mp != null && mp.isPlaying()) {
                                        tv2.post(new Runnable() {
                                            @Override
                                            public void run() {

                                                tv2.setText("남은시간: " + mmss.format(mp.getDuration() - mp.getCurrentPosition()));


                                            }
                                        });
                                    } else {
                             /*       timer.cancel();
                                    timer.purge();*/
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
            }
            catch (IOException ex) {
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
}
