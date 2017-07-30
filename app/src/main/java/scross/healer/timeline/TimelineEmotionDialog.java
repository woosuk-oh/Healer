package scross.healer.timeline;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import scross.healer.R;
import scross.healer.networkService.EmotionEntityObject;

/**
 * Created by gta2v on 2017-07-23.
 * 콘텐츠 시작 전/후 감정상태 다이얼로그
 */

public class TimelineEmotionDialog extends DialogFragment implements View.OnClickListener {

    private EmotionEntityObject entityObject;
    private LinearLayout emotion1, emotion2, emotion3, emotion4, emotion5, emotion6, emotion7;
    private ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7;
    int emotionValue;


    public static TimelineEmotionDialog newInstance() {

        TimelineEmotionDialog emotionDialog = new TimelineEmotionDialog();
        return emotionDialog;
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

        View view = inflater.inflate(R.layout.fragment_emotion_dialog, container, false);


        if (entityObject == null) {
            entityObject = new EmotionEntityObject();
        }

        emotion1 = (LinearLayout) view.findViewById(R.id.emotion_btn1);
        emotion2 = (LinearLayout) view.findViewById(R.id.emotion_btn2);
        emotion3 = (LinearLayout) view.findViewById(R.id.emotion_btn3);
        emotion4 = (LinearLayout) view.findViewById(R.id.emotion_btn4);
        emotion5 = (LinearLayout) view.findViewById(R.id.emotion_btn5);
        emotion6 = (LinearLayout) view.findViewById(R.id.emotion_btn6);
        emotion7 = (LinearLayout) view.findViewById(R.id.emotion_btn7);

        iv1 = (ImageView) view.findViewById(R.id.emotion_img1);
        iv2 = (ImageView) view.findViewById(R.id.emotion_img2);
        iv3 = (ImageView) view.findViewById(R.id.emotion_img3);
        iv4 = (ImageView) view.findViewById(R.id.emotion_img4);
        iv5 = (ImageView) view.findViewById(R.id.emotion_img5);
        iv6 = (ImageView) view.findViewById(R.id.emotion_img6);
        iv7 = (ImageView) view.findViewById(R.id.emotion_img7);


        emotion1.setOnClickListener(this);
        emotion2.setOnClickListener(this);
        emotion3.setOnClickListener(this);
        emotion4.setOnClickListener(this);
        emotion5.setOnClickListener(this);
        emotion6.setOnClickListener(this);
        emotion7.setOnClickListener(this);



        return view;
    }


    @Override
    public void onClick(View view) {

        //TODO 선택된 이미지로 리소스 변경해야함!
        switch (view.getId()) {
            case R.id.emotion_btn1:
                iv1.setImageResource(R.drawable.nophoto);
                emotionValue = 1;
                break;
            case R.id.emotion_btn2:
                iv2.setImageResource(R.drawable.nophoto);
                emotionValue = 2;
                break;
            case R.id.emotion_btn3:
                iv3.setImageResource(R.drawable.nophoto);
                emotionValue = 3;
                break;
            case R.id.emotion_btn4:
                iv4.setImageResource(R.drawable.nophoto);
                emotionValue = 4;
                break;
            case R.id.emotion_btn5:
                iv5.setImageResource(R.drawable.nophoto);
                emotionValue = 5;
                break;
            case R.id.emotion_btn6:
                iv6.setImageResource(R.drawable.nophoto);
                emotionValue = 6;
                break;
            case R.id.emotion_btn7:
                iv7.setImageResource(R.drawable.nophoto);
                emotionValue = 7;
                break;
            case R.id.emotion_success_btn:

                //TODO emotionValue 네트워크로 값 보내야댐!
                break;

            case R.id.emotion_cancel_btn:
                dismiss();
                break;




        }
    }
}
