package com.intownexec.chat.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.intownexec.chat.R;
import com.intownexec.chat.fragment.NewSignupFragment;

/**
 * Created by OLUWAPHEMMY on 5/25/2017.
 */

public class NewSignupActivity extends AppCompatActivity {

    private static final String TAG = "NewSignupActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_signup);

/*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sign Up");
        setSupportActionBar(toolbar);*/

        NewSignupFragment fragment = new NewSignupFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.newSignupFrame, fragment);
        ft.commit();



    }
}
