package com.hh.ehh.responsible;

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hh.ehh.R;
import com.hh.ehh.database.DataBaseSQLiteHelper;
import com.hh.ehh.database.managers.ProfileDatabaseManager;
import com.hh.ehh.model.Profile;
import com.hh.ehh.ui.customdialogs.CustomDialogs;
import com.hh.ehh.ui.profile.ProfileFragment;
import com.hh.ehh.utils.FragmentStackManager;

public class MainActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected DrawerLayout drawerLayout;
    protected FragmentStackManager fragmentStackManager;
    private NavigationView navigationView;
    private ResponsibleHomeFragment responsibleHomeFragment;
    private DataBaseSQLiteHelper dbHelper = null;
    private SQLiteDatabase database = null;
    private Profile profile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responsible_activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        responsibleHomeFragment = new ResponsibleHomeFragment();
        fragmentStackManager = FragmentStackManager.getInstance(this);
        fragmentStackManager.loadFragment(responsibleHomeFragment,R.id.responsiblePatientFrame);
        toolbar.setLogo(R.mipmap.ic_ehh);
        setDatabase();
    }

    private void setDatabase() {
        dbHelper = DataBaseSQLiteHelper.newInstance(getApplicationContext());
        database = dbHelper.getWritableDatabase();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

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
        final Profile profile = getProfileFromDatabase(database);
        if(profile != null && v!=null) {
            TextView name = (TextView) v.findViewById(R.id.username);
            TextView email = (TextView) v.findViewById(R.id.email);
            name.setText(profile.getName());
            email.setText(profile.getEmail());
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profile!=null) {
                    Fragment profileFragment = ProfileFragment.newInstance(profile);
                    fragmentStackManager.loadFragment(profileFragment, R.id.responsiblePatientFrame);
                }
                closeDrawer();
            }
        });

        Menu menuView = navigationView.getMenu();
        menuView.removeItem(1);
        /**
         * Todo esto cambiara cuando los cojamos de base de datos
         */

        menuView.clear();

        android.content.res.Resources res = context.getResources();
        String homeStr = res.getString(R.string.home);
        String patientsListStr = res.getString(R.string.patientsList);
        String medicalCentersStr = res.getString(R.string.medicalCenters);
        String configurationStr = res.getString(R.string.configuration);


        MenuItem item1 = menuView.add(homeStr);
        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                selectItem(1);
                return true;
            }
        });


        MenuItem item2 =  menuView.add(patientsListStr);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                selectItem(2);
                return true;
            }
        });

        MenuItem item3 =  menuView.add(medicalCentersStr);
        item3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                selectItem(3);
                return true;
            }
        });

        MenuItem item4 =  menuView.add(configurationStr);
        item4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                selectItem(4);
                return true;
            }
        });

        return true;
    }


    private void selectItem(int position) {
        Fragment fragment;
        switch (position)
        {
            case 1:
                fragmentStackManager.resetBackStack(responsibleHomeFragment);
                closeDrawer();
                break;
            case 2:
                // update the main content by replacing fragments
                fragment = new PatientsListFragment();
                fragmentStackManager.resetBackStack(responsibleHomeFragment);
                fragmentStackManager.loadFragment(fragment, R.id.responsiblePatientFrame);
                closeDrawer();
                break;
            case 3:
                fragment = new MedicalCentersListFragment();
                fragmentStackManager.resetBackStack(responsibleHomeFragment);
                fragmentStackManager.loadFragment(fragment, R.id.responsiblePatientFrame);
                closeDrawer();
                break;
            case 4:
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
        }else if (fragmentStackManager.popBackStatFragment()) {
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
        if(database!=null){
            dbProfile = ProfileDatabaseManager.getProfile(database);
            dbProfile = new Profile("1","Juan","Pérez","juanperez@gmail.com","Lleida",null,"973234323");
        }
        /*Must be removed*/
        else{
            dbProfile = new Profile("1","Juan","Pérez","juanperez@gmail.com","Lleida",null,"973234323");
            profile = dbProfile;
        }

        return dbProfile;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

}
