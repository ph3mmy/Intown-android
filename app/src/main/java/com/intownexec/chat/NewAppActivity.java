package com.intownexec.chat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.intownexec.chat.activity.NewSignupActivity;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by OLUWAPHEMMY on 5/23/2017.
 */

public class NewAppActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "NewAppActivity";
    TextView logoText, subLogoText, signInText;
    Button normalSignInButton, linkedInButton;

    String url = "https://api.linkedin.com/v1/people/~:"
            +"(email-address,formatted-name,phone-numbers,public-profile-url," +
            "industry,positions:(id,title,summary,start-date,end-date," +
            "is-current,company:(id,name,type,size,industry,ticker))," +
            "picture-url,picture-urls::(original))"
            ;

    String newUrl = "https://api.linkedin.com/v1/people-search:(people:(id,first-name,last-name," +
            "headline,picture-url,industry,positions:(id,title,summary,start-date,end-date," +
            "is-current,company:(id,name,type,size,industry,ticker))," +
            "educations:(id,school-name,field-of-study,start-date,end-date,degree,activities,notes))" +
            ",num-results)?first-name=parameter&last-name=parameter";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_new);


        Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/proxima-nova-cond-regular.ttf");
        Typeface tfSym= Typeface.createFromAsset(this.getAssets(), "fonts/proxima-thin-web.ttf");

        logoText = (TextView) findViewById(R.id.tvSignUpLogoText);
        subLogoText = (TextView) findViewById(R.id.tvSignUpLogoSubText);
        signInText = (TextView) findViewById(R.id.tvSignUpNewSignIn);
        normalSignInButton = (Button) findViewById(R.id.signupBtn);
        linkedInButton = (Button) findViewById(R.id.linkedInSignupBtn);

        logoText.setTypeface(tf);
        subLogoText.setTypeface(tfSym);

        signInText.setOnClickListener(this);
        normalSignInButton.setOnClickListener(this);
        linkedInButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSignUpNewSignIn:
                startActivity(new Intent(this, NewLoginActivity.class));
                break;
            case R.id.signupBtn:
                startActivity(new Intent(this, NewSignupActivity.class));
                break;
            case R.id.linkedInSignupBtn:
                loginLinkedIn();

                break;
            default:
                break;
        }
    }

    public void loginLinkedIn(){
            LISessionManager.getInstance(getApplicationContext()).init(this, buildScope(),new AuthListener() {
        @Override
        public void onAuthSuccess() {
            Toast.makeText(getApplicationContext(), "success" +
                    LISessionManager.getInstance( getApplicationContext()).getSession()
                            .getAccessToken().toString(), Toast.LENGTH_LONG).show();
            getUserData();
        }
        @Override
        public void onAuthError(LIAuthError error) {
             Toast.makeText(getApplicationContext(), "failed " + error.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }, true);
    }
   // This method is used to make permissions to retrieve data from linkedin
     private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Add this line to your existing onActivityResult() method
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
    }

    public void getUserData(){
            APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
            apiHelper.getRequest(this, url, new ApiListener() {
                @Override
                public void onApiSuccess(ApiResponse result) {
                    try { setUserProfile(result.getResponseDataAsJson()); }
                    catch (Exception e){  e.printStackTrace(); }
            }
            @Override
                public void onApiError(LIApiError error) {
                    // ((TextView) findViewById(R.id.error)).setText(error.toString());
            }
            });
    }

    public void setUserProfile(JSONObject response) {
        /**{"emailAddress":"afolayanseyi@gmail.com","formattedName":"Jephthah O. Afolayan, CPMP",
                "industry":"Computer Software",
                "pictureUrl":"https:\/\/media.licdn.com\/mpr\/mprx\/0_GlrRkytf5psV0ZadbbkRos_f5xRFMsSWiPHRGenah2RQpEPkIlQR2sUfCogFJZ8WmhQUfy47egE6VOYkHGzFueJ_DgEbVYcewGz4bW2m6yxIre2qFP_nFjGxSHKBPY8MGTlchUx-L9f",
                "pictureUrls":{"_total":1,
                "values":["https:\/\/media.licdn.com\/mpr\/mprx\/0_xTXgrCZ_bw2LOh3Y2gJOAcqhbouWwC8YYiJUhpWivbudVmS4oX4O3cd6cqyowhhROe4Oike6kmhdWPmgwov-FMH51mhWWPyU2ovxtCqLQNl8WXD-2m5YZgV9aq"]},
            "positions":{"_total":0},
            "publicProfileUrl":"https:\/\/www.linkedin.com\/in\/afolayanseyi"}
         **/
        try {
            Log.e(TAG, "setUserProfile: response == "+response.toString());
            String name = response.get("formattedName").toString();
            String email = response.get("emailAddress").toString();
            String profilePicUrl = response.get("pictureUrl").toString();
            Log.e(TAG, "setUserProfile: email: "+ email +" name:" +name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
