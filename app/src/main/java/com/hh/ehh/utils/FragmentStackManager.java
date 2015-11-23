package com.hh.ehh.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class FragmentStackManager {

    private static FragmentStackManager mInstance = null;
    private static Context mContext = null;
    private static String TAG = "FragmentStackManager.class";
    private FragmentManager fragmentManager;

    public FragmentStackManager(){
        try{
            final AppCompatActivity activity = (AppCompatActivity) mContext;
            fragmentManager = activity.getSupportFragmentManager();
        } catch (ClassCastException e) {
            Log.d(TAG, "Can't get the fragment manager with this");
        }
    }

    public static FragmentStackManager getInstance(Context context){
        if(mInstance==null || !mContext.equals(context)){
            mContext = context;
            mInstance =  new FragmentStackManager();
        }
        return mInstance;
    }

    public void loadFragment(Fragment toLoad, int frame){
        String fragmentName = toLoad.getClass().getName();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(frame, toLoad, fragmentName);
        ft.addToBackStack(fragmentName);
        ft.commit();
    }

    public boolean popBackStatFragment(){
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
            fragmentManager.beginTransaction().commit();
            return true;
        }
        return false;
    }

    public void resetBackStack(Fragment toPop, Fragment toLoad, int frame){
        String fragmentName = toLoad.getClass().getName();
        fragmentManager.popBackStack(toPop.getClass().getName(), 0);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(frame, toLoad, fragmentName);
        ft.addToBackStack(fragmentName);
        ft.commit();

    }

}
