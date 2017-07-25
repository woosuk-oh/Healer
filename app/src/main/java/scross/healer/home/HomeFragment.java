package scross.healer.home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import scross.healer.R;
import scross.healer.profile.ProfileDialogFragment;

/**
 * Created by hanee on 2017-07-25.
 */

public class HomeFragment extends Fragment {

    public HomeFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        /*Intent intent = new Intent(getApplicationContext(), MediaplayerActivity.class);
        startActivity(intent);*/


        return view;
    }


}
