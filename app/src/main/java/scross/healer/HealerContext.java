package scross.healer;

import android.app.Application;
import android.content.Context;

import com.tsengvn.typekit.Typekit;

import java.net.URISyntaxException;

/**
 * Created by hanee on 2017-07-18.
 */

public class HealerContext  extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(mContext, "fonts/NanumSquareOTFRegular.otf"))//나눔스퀘어 Regular
                .addBold(Typekit.createFromAsset(mContext, "fonts/NanumSquareOTFBold.otf"))//나눔스퀘어 Bold
                .addCustom1(Typekit.createFromAsset(mContext, "fonts/NanumSquareOTFExtraBold.otf"));//나눔스퀘어 ExtraBold


    }

    public static Context getContext() {
        return mContext;
    }

}
