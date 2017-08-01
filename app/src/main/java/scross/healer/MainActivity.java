package scross.healer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import scross.healer.home.HomeFragment;
import scross.healer.media.MediaplayerActivity;
import scross.healer.profile.ProfileDialogFragment;
import scross.healer.survay.SurvayFragment;
import scross.healer.emotion.EmotionDialog;
import scross.healer.timeline.TimelineFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment = new HomeFragment();


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


/*
다이얼로그에서 완료해야만 타임라인으로 보내고 스테이트 변경하는거임.

            fragment = new TimelineFragment();
            ChangeFragment();*/



    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //TODO 프레그먼트 붙여야됨.
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

}
