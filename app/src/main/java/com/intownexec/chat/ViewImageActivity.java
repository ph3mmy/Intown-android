package com.intownexec.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.intownexec.chat.common.ActivityBase;
import com.intownexec.chat.dialogs.CommentActionDialog;
import com.intownexec.chat.dialogs.CommentDeleteDialog;
import com.intownexec.chat.dialogs.MixedCommentActionDialog;
import com.intownexec.chat.dialogs.MyCommentActionDialog;
import com.intownexec.chat.dialogs.MyPhotoActionDialog;
import com.intownexec.chat.dialogs.PhotoActionDialog;
import com.intownexec.chat.dialogs.PhotoDeleteDialog;
import com.intownexec.chat.dialogs.PhotoReportDialog;


public class ViewImageActivity extends ActivityBase implements CommentDeleteDialog.AlertPositiveListener, CommentActionDialog.AlertPositiveListener, MyCommentActionDialog.AlertPositiveListener, PhotoDeleteDialog.AlertPositiveListener, PhotoReportDialog.AlertPositiveListener, MyPhotoActionDialog.AlertPositiveListener, PhotoActionDialog.AlertPositiveListener, MixedCommentActionDialog.AlertPositiveListener {

    Toolbar mToolbar;

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState != null) {

            fragment = getSupportFragmentManager().getFragment(savedInstanceState, "currentFragment");

        } else {

            fragment = new ViewImageFragment();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_body, fragment).commit();
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {

        super.onSaveInstanceState(outState);

        getSupportFragmentManager().putFragment(outState, "currentFragment", fragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {

        ViewImageFragment p = (ViewImageFragment) fragment;
        p.hideEmojiKeyboard();

        super.onPause();
    }

    @Override
    public void onPhotoDelete(int position) {

        ViewImageFragment p = (ViewImageFragment) fragment;
        p.onPhotoDelete(position);
    }

    @Override
    public void onPhotoReport(int position, int reasonId) {

        ViewImageFragment p = (ViewImageFragment) fragment;
        p.onPhotoReport(position, reasonId);
    }

    @Override
    public void onPhotoRemoveDialog(int position) {

        ViewImageFragment p = (ViewImageFragment) fragment;
        p.remove(position);
    }

    @Override
    public void onPhotoReportDialog(int position) {

        ViewImageFragment p = (ViewImageFragment) fragment;
        p.report(position);
    }

    @Override
    public void onCommentRemove(int position) {

        ViewImageFragment p = (ViewImageFragment) fragment;
        p.onCommentRemove(position);
    }

    @Override
    public void onCommentDelete(int position) {

        ViewImageFragment p = (ViewImageFragment) fragment;
        p.onCommentDelete(position);
    }

    @Override
    public void onCommentReply(int position) {

        ViewImageFragment p = (ViewImageFragment) fragment;
        p.onCommentReply(position);
    }

    @Override
    public void onBackPressed(){

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case android.R.id.home: {

                finish();
                return true;
            }

            default: {

                return super.onOptionsItemSelected(item);
            }
        }
    }
}
