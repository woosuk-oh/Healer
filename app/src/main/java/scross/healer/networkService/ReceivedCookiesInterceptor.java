package scross.healer.networkService;

import android.content.Context;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by hoyeonlee on 2017. 7. 27..
 */
public class ReceivedCookiesInterceptor implements Interceptor {

    private DalgonaSharedPreferences mDsp;

    public ReceivedCookiesInterceptor(Context context){
        mDsp = DalgonaSharedPreferences.getInstanceOf(context);
    }


    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }

            mDsp.putHashSet(DalgonaSharedPreferences.KEY_COOKIE, cookies);
        }

        return originalResponse;
    }
}
