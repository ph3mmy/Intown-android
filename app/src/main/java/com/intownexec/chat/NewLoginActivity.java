package com.intownexec.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.intownexec.chat.fragment.NewLoginFragment;

/**
 * Created by OLUWAPHEMMY on 5/23/2017.
 */

public class NewLoginActivity extends AppCompatActivity {

    private static final String TAG = "NewLoginActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);

        NewLoginFragment fragment = new NewLoginFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frameContainer, fragment);
        ft.commit();


    }
}
