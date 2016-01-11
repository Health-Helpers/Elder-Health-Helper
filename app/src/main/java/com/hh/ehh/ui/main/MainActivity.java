package com.hh.ehh.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hh.ehh.R;
import com.hh.ehh.database.DataBaseSQLiteHelper;
import com.hh.ehh.database.managers.ProfileDatabaseManager;
import com.hh.ehh.model.Profile;
import com.hh.ehh.ui.customdialogs.CustomDialogs;
import com.hh.ehh.ui.medicalcenters.MedicalCentersListFragment;
import com.hh.ehh.ui.patients.PatientsListFragment;
import com.hh.ehh.ui.profile.ProfileFragment;
import com.hh.ehh.ui.settings.ResponsibleSettingsFragment;
import com.hh.ehh.utils.FragmentStackManager;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;


public class MainActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected DrawerLayout drawerLayout;
    protected FragmentStackManager fragmentStackManager;
    private NavigationView navigationView;
    private HomeFragment responsibleHomeFragment;
    private DataBaseSQLiteHelper dbHelper = null;
    private SQLiteDatabase database = null;
    private Profile profile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        responsibleHomeFragment = new HomeFragment();
        fragmentStackManager = FragmentStackManager.getInstance(this);
        fragmentStackManager.loadFragment(responsibleHomeFragment, R.id.responsiblePatientFrame);
        toolbar.setLogo(R.mipmap.ic_ehh);
        setDatabase();
        profile = getProfileFromDatabase(database);


        /****Push Notifications*****/
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this, "l3gHQ5XzYri2jGFACJkZKiupEHe3xDF2MgoRbrz4", "rppSTdZZ75f7rrY56ljsYtQsqivqg2XaxfH1hp3P");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }

    private void setDatabase() {
        dbHelper = DataBaseSQLiteHelper.newInstance(getApplicationContext());
        database = dbHelper.getWritableDatabase();
    }


    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectItem(menuItem.getItemId());
                        return true;
                    }
                }
        );

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        View v = findViewById(R.id.header_view);
        Context context = v.getContext();
        if (profile != null && v != null) {
            TextView name = (TextView) v.findViewById(R.id.username);
            TextView email = (TextView) v.findViewById(R.id.email);
            name.setText(profile.getName());
            email.setText(profile.getEmail());
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profile != null) {
                    Fragment profileFragment = ProfileFragment.newInstance(profile);
                    fragmentStackManager.loadFragment(profileFragment, R.id.responsiblePatientFrame);
                }
                closeDrawer();
            }
        });

        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectItem(int itemId) {
        Fragment fragment;
        switch (itemId) {
            case R.id.nav_home:
                fragmentStackManager.resetBackStack(responsibleHomeFragment);
                closeDrawer();
                break;
            case R.id.nav_patients:
                fragment = PatientsListFragment.newInstance(profile);
                fragmentStackManager.resetBackStack(responsibleHomeFragment);
                fragmentStackManager.loadFragment(fragment, R.id.responsiblePatientFrame);
                closeDrawer();
                break;
            case R.id.nav_medical_centers:
                fragment = new MedicalCentersListFragment();
                fragmentStackManager.resetBackStack(responsibleHomeFragment);
                fragmentStackManager.loadFragment(fragment, R.id.responsiblePatientFrame);
                closeDrawer();
                break;
            case R.id.nav_config:
                fragment = new ResponsibleSettingsFragment();
                fragmentStackManager.resetBackStack(responsibleHomeFragment);
                fragmentStackManager.loadFragment(fragment, R.id.responsiblePatientFrame);
                closeDrawer();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (closeDrawer()) {
        } else if (fragmentStackManager.popBackStatFragment()) {
        } else {
            CustomDialogs.closeDialog(MainActivity.this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    }

    protected boolean closeDrawer() {
        boolean state = drawerLayout.isDrawerOpen(GravityCompat.START);
        if (state) {
            drawerLayout.closeDrawers();
        }
        return state;
    }

    private Profile getProfileFromDatabase(SQLiteDatabase database) {
        Profile dbProfile = null;
        if (database != null) {
            dbProfile = ProfileDatabaseManager.getProfile(database);
            dbProfile = new Profile("1", "Juan", "Pérez", "juanperez@gmail.com", "Lleida", null, "973234323");
        }
        /*Must be removed*/
        else {
            dbProfile = new Profile("1", "Juan", "Pérez", "juanperez@gmail.com", "Lleida", null, "973234323");
            profile = dbProfile;
        }

        return dbProfile;
    }

}
