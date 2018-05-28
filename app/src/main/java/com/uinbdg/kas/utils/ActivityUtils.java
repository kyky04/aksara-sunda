package com.uinbdg.kas.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by knalb on 19/07/17.
 */

public class ActivityUtils {
    public static void addFragment(Context context, int id, Fragment fragment) {
        ((AppCompatActivity) context).getSupportFragmentManager()
                .beginTransaction()
                .add(id, fragment)
                .commit();
    }

    public static void addFragment(Context context, int id, Fragment fragment, String TAG) {
        ((AppCompatActivity) context).getSupportFragmentManager()
                .beginTransaction()
                .add(id, fragment, TAG)
                .commit();
    }

    public static void replaceFragment(Context context, int id, Fragment fragment ) {
        ((AppCompatActivity) context).getSupportFragmentManager()
                .beginTransaction()
                .replace(id, fragment)
                .commit();
    }

    public static void replaceFragment(Context context, int id, Fragment fragment, String TAG) {
        ((AppCompatActivity) context).getSupportFragmentManager()
                .beginTransaction()
                .replace(id, fragment, TAG)
                .commit();
    }

    public static void removeFragment(Context context, Fragment fragment) {
        ((AppCompatActivity) context).getSupportFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();
    }
}
