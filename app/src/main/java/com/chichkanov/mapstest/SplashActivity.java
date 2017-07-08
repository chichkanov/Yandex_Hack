package com.chichkanov.mapstest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by julia on 08.07.17.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.shared_preferences_name), 0);
        String school = sharedPreferences.getString(getString(R.string.pref_school_name), getString(R.string.pref_default_school));
        Log.d("Splash", school);
        if (school.equals(getString(R.string.pref_default_school))) {
            Intent intent = new Intent(this, PickSchoolNameActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
