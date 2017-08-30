/*
package scross.healer.networkService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.telecom.Call;
import android.util.Log;
import android.view.WindowManager;

import retrofit2.Callback;
import retrofit2.Response;



public class CustomCallBack<T> implements Callback<T> {
    Context context;
    private ProgressDialog mProgressDialog;
    private Callback<T> mCallback;


    public CustomCallBack(Context context) {

        this.context = context;
        mProgressDialog = new ProgressDialog(context);

        ((Activity) context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("로딩중");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void onResponse(retrofit2.Call<T> call, Response<T> response) {
        Log.e("CustomCallback","온리스폰스");
        mCallback.onResponse(call, response);


        if(mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        }
    }

    @Override
    public void onFailure(retrofit2.Call<T> call, Throwable t) {
        Log.e("CustomCallback","온페일");
        mCallback.onFailure(call, t);

        if(mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        }
    }
}
*/
