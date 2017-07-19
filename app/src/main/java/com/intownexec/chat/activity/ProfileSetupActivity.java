package com.intownexec.chat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.intownexec.chat.R;
import com.intownexec.chat.app.App;
import com.intownexec.chat.constants.Constants;
import com.intownexec.chat.dialogs.PostImageChooseDialog;
import com.intownexec.chat.fragment.ProfileOneFragment;
import com.intownexec.chat.fragment.ProfileThreeFragment;
import com.intownexec.chat.util.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by OLUWAPHEMMY on 5/29/2017.
 */

public class ProfileSetupActivity extends AppCompatActivity implements Constants, View.OnClickListener {

    private static final String TAG = "ProfileSetupActivity";
    Fragment mFragment;

    private Boolean loading = false;

    private ProgressDialog pDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Intownexec - Profile Set up");
        setSupportActionBar(toolbar);

        if (loading) {

            showpDialog();
        }

        ProfileOneFragment fragment = new ProfileOneFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.profileSetUpFrame, fragment);
        ft.commit();

        mFragment = new ProfileThreeFragment();

    }


    protected void initpDialog() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.msg_loading));
        pDialog.setCancelable(false);
    }

    protected void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }


    public void saveSettings() {

        loading = true;

        showpDialog();

        CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_ACCOUNT_SAVE_SETTINGS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if (response.has("error")) {

                                if (!response.getBoolean("error")) {

                                    fullname = response.getString("fullname");
                                    location = response.getString("location");
                                    facebookPage = response.getString("fb_page");
                                    instagramPage = response.getString("instagram_page");
                                    bio = response.getString("status");

                                    Toast.makeText(ProfileSetupActivity.this, getText(R.string.msg_settings_saved), Toast.LENGTH_SHORT).show();

                                    App.getInstance().setFullname(fullname);

                                    Intent i = new Intent();
                                    i.putExtra("fullname", fullname);
                                    i.putExtra("location", location);
                                    i.putExtra("facebookPage", facebookPage);
                                    i.putExtra("instagramPage", instagramPage);
                                    i.putExtra("bio", bio);

                                    i.putExtra("sex", sex);

                                    i.putExtra("year", year);
                                    i.putExtra("month", month);
                                    i.putExtra("day", day);

                                    i.putExtra("relationshipStatus", relationshipStatus);
                                    i.putExtra("politicalViews", politicalViews);
                                    i.putExtra("worldView", worldView);
                                    i.putExtra("personalPriority", personalPriority);
                                    i.putExtra("importantInOthers", importantInOthers);
                                    i.putExtra("viewsOnSmoking", viewsOnSmoking);
                                    i.putExtra("viewsOnAlcohol", viewsOnAlcohol);
                                    i.putExtra("youLooking", youLooking);
                                    i.putExtra("youLike", youLike);
                                    i.putExtra("allowShowMyBirthday", allowShowMyBirthday);

                                    setResult(RESULT_OK, i);

                                    finish();
                                }
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
                params.put("accountId", Long.toString(App.getInstance().getId()));
                params.put("accessToken", App.getInstance().getAccessToken());
                params.put("fullname", fullname);
                params.put("location", location);
                params.put("facebookPage", facebookPage);
                params.put("instagramPage", instagramPage);
                params.put("bio", bio);
                params.put("sex", Integer.toString(sex));
                params.put("year", Integer.toString(year));
                params.put("month", Integer.toString(month));
                params.put("day", Integer.toString(day));

                params.put("iStatus", Integer.toString(relationshipStatus));
                params.put("politicalViews", Integer.toString(politicalViews));
                params.put("worldViews", Integer.toString(worldView));
                params.put("personalPriority", Integer.toString(personalPriority));
                params.put("importantInOthers", Integer.toString(importantInOthers));
                params.put("smokingViews", Integer.toString(viewsOnSmoking));
                params.put("alcoholViews", Integer.toString(viewsOnAlcohol));
                params.put("lookingViews", Integer.toString(youLooking));
                params.put("interestedViews", Integer.toString(youLike));

                params.put("allowShowMyBirthday", Integer.toString(allowShowMyBirthday));

                return params;
            }
        };

        App.getInstance().addToRequestQueue(jsonReq);
    }

    @Override
    public void onClick(View v) {

    }
}
