package scross.healer.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import scross.healer.R;
import static android.content.ContentValues.TAG;


/**
 * Created by gta2v on 2017-07-28.
 */

public class CameraActivity2 extends AppCompatActivity {

    Button cameraButton;
    private Handler mBackgroundHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);


        final CameraPreview surfaceView = (CameraPreview) findViewById(R.id.activity_camera_test);
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(surfaceView);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        cameraButton = (Button) findViewById(R.id.button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surfaceView.takePhoto(new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(final byte[] data, Camera camera) {

                        Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                        Log.v("","");



                        Log.d(TAG, "onPictureTaken " + data.length);

                        getBackgroundHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                                        "picture.jpg");
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
                            }
                        });
                    }
                });
            }
        });
    }
    private Handler getBackgroundHandler() {
        if (mBackgroundHandler == null) {
            HandlerThread thread = new HandlerThread("background");
            thread.start();
            mBackgroundHandler = new Handler(thread.getLooper());
        }
        return mBackgroundHandler;
    }
}
