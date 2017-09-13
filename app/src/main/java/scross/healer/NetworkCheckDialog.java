/*
package scross.healer;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;

*/
/**
 * Created by gta2v on 2017-09-12.
 *//*


public class NetworkCheckDialog extends BaseActivity{

    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HealerContext.getContext());
                        alertDialogBuilder.setTitle("Wi-fi 연결된 상태에서만 진행 가능합니다. \n");
                        alertDialogBuilder.setMessage("3G/LTE 모드로 진행 하시려면\n" +
                                "설정에 ’Wi-fi로만 연결’ 에서\n" +
                                "체크 해제를 해 주세요.\n").setCancelable(false);

                        alertDialogBuilder
                                .setPositiveButton("취소", new DialogInterface.OnClickListener() {
        public void onClick(
                DialogInterface dialog, int id) {
            dialog.cancel();
        }
    }).setNegativeButton("설정이동", new DialogInterface.OnClickListener() {
        public void onClick(
                DialogInterface dialog, int id) {

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_home, fragment);
            fragmentTransaction.commit();
        }
    });
}
*/
