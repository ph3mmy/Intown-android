package com.intownexec.chat.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.intownexec.chat.MainActivity;
import com.intownexec.chat.R;
import com.intownexec.chat.app.App;
import com.intownexec.chat.constants.Constants;
import com.intownexec.chat.util.CustomRequest;
import com.intownexec.chat.util.Helper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by OLUWAPHEMMY on 5/23/2017.
 */

public class NewLoginFragment extends Fragment implements Constants, View.OnClickListener {

    private static final String TAG = "NewLoginFragment";
    View view;
    TextInputEditText emailEditText, passwordEditText;
    CheckBox rememberMe;
    TextView forgotPass, signInTV;
    Button btnLogin;
    ImageView backArrowImg;
    private ProgressDialog pDialog;
    private Boolean loading = false;
    private String username, email, password;


    public NewLoginFragment() {
        //required empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initpDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        view = inflater.inflate(R.layout.fragment_login_new, container, false);


        if (loading) {

            showpDialog();
        }

        //initialize views
        emailEditText = (TextInputEditText) view.findViewById(R.id.etLoginEmail);
        passwordEditText = (TextInputEditText) view.findViewById(R.id.password);
        rememberMe = (CheckBox) view.findViewById(R.id.chkboxRememberMe);
        forgotPass = (TextView) view.findViewById(R.id.forgotPassword);
        btnLogin = (Button) view.findViewById(R.id.signinBtn);
        backArrowImg = (ImageView) view.findViewById(R.id.ivLoginBack);
        signInTV = (TextView) view.findViewById(R.id.tvSignIn);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/proxima-nova-cond-regular.ttf");
        signInTV.setTypeface(tf);

        forgotPass.setOnClickListener(this);
        backArrowImg.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set and save checked state to a prefUtils

            }
        });
        
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgotPassword:

                break;
            case R.id.ivLoginBack:
                getActivity().finish();
                break;
            case R.id.signinBtn:
                //validate input
                username = emailEditText.getText().toString();
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();

                if (!App.getInstance().isConnected()) {

                    Toast.makeText(getActivity(), R.string.msg_network_error, Toast.LENGTH_SHORT).show();

                } else if (!checkUsername() || !checkPassword()) {


                } else {

                    signin();
                }

        }
    }


    public void onDestroyView() {

        super.onDestroyView();

        hidepDialog();
    }

    protected void initpDialog() {

        pDialog = new ProgressDialog(getActivity());
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

    public void signin() {

        loading = true;

        showpDialog();

        CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_ACCOUNT_LOGIN, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e(TAG, "onResponse: LOGIN response = " + response.toString());

                        if (App.getInstance().authorize(response)) {

                            if (App.getInstance().getState() == ACCOUNT_STATE_ENABLED) {

                                App.getInstance().updateGeoLocation();

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            } else {

                                if (App.getInstance().getState() == ACCOUNT_STATE_BLOCKED) {

                                    App.getInstance().logout();
                                    Toast.makeText(getActivity(), getText(R.string.msg_account_blocked), Toast.LENGTH_SHORT).show();

                                } else {

                                    App.getInstance().updateGeoLocation();

                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }

                        } else {

                            Toast.makeText(getActivity(), getString(R.string.error_signin), Toast.LENGTH_SHORT).show();
                        }

                        loading = false;

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: LOGIN = " + error);
                Log.e(TAG, "onErrorResponse: LOGIN = " + error.getCause());
                Log.e(TAG, "onErrorResponse: LOGIN = " + METHOD_ACCOUNT_LOGIN);

                Toast.makeText(getActivity(), getText(R.string.error_data_loading), Toast.LENGTH_LONG).show();

                loading = false;

                hidepDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                params.put("clientId", CLIENT_ID);

                return params;
            }
        };

        App.getInstance().addToRequestQueue(jsonReq);
    }

    public Boolean checkUsername() {

        username = emailEditText.getText().toString();

        emailEditText.setError(null);

        Helper helper = new Helper();

        if (username.length() == 0) {

            emailEditText.setError(getString(R.string.error_field_empty));

            return false;
        }

        if (username.length() < 5) {

            emailEditText.setError(getString(R.string.error_small_username));

            return false;
        }

        if (!helper.isValidLogin(username) && !helper.isValidEmail(username)) {

            emailEditText.setError(getString(R.string.error_wrong_format));

            return false;
        }

        return  true;
    }

    public Boolean checkPassword() {

        password = passwordEditText.getText().toString();

        passwordEditText.setError(null);

        Helper helper = new Helper();

        if (password.length() == 0) {

            passwordEditText.setError(getString(R.string.error_field_empty));

            return false;
        }

        if (password.length() < 6) {

            passwordEditText.setError(getString(R.string.error_small_password));

            return false;
        }

        if (!helper.isValidPassword(password)) {

            passwordEditText.setError(getString(R.string.error_wrong_format));

            return false;
        }

        return  true;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
