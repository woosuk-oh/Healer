package scross.healer.profile;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import scross.healer.R;
import scross.healer.timeline.TimelineEmotionDialog;

/**
 * Created by hanee on 2017-07-18.
 */

public class ProfileDialogFragment extends DialogFragment implements View.OnClickListener {

    @Override
    public void onClick(View view) {

    }

    public static ProfileDialogFragment newInstance() {

        ProfileDialogFragment profileDialog = new ProfileDialogFragment();
        return profileDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.CustomDialog);


    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.6f;
        window.setAttributes(params);
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_update_dialog, container, false);

        return  view;
    }
}
