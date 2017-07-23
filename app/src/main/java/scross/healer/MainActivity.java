package scross.healer;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import scross.healer.BaseActivity;
import scross.healer.R;
import scross.healer.media.MediaplayerActivity;
import scross.healer.profile.ProfileDialogFragment;
import scross.healer.survay.SurvayFragment;
import scross.healer.timeline.TimelineEmotionDialog;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
            TimelineEmotionDialog dialog = new TimelineEmotionDialog();
            dialog.show(getFragmentManager(), "Timeline Emotion Test");


            /*Intent intent = new Intent(getApplicationContext(), TimelineFragment.class);
            startActivity(intent);*/

            // Handle the camera action
        } else if (id == R.id.survay) {
        /*    Intent intent = new Intent(getApplicationContext(), SurvayFragment.class);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.right_container, new FragmentB(), FRAGMENT_TAG);
            ft.commit();
*/
        } else if (id == R.id.profile) {
            ProfileDialogFragment dialog = new ProfileDialogFragment();
            dialog.show(getFragmentManager(), "Profile Update test");

        } else if (id == R.id.setting) {

            Intent intent = new Intent(getApplicationContext(), MediaplayerActivity.class);
            startActivity(intent);
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


}
