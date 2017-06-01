package com.intownexec.chat.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.intownexec.chat.AppActivity;
import com.intownexec.chat.DialogsFragment;
import com.intownexec.chat.FeedFragment;
import com.intownexec.chat.FragmentDrawer;
import com.intownexec.chat.FriendsFragment;
import com.intownexec.chat.GalleryFragment;
import com.intownexec.chat.GuestsFragment;
import com.intownexec.chat.LikedFragment;
import com.intownexec.chat.LikesFragment;
import com.intownexec.chat.MainActivity;
import com.intownexec.chat.NotificationsFragment;
import com.intownexec.chat.PeopleNearbyFragment;
import com.intownexec.chat.ProfileFragment;
import com.intownexec.chat.R;
import com.intownexec.chat.SearchFragment;
import com.intownexec.chat.SettingsActivity;
import com.intownexec.chat.StreamFragment;
import com.intownexec.chat.UpgradesFragment;
import com.intownexec.chat.app.App;
import com.intownexec.chat.common.ActivityBase;
import com.intownexec.chat.dialogs.FriendRequestActionDialog;
import com.intownexec.chat.dialogs.ImageChooseDialog;
import com.intownexec.chat.dialogs.MyPhotoActionDialog;
import com.intownexec.chat.dialogs.PeopleNearbySettingsDialog;
import com.intownexec.chat.dialogs.PhotoDeleteDialog;
import com.intownexec.chat.dialogs.ProfileBlockDialog;
import com.intownexec.chat.dialogs.ProfileReportDialog;
import com.intownexec.chat.dialogs.SearchSettingsDialog;
import com.intownexec.chat.util.CustomRequest;
import com.pkmmte.view.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivityNew extends ActivityBase implements FragmentDrawer.FragmentDrawerListener,
        ImageChooseDialog.AlertPositiveListener, ProfileReportDialog.AlertPositiveListener,
        ProfileBlockDialog.AlertPositiveListener, PhotoDeleteDialog.AlertPositiveListener,
        MyPhotoActionDialog.AlertPositiveListener, FriendRequestActionDialog.AlertPositiveListener,
        SearchSettingsDialog.AlertPositiveListener, PeopleNearbySettingsDialog.AlertPositiveListener,
        NavigationView.OnNavigationItemSelectedListener {

    Toolbar mToolbar;

    private FragmentDrawer drawerFragment;

    // used to store app title
    private CharSequence mTitle;

    LinearLayout mContainerAdmob;

    Fragment fragment;
    Boolean action = false;
    int page = 0;

    TextView userFullname, userUsername;
    CircularImageView userPhoto;
    ImageView userCover;
    ImageLoader imageLoader = App.getInstance().getImageLoader();

    private Boolean restore = false;
    private boolean loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        if (savedInstanceState != null) {

            //Restore the fragment's instance
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, "currentFragment");

            restore = savedInstanceState.getBoolean("restore");
            mTitle = savedInstanceState.getString("mTitle");

        } else {

            fragment = new Fragment();

            restore = false;
            mTitle = getString(R.string.app_name);
        }

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container_body, fragment).commit();
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(mTitle);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUpNavHeader(navigationView);

        setUpAdView();
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /*@Override
    public void onBackPressed() {
        if (drawerFragment.isDrawerOpen()) {
            drawerFragment.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_people:
                displayView(10);
                break;
            case R.id.nav_exec:
                displayView(4);
                break;
            case R.id.nav_business:

                break;
            case R.id.nav_messages:
                displayView(4);
                break;
            case R.id.nav_matches:

                break;
            case R.id.nav_connection:

                break;
            case R.id.nav_visitors:
                displayView(6);
                break;
            case R.id.nav_logout:
                logOutUser();
                break;
            case R.id.nav_settings:
                Intent i = new Intent(MainActivityNew.this, SettingsActivity.class);
                startActivity(i);
                break;
        }
        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {

        displayView(position);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putBoolean("restore", true);
        outState.putString("mTitle", getSupportActionBar().getTitle().toString());
        getSupportFragmentManager().putFragment(outState, "currentFragment", fragment);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onChangeDistance(int position) {

        PeopleNearbyFragment p = (PeopleNearbyFragment) fragment;
        p.onChangeDistance(position);
    }

    @Override
    public void onCloseSettingsDialog(int searchGender, int searchOnline, int searchAgeFrom, int searchAgeTo) {

        SearchFragment p = (SearchFragment) fragment;
        p.onCloseSettingsDialog(searchGender, searchOnline, searchAgeFrom, searchAgeTo);
    }

    @Override
    public void onAcceptRequest(int position) {

        NotificationsFragment p = (NotificationsFragment) fragment;
        p.onAcceptRequest(position);
    }

    @Override
    public void onRejectRequest(int position) {

        NotificationsFragment p = (NotificationsFragment) fragment;
        p.onRejectRequest(position);
    }

    @Override
    public void onPhotoDelete(int position) {

        GalleryFragment p = (GalleryFragment) fragment;
        p.onPhotoDelete(position);
    }

    @Override
    public void onPhotoRemoveDialog(int position) {

        GalleryFragment p = (GalleryFragment) fragment;
        p.onPhotoRemove(position);
    }

    @Override
    public void onImageFromGallery() {

        ProfileFragment p = (ProfileFragment) fragment;
        p.imageFromGallery();
    }

    @Override
    public void onImageFromCamera() {

        ProfileFragment p = (ProfileFragment) fragment;
        p.imageFromCamera();
    }

    @Override
    public void onProfileReport(int position) {

        ProfileFragment p = (ProfileFragment) fragment;
        p.onProfileReport(position);
    }

    @Override
    public void onProfileBlock() {

        ProfileFragment p = (ProfileFragment) fragment;
        p.onProfileBlock();
    }


    private void displayView(int position) {

        action = false;

        switch (position) {

            case 0: {

                break;
            }

            case 1: {

                page = 1;

                fragment = new ProfileFragment();
                getSupportActionBar().setTitle(R.string.page_1);

                action = true;

                break;
            }

            case 2: {

                page = 2;

                fragment = new GalleryFragment();
                getSupportActionBar().setTitle(R.string.page_2);

                action = true;

                break;
            }

            case 3: {

                page = 3;

                fragment = new FriendsFragment();
                getSupportActionBar().setTitle(R.string.page_3);
                action = true;

                break;
            }

            case 4: {

                page = 4;

                fragment = new DialogsFragment();
                getSupportActionBar().setTitle(R.string.page_4);

                action = true;

                break;
            }

            case 5: {

                page = 5;

                fragment = new NotificationsFragment();
                getSupportActionBar().setTitle(R.string.page_5);

                action = true;

                break;
            }

            case 6: {

                page = 6;

                fragment = new GuestsFragment();
                getSupportActionBar().setTitle(R.string.page_6);

                action = true;

                break;
            }

            case 7: {

                page = 7;

                fragment = new LikesFragment();
                getSupportActionBar().setTitle(R.string.page_7);

                action = true;

                break;
            }

            case 8: {

                page = 8;

                fragment = new LikedFragment();
                getSupportActionBar().setTitle(R.string.page_8);

                action = true;

                break;
            }

            case 9: {

                page = 9;

                // Upgrade

                fragment = new UpgradesFragment();
                getSupportActionBar().setTitle(R.string.page_9);

                action = true;

                break;
            }

            case 10: {

                page = 10;

                // People Nearby

                fragment = new PeopleNearbyFragment();
                getSupportActionBar().setTitle(R.string.page_10);

                action = true;

                break;
            }

            case 11: {

                page = 11;

                // Photos Stream

                fragment = new StreamFragment();
                getSupportActionBar().setTitle(R.string.page_14);

                action = true;

                break;
            }

            case 12: {

                page = 12;

                // Photos Stream

                fragment = new FeedFragment();
                getSupportActionBar().setTitle(R.string.page_15);

                action = true;

                break;
            }

            case 13: {

                page = 13;

                fragment = new SearchFragment();
                getSupportActionBar().setTitle("");

                action = true;

                break;
            }

            default: {

                Intent i = new Intent(MainActivityNew.this, SettingsActivity.class);
                startActivity(i);
            }
        }

        if (action) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container_body, fragment)
                    .commit();
        }
    }

    public void hideAds() {

        if (App.getInstance().getAdmob() == ADMOB_DISABLED) {

            mContainerAdmob.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case android.R.id.home: {

                return true;
            }

            default: {

                return super.onOptionsItemSelected(item);
            }
        }
    }


    @Override
    public void setTitle(CharSequence title) {

        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    private void setUpNavHeader(NavigationView navigationView) {
        View itemHeader = navigationView.inflateHeaderView(R.layout.header_navigation_drawer);

        userPhoto = (CircularImageView) itemHeader.findViewById(R.id.userPhoto);
        userCover = (ImageView) itemHeader.findViewById(R.id.userCover);

        userFullname = (TextView) itemHeader.findViewById(R.id.userFullname);
        userUsername = (TextView) itemHeader.findViewById(R.id.userUsername);

        if (imageLoader == null) {

            imageLoader = App.getInstance().getImageLoader();
        }

        userUsername.setText("@" + App.getInstance().getUsername());
        userFullname.setText(App.getInstance().getFullname());

        if (App.getInstance().getCoverUrl() != null && App.getInstance().getCoverUrl().length() > 0) {

            imageLoader.get(App.getInstance().getCoverUrl(), ImageLoader.getImageListener(userCover, R.drawable.profile_default_cover, R.drawable.profile_default_cover));
        }

        if (App.getInstance().getPhotoUrl() != null && App.getInstance().getPhotoUrl().length() > 0) {

            imageLoader.get(App.getInstance().getPhotoUrl(), ImageLoader.getImageListener(userPhoto, R.drawable.profile_default_photo, R.drawable.profile_default_photo));
        }

        if (App.getInstance().getVerify() == 0) {

            userFullname.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        } else {

            userFullname.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.profile_verify_icon, 0);
        }

        if (Build.VERSION.SDK_INT > 15) {

            userCover.setImageAlpha(155);
        }
    }

    private void setUpAdView() {
        mContainerAdmob = (LinearLayout) findViewById(R.id.container_admob);

        if (App.getInstance().getAdmob() == ADMOB_ENABLED) {

            mContainerAdmob.setVisibility(View.VISIBLE);

            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

        if (!restore) {

            displayView(10);
        }
    }

    private boolean logOutUser() {
        if (App.getInstance().isConnected() && App.getInstance().getId() != 0) {

            loading = true;

            showpDialog();

            CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_ACCOUNT_LOGOUT, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {

                                if (!response.getBoolean("error")) {

                                    App.getInstance().removeData();
                                    App.getInstance().readData();

                                    Intent intent = new Intent( MainActivityNew.this, AppActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }

                            } catch (JSONException e) {

                                e.printStackTrace();

                            } finally {

                                loading = false;

                                hidepDialog();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    loading = false;

                    hidepDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("clientId", CLIENT_ID);
                    params.put("accountId", Long.toString(App.getInstance().getId()));
                    params.put("accessToken", App.getInstance().getAccessToken());

                    return params;
                }
            };

            App.getInstance().addToRequestQueue(jsonReq);
        }

        return true;
    }
}
