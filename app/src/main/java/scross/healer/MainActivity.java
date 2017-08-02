package scross.healer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scross.healer.home.HomeFragment;
import scross.healer.media.MediaplayerActivity;
import scross.healer.networkService.NetworkApi;
import scross.healer.networkService.NetworkService;
import scross.healer.profile.ProfileDialogFragment;
import scross.healer.survay.SurvayFragment;
import scross.healer.emotion.EmotionDialog;
import scross.healer.timeline.TimelineFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment = new HomeFragment();
//    BackPressCloseHandler backPressCloseHandler;

    int state;
    NetworkService apiService;


    private TextView navHeadName;
    private CircleImageView navHeadImage;
    private TextView navHeadState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



/*


        startActivity(new Intent(this, LoginActivity.class));
        finish();

*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_home, fragment);
        fragmentTransaction.commit();



        View header = navigationView.getHeaderView(0);


        navHeadName = (TextView)header.findViewById(R.id.nav_head_name);
        navHeadImage = (CircleImageView)header.findViewById(R.id.nav_head_image);
        navHeadState = (TextView)header.findViewById(R.id.nav_head_state);



        network();


/*
다이얼로그에서 완료해야만 타임라인으로 보내고 스테이트 변경하는거임.

            fragment = new TimelineFragment();
            ChangeFragment();*/



    }



/*
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        FragmentManager fragmentManager = getFragmentManager();

        TimelieFragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id., fragment);
        fragmentTransaction.commit();

        inflater.inflate(R.layout.example_fragment, container, false);

        return super.onCreateView(parent, name, context, attrs);
    }*/
    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.timeline) {

            fragment = new TimelineFragment();
            ChangeFragment();


            // Handle the camera action
        } else if (id == R.id.survay) {

            fragment = new SurvayFragment();
            ChangeFragment();


        } else if (id == R.id.profile) {
            ProfileDialogFragment dialog = new ProfileDialogFragment();
            dialog.show(getFragmentManager(), "Profile Update test");

        } else if (id == R.id.setting) {

            Intent intent = new Intent(getApplicationContext(), MediaplayerActivity.class);
            startActivity(intent);

          /*  fragment = new SettingFragment();
            ChangeFragment();*/

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
/*

  NavigationView.MarginLayoutParams


    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        Menu menu = navigationView.getMenu();



        menu.findItem(R.id.nav_profile).setTitle("My Account");
        menu.findItem(R.id.nav_mng_task).setTitle("Control Task");
        //menu.findItem(R.id.nav_pkg_manage).setVisible(false);//In case you want to remove menu item
        navigationView.setNavigationItemSelectedListener(this);
*/


    public void ChangeFragment() {


   /*     switch( v.getId() ) {
            default:
            case R.id.button1: {
                fragment = new FirstFragment();
                break;
            }
            case R.id.button2: {
                fragment = new SecondFragment();
                break;
            }
        }*/

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_home, fragment);
        fragmentTransaction.commit();
    }
    public void network(){


        apiService = NetworkApi.getInstance(HealerContext.getContext()).getServce();
        Call<ResponseBody> getTimeline = apiService.timeline();
        getTimeline.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String imageUrl;


                if(response.body()!= null){
                    try{
                        JSONObject data = new JSONObject(response.body().string());
                        String code = data.get("code").toString();
                        if(code.equals("1")){
                            JSONObject results = data.getJSONObject("results");


                            Log.e("main nav:",results.getString("name"));


                            navHeadName.setText(results.getString("name"));

                            imageUrl = results.getString("profile");

                            if(!results.isNull("profile")){//프로필 이미지 널이 아닌 경우
                                Glide.with(HealerContext.getContext()).load(imageUrl).into(navHeadImage);
                            }


                        }else{
                            Toast.makeText(HealerContext.getContext().getApplicationContext(), "다시 시도해주세요", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(HealerContext.getContext().getApplicationContext(), "서버오류입니다", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
//            super.onBackPressed();

//            backPressCloseHandler.onBackPressed();
        }
    }
}
