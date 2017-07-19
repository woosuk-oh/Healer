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
                .addNormal(Typekit.createFromAsset(mContext, "fonts/NanumSquareRegular.ttf"))//나눔M
                .addBold(Typekit.createFromAsset(mContext, "fonts/NanumSquareBold.ttf"))//나눔B
                .addItalic(Typekit.createFromAsset(mContext, "fonts/Gotham-Black.otf"));//고담B


    }
    // TODO 07-18. 폰트 다운받아서 적용  해야됨. 이전의 폰트로 적용해놨음.

    public static Context getContext() {
        return mContext;
    }

}
