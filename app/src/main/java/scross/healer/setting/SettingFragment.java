package scross.healer.setting;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import scross.healer.BaseFragment;
import scross.healer.HealerContext;
import scross.healer.R;
import scross.healer.SharedPreferenceUtil;

/**
 * Created by hanee on 2017-07-18.
 */

public class SettingFragment extends BaseFragment {

    CheckBox networkTypeCheck;
    SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(HealerContext.getContext());
    int networkType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        networkTypeCheck = (CheckBox) view.findViewById(R.id.network_type_check);


        networkType = sharedPreferenceUtil.getSaveNetworkType();

        if(networkType == 0){
            networkTypeCheck.setChecked(false);
        }else{
            networkTypeCheck.setChecked(true);
        }



        networkTypeCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    sharedPreferenceUtil.setSaveNetworkType(1);
                }else{
                    sharedPreferenceUtil.setSaveNetworkType(0);
                }
            }
        });


        return view;
    }

    public SettingFragment() {
        super();
    }

}
