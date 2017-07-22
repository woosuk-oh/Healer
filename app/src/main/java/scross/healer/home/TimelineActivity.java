package scross.healer.home;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import scross.healer.BaseActivity;
import scross.healer.R;

/**
 * Created by hanee on 2017-07-18.
 */

public class TimelineActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);


        setContentView(R.layout.activity_timeline_body);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        ActionBar actionBar = getActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#550000ff")));
        actionBar.setTitle("타임라인");
        actionBar.setDisplayShowTitleEnabled(true);


    }
}
