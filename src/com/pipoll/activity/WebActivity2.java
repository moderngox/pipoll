package com.pipoll.activity;

import com.pipoll.R;
import com.pipoll.fragment.WebFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class WebActivity2 extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_test);
        
        FragmentManager manager = getSupportFragmentManager();
        
        Fragment fragment1 = manager.findFragmentById(R.id.fragmentContainer1);
        if (fragment1 == null) {
            fragment1 = WebFragment.newInstance("http://www.gamekult.com/");
            manager.beginTransaction()
                .add(R.id.fragmentContainer1, fragment1)
                .commit();
        }
        
        Fragment fragment2 = manager.findFragmentById(R.id.fragmentContainer2);
        if (fragment2 == null) {
            fragment2 = WebFragment.newInstance("http://lemonde.fr/");
            manager.beginTransaction()
                .add(R.id.fragmentContainer2, fragment2)
                .commit();
        }
        
        Fragment fragment3 = manager.findFragmentById(R.id.fragmentContainer3);
        if (fragment3 == null) {
            fragment3 = WebFragment.newInstance("http://www.leparisien.fr");
            manager.beginTransaction()
                .add(R.id.fragmentContainer3, fragment3)
                .commit();
        }
    }
    
}
