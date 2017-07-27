package scross.healer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;

/**
 * Created by gta2v on 2017-07-26.
 */

public class BaseFragment extends Fragment {


    public BaseFragment() {
        super();
    }


    protected void startFragment(FragmentManager fm, Class<? extends BaseFragment> fragmentClass) {

        BaseFragment fragment = null;
        try {
            fragment = fragmentClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();

        }

        if (fragment == null) {

            throw new IllegalStateException("cannot start fragment. " + fragmentClass.getName());

        }

        getActivity().getFragmentManager().beginTransaction().add(R.id.fragment_home, fragment).addToBackStack(null).commit();

    }

    protected void finishFragment() {

        getFragmentManager().popBackStack();

    }

    public void onPressedBackkey() {
        finishFragment();
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
