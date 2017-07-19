package scross.healer.media;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import scross.healer.R;

public class MediaplayerActivity extends Activity implements OnErrorListener,
        OnBufferingUpdateListener, OnCompletionListener,
        MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {
    private static final String TAG = "VideoPlayer";

    private MediaPlayer mp;
    private String mPath;
    private Button mPlay;
    private Button mPause;
    private ImageButton mReset;
    private ImageButton mStop;
    private String current;

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_audioplayer);

        // Set up the play/pause/reset/stop buttons
        mPath = "http://www.sample-videos.com/audio/mp3/india-national-anthem.mp3";
        mPlay = (Button) findViewById(R.id.play);
        mPause = (Button) findViewById(R.id.pause);
//        mReset = (ImageButton) findViewById(R.id.reset);
//        mStop = (ImageButton) findViewById(R.id.stop);

        mPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                playVideo();
            }
        });
        mPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mp != null) {
                    mp.pause();
                }
            }
        });
/*        mReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mp != null) {
                    mp.seekTo(0);
                }
            }
        });
        mStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mp != null) {
                    mp.stop();
                    mp.release();
                }
            }
        });*/

        // Set the transparency
      //  getWindow().setFormat(PixelFormat.TRANSPARENT);


    }

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

/*
            AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
            audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);*/

            mp.setVolume(1,1);
