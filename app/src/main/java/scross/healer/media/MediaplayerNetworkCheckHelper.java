/*
package scross.healer.media;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import scross.healer.HealerContext;

*/
/**
 * Created by hanee on 2017-08-04.
 *//*


public class MediaplayerNetworkCheckHelper extends BroadcastReceiver {

    Boolean wifiConnect = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        // 네트웍에 변경이 일어났을때 발생하는 부분a
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            Toast.makeText(context, "Active Network Type : " + activeNetInfo.getTypeName(), Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Mobile Network Type : " + mobNetInfo.getTypeName(), Toast.LENGTH_SHORT).show();



        }
    }

    boolean checkInternet(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
        String networkTypeName = ni.getTypeName();

        if (networkTypeName.equals("MOBILE")) {
            Log.d("network type", "Network - > (모바일)" + networkTypeName);
//                Toast.makeText(HealerContext.getContext(), "WIFI 연결상태가 아닙니다. 연결 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
           return wifiConnect = false;

        } else {
            Log.d("network type", "Network - > (와이파이)" + networkTypeName);
           return wifiConnect = true;

        }
    }


}
*/
