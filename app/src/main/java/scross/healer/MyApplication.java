package scross.healer;

import android.app.Application;
import android.content.Context;

/**
 * Created by hoyeonlee on 2017. 7. 27..
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getInstance(){
        return instance;
    }
    public static Context getContext(){
        return instance;
    }
    @Override
    public void onCreate(){
        instance = this;
        super.onCreate();
    }
}
