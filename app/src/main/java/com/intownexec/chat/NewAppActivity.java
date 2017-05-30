package com.intownexec.chat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.intownexec.chat.activity.NewSignupActivity;

/**
 * Created by OLUWAPHEMMY on 5/23/2017.
 */

public class NewAppActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "NewAppActivity";
    TextView logoText, subLogoText, signInText;
    Button normalSignInButton;

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

        logoText.setTypeface(tf);
        subLogoText.setTypeface(tfSym);

        signInText.setOnClickListener(this);
        normalSignInButton.setOnClickListener(this);

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
            default:
                break;
        }
    }
}
