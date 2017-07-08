package com.chichkanov.mapstest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by julia on 08.07.17.
 */

public class PickSchoolNameActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonDesignScool;
    private Button mButtonInterfacesScool;
    private Button mButtonDevelopmentScool;
    private Button mButtonManagersScool;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickschool);
        mButtonDesignScool = (Button) findViewById(R.id.bn_design_school);
        mButtonInterfacesScool = (Button) findViewById(R.id.bn_interfaces_school);
        mButtonDevelopmentScool =(Button) findViewById(R.id.bn_mobile_school);
        mButtonManagersScool = (Button) findViewById(R.id.bn_managers_school);
        mButtonManagersScool.setOnClickListener(this);
        mButtonDevelopmentScool.setOnClickListener(this);
        mButtonDesignScool.setOnClickListener(this);
        mButtonInterfacesScool.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        SharedPreferences.Editor editor = this.getSharedPreferences(getString(R.string.shared_preferences_name), 0).edit();

        switch(id) {
            case R.id.bn_design_school:
                editor.putString(getString(R.string.pref_school_name), getString(R.string.pref_design_school));
                break;
            case R.id.bn_interfaces_school:
                editor.putString(getString(R.string.pref_school_name), getString(R.string.pref_interfaces_school));
                break;
            case R.id.bn_managers_school:
                editor.putString(getString(R.string.pref_school_name), getString(R.string.pref_management_school));
                break;
            case R.id.bn_mobile_school:
                editor.putString(getString(R.string.pref_school_name), getString(R.string.pref_development_school));
                break;
        }
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
