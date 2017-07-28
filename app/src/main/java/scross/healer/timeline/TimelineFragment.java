package scross.healer.timeline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import scross.healer.BaseFragment;
import scross.healer.R;
import scross.healer.camera.CameraActivity;
import scross.healer.camera.TakePictureActivity;
import scross.healer.profile.ProfileDialogFragment;

/**
 * Created by hanee on 2017-07-18.
 */

public class TimelineFragment extends BaseFragment implements View.OnClickListener {
    public TimelineFragment() {
        super();
    }

    private LinearLayout content1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar

        inflater.inflate(R.menu.actionbar_menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                ProfileDialogFragment dialog = new ProfileDialogFragment();
                dialog.show(getFragmentManager(), "Profile Update test");
                return true;

            default:
                return true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

//        TimelineEmotionDialog dialog = new TimelineEmotionDialog();
//        dialog.show(getFragmentManager(), "Timeline Emotion Test");
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        content1 = (LinearLayout) view.findViewById(R.id.timeline_content6);
        content1.setOnClickListener(this);


        return view;

        //TODO 텍스트가 25자 이상이면 ...으로 표시
        //TODO 진행 완료한 컨텐츠는 content_name 옆에 +' 완료' 추가하기.
        //TODO 상세보기 누르면 왼쪽 아이콘이랑 오른쪽 리니어레이아웃 탑마진 주기
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.timeline_content6:

                 Intent intent = new Intent(getActivity(), CameraActivity.class);
            startActivity(intent);

                break;

        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);


        setContentView(R.layout.activity_timeline_body);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon
        toolbar.setTitle("타임라인");*/


/*
        ActionBar actionBar = getActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#550000ff")));
        actionBar.setTitle("타임라인");
        actionBar.setDisplayShowTitleEnabled(true);*/



