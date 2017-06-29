package com.intownexec.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.intownexec.chat.R;
import com.intownexec.chat.dialogs.PostImageChooseDialog;
import com.intownexec.chat.fragment.ProfileOneFragment;
import com.intownexec.chat.fragment.ProfileThreeFragment;

/**
 * Created by OLUWAPHEMMY on 5/29/2017.
 */

public class ProfileSetupActivity extends AppCompatActivity implements PostImageChooseDialog.AlertPositiveListener {

    private static final String TAG = "ProfileSetupActivity";
    Fragment mFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Intownexec - Profile Set up");
        setSupportActionBar(toolbar);

        ProfileOneFragment fragment = new ProfileOneFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.profileSetUpFrame, fragment);
        ft.commit();

        mFragment = new ProfileThreeFragment();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        mFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        mFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onImageFromGallery() {

        ProfileThreeFragment p = (ProfileThreeFragment) mFragment;
        p.imageFromGallery();
    }

    @Override
    public void onImageFromCamera() {

        ProfileThreeFragment p = (ProfileThreeFragment) mFragment;
        p.imageFromCamera();
    }

    @Override
    public void onVideoFromGallery() {

    }
}
