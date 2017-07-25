package scross.healer.survay;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import scross.healer.BaseActivity;
import scross.healer.R;
import scross.healer.profile.ProfileDialogFragment;

/**
 * Created by hanee on 2017-07-18.
 */

public class SurvayFragment extends Fragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar

        inflater.inflate(R.menu.actionbar_menu_survay, menu);
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
        View view = inflater.inflate(R.layout.fragment_survay, container, false);


        return view;
    }

    public SurvayFragment() {
        super();
    }


}
