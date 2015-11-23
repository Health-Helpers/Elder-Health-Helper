package com.hh.ehh.responsible;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.Toast;


import com.hh.ehh.R;
import com.hh.ehh.ui.customdialogs.CustomDialogs;
import com.hh.ehh.ui.profile.ProfileFragment;
import com.hh.ehh.utils.FragmentStackManager;

public class MainActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected DrawerLayout drawerLayout;
    protected FragmentStackManager fragmentStackManager;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responsible_activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        fragmentStackManager = FragmentStackManager.getInstance(this);
        toolbar.setLogo(R.mipmap.ic_ehh);
    }

    private void initView() {
        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //   getMenuInflater().inflate(R.menu.menu_main, menu);

        View v = findViewById(R.id.header_view);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment profile = new ProfileFragment();
                fragmentStackManager.loadFragment(profile, R.id.responsiblePatientFrame);
                closeDrawer();
            }
        });

        Menu menuView = navigationView.getMenu();
        menuView.removeItem(1);
        /**
         * Todo esto cambiara cuando los cojamos de base de datos
         */
        MenuItem item1 = menuView.add("Home");
        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });

        MenuItem item2 =  menuView.add("List of patients");
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                selectItem(2);
                return true;
            }
        });

        MenuItem item3 =  menuView.add("Medic Centers");
        item3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });

        MenuItem item4 =  menuView.add("Configuration");
        item4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                selectItem(4);
                return true;
            }
        });

        MenuItem item5 =  menuView.add("Emparejar");
        item5.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                selectItem(5);
                return true;
            }
        });


//
//        MenuItem item3 =menuView.add ("Paciente 3");
//        item3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                selectItem(2);
//                return true;
//            }
//        });
//
//        MenuItem item4 = menuView.add ("Paciente 4");
//        item4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                selectItem(3);
//                return true;
//            }
//        });
//
//        MenuItem item5 = menuView.add("Paciente 5");
//        item5.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                selectItem(4);
//                return true;
//            }
//        });
//
//        MenuItem item6 =  menuView.add("Paciente 6");
//        item6.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                selectItem(5);
//                return true;
//            }
//        });
//
        return true;
    }


    private void selectItem(int position) {

        Fragment fragment;
        Intent intents = null;

        switch (position)
        {
            case 2:
                // update the main content by replacing fragments
                fragment = new PatientsListFragment();
//        Bundle args = new Bundle();
//        args.putInt(PatientFragment.ARG_PATIENT_NUMBER, position);
//        fragment.setArguments(args);
//        fragmentStackManager.resetBackStack(initialFragment,fragment,R.id.content_frame);
                fragmentStackManager.loadFragment(fragment, R.id.responsiblePatientFrame);
                closeDrawer();
                break;
            case 4:
                fragment = new ResponsibleSettingsFragment();
                fragmentStackManager.loadFragment(fragment, R.id.responsiblePatientFrame);
                closeDrawer();
                break;
            case 5:
                fragment = new ResponsibleBluetoothFragment();
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

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

}
