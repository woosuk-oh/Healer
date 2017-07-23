package scross.healer.survay;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import scross.healer.BaseActivity;
import scross.healer.R;

/**
 * Created by hanee on 2017-07-18.
 */

public class SurvayFragment extends Fragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survay, container, false);


        return view;
    }

    public SurvayFragment() {
        super();
    }


}
