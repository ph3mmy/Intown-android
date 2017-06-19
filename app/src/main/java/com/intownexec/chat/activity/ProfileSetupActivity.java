package com.intownexec.chat.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.intownexec.chat.R;
import com.intownexec.chat.fragment.ProfileOneFragment;

/**
 * Created by OLUWAPHEMMY on 5/29/2017.
 */

public class ProfileSetupActivity extends AppCompatActivity {

    private static final String TAG = "ProfileSetupActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_setup);

        ProfileOneFragment fragment = new ProfileOneFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.profileSetUpFrame, fragment);
        ft.commit();

    }
}
