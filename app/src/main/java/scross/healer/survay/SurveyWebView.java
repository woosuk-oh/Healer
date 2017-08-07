package scross.healer.survay;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import scross.healer.BaseFragment;
import scross.healer.R;
import scross.healer.emotion.EmotionDialog;

/**
 * Created by gta2v on 2017-08-04.
 */

public class SurveyWebView extends BaseFragment {

    private static final String SETURL = "setUrl";
    WebView webView;


    String url;

    public static SurveyWebView newInstance(String param1) {
        SurveyWebView fragment = new SurveyWebView();
        Bundle args = new Bundle();
        args.putString(SETURL, param1);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

/*    public void setUrl(String url) {
        this.url = url;
        Log.e("this.url: ",this.url);
        Log.e("url: ",url);
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);
        webView = (WebView) rootView.findViewById(R.id.webview);


        Bundle bundle = getArguments();
        url = bundle.getString(SETURL);
        Log.e("url1: ",url+"");


        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(this.url);

        Log.e("url2: ",url+"");
        Log.e("onCreate this url: ",this.url+"");


        webView.setWebViewClient(new WebViewClientClass());
        webView.setVerticalScrollBarEnabled(true);


//        webView.setListener(SurvayFragment.class, this);

        // ...

        return rootView;
    }





/*
    webView.setWebViewClient(new WebViewClient(){
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            et.setText(url);

        }
    });*/

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            Log.e("url3: ",url+"");



            return true;
        }
    }
}
