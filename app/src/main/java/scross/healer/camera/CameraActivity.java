package scross.healer.camera;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import scross.healer.BaseActivity;
import scross.healer.R;

/**
 * Created by gta2v on 2017-07-23.
 */

public class CameraActivity extends BaseActivity{


    private ImageButton cameraStart;
    private ImageView cameraCheck;
    private TextView contentName;
    private TextView contentDay;
    private TextView contentBody;


    // TODO 네트워크로 부터 state받아와야함. daystate는 메인액티비티에서 받아오고 인텐트로 전달받기!
    int state = 2;
    int dayState = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_before_shoot_picture);


        cameraStart = (ImageButton) findViewById(R.id.camera_start);
        contentDay = (TextView) findViewById(R.id.content_day);
        contentName = (TextView) findViewById(R.id.content_name);
        contentBody = (TextView) findViewById(R.id.content_body);

        cameraCheck = (ImageView) findViewById(R.id.camera_check);


        if (state == 2) {// 네트워크 통신해서 받아와야함. 2단계 임(감정상태 선택후, 사진촬영 전, 컨텐츠 듣기전, ).

            contentBody.setText("시작 전/후로 웃는 얼굴을 촬영 합니다.\n카메라를 보고 '치~즈'라고\n말하며 사진 촬영을 해주세요.");


            // 몇일차인지에 따라 컨텐츠 제목이랑 일자 수정!
            switch (dayState) {

                case 1:
                    cameraCheck.setVisibility(View.VISIBLE);
                    contentDay.setText("1일차");
                    contentName.setText("초기트라우마\n부정적 기억 내보내기");
                    break;
                case 2:
                    cameraCheck.setVisibility(View.VISIBLE);
                    contentDay.setText("2일차");
                    contentName.setText("2일차 컨텐츠제목");
                    break;
                case 3:
                    cameraCheck.setVisibility(View.VISIBLE);
                    contentDay.setText("3일차");
                    contentName.setText("3일차 컨텐츠제목");
                    break;
                case 4:
                    cameraCheck.setVisibility(View.VISIBLE);
                    contentDay.setText("4일차");
                    contentName.setText("4일차 컨텐츠제목");
                    break;
                case 5:
                    cameraCheck.setVisibility(View.VISIBLE);
                    contentDay.setText("5일차");
                    contentName.setText("5일차 컨텐츠제목");
                    break;
                case 6:
                    cameraCheck.setVisibility(View.VISIBLE);
                    contentDay.setText("6일차");
                    contentName.setText("6일차 컨텐츠제목");
                    break;
                case 7:
                    cameraCheck.setVisibility(View.VISIBLE);
                    contentDay.setText("7일차");
                    contentName.setText("7일차 컨텐츠제목");
                    break;
                case 8:
                    cameraCheck.setVisibility(View.VISIBLE);
                    contentDay.setText("8일차");
                    contentName.setText("8일차 컨텐츠제목");
                    break;



            }

        }  else if(state == 6){// ?일차 6단계. 컨텐츠 듣기후, 사진 촬영전,
            cameraCheck.setVisibility(View.VISIBLE);
            contentBody.setText("수고하셨습니다.\n웃는 얼굴을 다시 한 번 촬영 합니다.\n전과 같이 '치~즈'를 외쳐주세요.");

            cameraStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //TODO 카메라 촬영하는 액티비티로 이동
                    //TODO 카메라 촬영 액티비티에서 촬영버튼, 닫기버튼해서 닫기버튼 누르면 state +1 되도록!

                }
            });
        }

    }


}