//            mp.setVolumeControlStream (AudioManager.STREAM_MUSIC);
/*

            audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

//음량 늘이는 소스
            audio.setStreamVolume(AudioManager.STREAM_MUSIC,
                    (int)(audio.getStreamVolume(AudioManager.STREAM_MUSIC) + 1), // 1씩 늘림
                    0);
*//*
            AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);*/
            mp.setOnBufferingUpdateListener(this);
            mp.setVolume(1,1);

            mp.setOnCompletionListener(this);
            mp.setVolume(1,1);

            mp.setOnPreparedListener(this);
            mp.setVolume(1,1);

            mp.setAudioStreamType(2);
            mp.setVolume(1,1);

            // Set the surface for the video output

            // Set the data source in another thread
            // which actually downloads the mp3 or videos
            // to a temporary location
            Runnable r = new Runnable() {
                public void run() {
                    try {
                        setDataSource(path);
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


/*
package scross.healer.media;
import android.app.Activity;
import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import scross.healer.R;


*/
/**
 * Created by hanee on 2017-07-20.
 *//*



public class MediaplayerActivity extends Activity {
    private Button btn;
    */
/**
     * help to toggle between play and pause.
     *//*

    private boolean playPause;
    private MediaPlayer mediaPlayer;
    */
/**
     * remain false till media is not completed, inside OnCompletionListener make it true.
     *//*

    private boolean intialStage = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audioplayer);
        btn = (Button) findViewById(R.id.player_button);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        btn.setOnClickListener(pausePlay);

    }

  */
/*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }*//*


    private View.OnClickListener pausePlay = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            // TODO Auto-generated method stub

            if (!playPause) {
                btn.setBackgroundResource(R.drawable.pausebtn);
                if (intialStage)
                    new Player()
                            .execute("www.sample-videos.com/audio/mp3/india-national-anthem.mp3");
                else {
                    if (!mediaPlayer.isPlaying())
                        mediaPlayer.start();
                }
                playPause = true;
            } else {
                btn.setBackgroundResource(R.drawable.playbtn);
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                playPause = false;
            }
        }
    };

    */
/**
     * preparing mediaplayer will take sometime to buffer the content so prepare it inside the background thread and starting it on UI thread.
     *
     * @author piyush
     *//*


    class Player extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog progress;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            Boolean prepared;
            try {
                FileInputStream fis = new FileInputStream(params[0]);
                FileDescriptor fd = fis.getFD();
                mediaPlayer.setDataSource("http://www.sample-videos.com/audio/mp3/india-national-anthem.mp3");

                mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // TODO Auto-generated method stub
                        intialStage = true;
                        playPause = false;
                        btn.setBackgroundResource(R.drawable.playbtn);
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.prepare();
                prepared = true;
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                Log.d("IllegarArgument", e.getMessage());
                prepared = false;
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (progress.isShowing()) {
                progress.cancel();
            }
            Log.d("Prepared", "//" + result);
            mediaPlayer.start();

            intialStage = false;
        }

        public Player() {
            progress = new ProgressDialog(MediaplayerActivity.this);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            this.progress.setMessage("Buffering...");
            this.progress.show();

        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

    */
/*


public class MediaplayerActivity extends Activity implements
        OnBufferingUpdateListener, OnCompletionListener,
        OnPreparedListener, OnVideoSizeChangedListener, SurfaceHolder.Callback {




        private static final String TAG = "MediaPlayerDemo";
        private int mVideoWidth;
        private int mVideoHeight;
        private MediaPlayer mMediaPlayer;
        private SurfaceView mPreview;
        private SurfaceHolder holder;
        private String path;
        private Bundle extras;
        private static final String MEDIA = "media";
        private static final int STREAM_VIDEO = 5;
        private boolean mIsVideoSizeKnown = false;
        private boolean mIsVideoReadyToBePlayed = false;

        *//*
*/
/**
 * Called when the activity is first created.
 *//*
*/
/*
        @Override
        public void onCreate(Bundle icicle) {
            super.onCreate(icicle);
            setContentView(R.layout.main);
            mPreview = (SurfaceView) findViewById(R.id.VideoView);
            holder = mPreview.getHolder();
            holder.addCallback(this);
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            extras = getIntent().getExtras();

        }

        private void playVideo(Integer Media) {
            doCleanUp();
            try {

                path = "http://www.pocketjourney.com/downloads/pj/video/famous.3gp";


                // Create a new media player and set the listeners
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setDataSource(path);
                mMediaPlayer.setDisplay(holder);
                mMediaPlayer.prepare();
                mMediaPlayer.setOnBufferingUpdateListener(this);
                mMediaPlayer.setOnCompletionListener(this);
                mMediaPlayer.setOnPreparedListener(this);
                mMediaPlayer.setOnVideoSizeChangedListener(this);
                //mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


            } catch (Exception e) {
                Log.e(TAG, "error: " + e.getMessage(), e);
            }
        }

        public void onBufferingUpdate(MediaPlayer arg0, int percent) {
            Log.d(TAG, "onBufferingUpdate percent:" + percent);

        }

        public void onCompletion(MediaPlayer arg0) {
            Log.d(TAG, "onCompletion called");
        }

        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            Log.v(TAG, "onVideoSizeChanged called");
            if (width == 0 || height == 0) {
                Log.e(TAG, "invalid video width(" + width + ") or height(" + height + ")");
                return;
            }
            mIsVideoSizeKnown = true;
            mVideoWidth = width;
            mVideoHeight = height;
            if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
                startVideoPlayback();
            }
        }

        public void onPrepared(MediaPlayer mediaplayer) {
            Log.d(TAG, "onPrepared called");
            mIsVideoReadyToBePlayed = true;
            if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
                startVideoPlayback();
            }
        }

        public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
            Log.d(TAG, "surfaceChanged called");

        }

        public void surfaceDestroyed(SurfaceHolder surfaceholder) {
            Log.d(TAG, "surfaceDestroyed called");
        }


        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "surfaceCreated called");
            playVideo(STREAM_VIDEO);


        }

        @Override
        protected void onPause() {
            super.onPause();
            releaseMediaPlayer();
            doCleanUp();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            releaseMediaPlayer();
            doCleanUp();
        }

        private void releaseMediaPlayer() {
            if (mMediaPlayer != null) {
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        }

        private void doCleanUp() {
            mVideoWidth = 0;
            mVideoHeight = 0;
            mIsVideoReadyToBePlayed = false;
            mIsVideoSizeKnown = false;
        }

        private void startVideoPlayback() {
            Log.v(TAG, "startVideoPlayback");
            holder.setFixedSize(mVideoWidth, mVideoHeight);
            mMediaPlayer.start();
        }

}
*/
