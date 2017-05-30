package com.intownexec.chat.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.intownexec.chat.MainActivity;
import com.intownexec.chat.R;
import com.intownexec.chat.WebViewActivity;
import com.intownexec.chat.app.App;
import com.intownexec.chat.constants.Constants;
import com.intownexec.chat.util.CustomRequest;
import com.intownexec.chat.util.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by OLUWAPHEMMY on 5/28/2017.
 */

public class NewSignupFragment extends Fragment implements Constants, View.OnClickListener {

    private static final String TAG = "NewSignupFragment";
    TextInputEditText username, email, password, fullname;
    String usernameStr, emailStr, passwordStr, fullnameStr, language;
    private ProgressDialog pDialog;
    Button signupButton;
    Spinner genderSpinner, bDaySpinner, bMonthSpinner, bYearSpinner;
    CheckBox termsCheckBox;
    ImageView backButton;
    TextView tvTerms;
    List<Integer> daySpinnerList, yearSpinnerList;
    List<String> monthSpinnerList, genderSpinnerList;
    String bDaySel, bMonthSel, bYearSel, genderSel;
    String[] monthArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    String[] genderArray = {"Male", "Female"};

    private Boolean restore = false;
    private Boolean loading = false;

    public NewSignupFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        initpDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if (loading) {

            showpDialog();
        }

        View view = inflater.inflate(R.layout.fragment_new_signup, container, false);
        username = (TextInputEditText) view.findViewById(R.id.etSignupUsername);
        email = (TextInputEditText) view.findViewById(R.id.etSignupEmail);
        fullname = (TextInputEditText) view.findViewById(R.id.etSignupFullname);
        password = (TextInputEditText) view.findViewById(R.id.password);
        genderSpinner = (Spinner) view.findViewById(R.id.spinnerGender);
        bDaySpinner = (Spinner) view.findViewById(R.id.spinnerSignupDay);
        bMonthSpinner = (Spinner) view.findViewById(R.id.spinnerSignupMonth);
        bYearSpinner = (Spinner) view.findViewById(R.id.spinnerSignupYear);
        termsCheckBox = (CheckBox) view.findViewById(R.id.checkboxTerms);
        signupButton = (Button) view.findViewById(R.id.signupFormBtn);
        backButton = (ImageView) view.findViewById(R.id.ivRegisterBack);
        tvTerms = (TextView) view.findViewById(R.id.tvCheckboxTerms);

        daySpinnerList = new ArrayList<>();
        monthSpinnerList = new ArrayList<>();
        yearSpinnerList = new ArrayList<>();
        genderSpinnerList = new ArrayList<>();

        //populate day spinner
        for (int i = 1; i <= 31; i++) {
            daySpinnerList.add(i);
        }
        ArrayAdapter<Integer> daySpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, daySpinnerList);
        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bDaySpinner.setAdapter(daySpinnerAdapter);

        bDaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bDaySel = bDaySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //populate month spinner

        ArrayAdapter<String> monthSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, monthArray);
        monthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bMonthSpinner.setAdapter(monthSpinnerAdapter);

        bMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bMonthSel = bMonthSpinner.getSelectedItem().toString();
                Log.e(TAG, "onItemSelected: selected month == " + bMonthSel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //populate gender spinner
        ArrayAdapter<String> genderSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, genderArray);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderSpinnerAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderSel = genderSpinner.getSelectedItem().toString();
                Log.e(TAG, "onItemSelected: selected month == " + genderSel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //populate year spinner
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        Log.e(TAG, "onCreateView: returned year == " + year);
        for (int i = 1917; i <= year; i++) {
            yearSpinnerList.add(i);
        }
        ArrayAdapter<Integer> yearSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, yearSpinnerList);
        yearSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bYearSpinner.setAdapter(yearSpinnerAdapter);

        bYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bYearSel = bDaySpinner.getSelectedItem().toString();
                Log.e(TAG, "onItemSelected: selected bDay year == " + bYearSel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        username.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                if (App.getInstance().isConnected() && checkUsername()) {

//                        showpDialog();

                    CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_APP_CHECKUSERNAME, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {

                                        if (response.getBoolean("error")) {

                                            username.setError(getString(R.string.error_login_taken));
                                        }

                                    } catch (JSONException e) {

                                        e.printStackTrace();

                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

//                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("username", usernameStr);

                            return params;
                        }
                    };

                    App.getInstance().addToRequestQueue(jsonReq);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        fullname.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                checkFullname();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        password.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                checkPassword();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        email.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                checkEmail();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        signupButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        tvTerms.setOnClickListener(this);

        return view;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivRegisterBack:
                getActivity().finish();
                break;
            case R.id.signupFormBtn:
                check_signup();
                break;
            case R.id.tvCheckboxTerms:

                Intent i = new Intent(getActivity(), WebViewActivity.class);
                i.putExtra("url", METHOD_APP_TERMS);
                i.putExtra("title", getText(R.string.signup_label_terms_and_policies));
                startActivity(i);
                break;
            default:
                break;
        }
    }





    public Boolean checkUsername() {

        usernameStr = username.getText().toString();

        Helper helper = new Helper();

        if (usernameStr.length() == 0) {

            username.setError(getString(R.string.error_field_empty));

            return false;
        }

        if (usernameStr.length() < 5) {

            username.setError(getString(R.string.error_small_username));

            return false;
        }

        if (!helper.isValidLogin(usernameStr)) {

            username.setError(getString(R.string.error_wrong_format));

            return false;
        }

        username.setError(null);

        return  true;
    }

    public Boolean checkFullname() {

        fullnameStr = fullname.getText().toString();

        if (fullnameStr.length() == 0) {

            fullname.setError(getString(R.string.error_field_empty));

            return false;
        }

        if (fullnameStr.length() < 2) {

            fullname.setError(getString(R.string.error_small_fullname));

            return false;
        }

        fullname.setError(null);

        return  true;
    }

    public Boolean checkPassword() {

        passwordStr = password.getText().toString();

        Helper helper = new Helper();

        if (passwordStr.length() == 0) {

            password.setError(getString(R.string.error_field_empty));

            return false;
        }

        if (passwordStr.length() < 6) {

            password.setError(getString(R.string.error_small_password));

            return false;
        }

        if (!helper.isValidPassword(passwordStr)) {

            password.setError(getString(R.string.error_wrong_format));

            return false;
        }

        password.setError(null);

        return true;
    }

    public Boolean checkEmail() {

        emailStr = email.getText().toString();

        Helper helper = new Helper();

        if (emailStr.length() == 0) {

            email.setError(getString(R.string.error_field_empty));

            return false;
        }

        if (!helper.isValidEmail(emailStr)) {

            email.setError(getString(R.string.error_wrong_format));

            return false;
        }

        email.setError(null);

        return true;
    }

    public Boolean verifyRegForm() {

        username.setError(null);
        fullname.setError(null);
        password.setError(null);
        email.setError(null);

        Helper helper = new Helper();

        if (usernameStr.length() == 0) {

            username.setError(getString(R.string.error_field_empty));

            return false;
        }

        if (usernameStr.length() < 5) {

            username.setError(getString(R.string.error_small_username));

            return false;
        }

        if (!helper.isValidLogin(usernameStr)) {

            username.setError(getString(R.string.error_wrong_format));

            return false;
        }

        if (fullnameStr.length() == 0) {

            fullname.setError(getString(R.string.error_field_empty));

            return false;
        }

        if (fullnameStr.length() < 2) {

            fullname.setError(getString(R.string.error_small_fullname));

            return false;
        }

        if (passwordStr.length() == 0) {

            password.setError(getString(R.string.error_field_empty));

            return false;
        }

        if (passwordStr.length() < 6) {

            password.setError(getString(R.string.error_small_password));

            return false;
        }

        if (!helper.isValidPassword(passwordStr)) {

            password.setError(getString(R.string.error_wrong_format));

            return false;
        }

        if (emailStr.length() == 0) {

            email.setError(getString(R.string.error_field_empty));

            return false;
        }

        if (!helper.isValidEmail(emailStr)) {

            email.setError(getString(R.string.error_wrong_format));

            return false;
        }

        return true;
    }



    public void check_signup() {

        usernameStr = username.getText().toString();
        fullnameStr = fullname.getText().toString();
         passwordStr = password.getText().toString();
         emailStr = email.getText().toString();
         language = Locale.getDefault().getLanguage();

        if (verifyRegForm()) {

                loading = true;

                showpDialog();

                signup();
            }
    }

    public void signup() {

        if (App.getInstance().isConnected()) {

            loading = true;

            showpDialog();

            CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_ACCOUNT_SIGNUP, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.e("Profile", "Malformed JSON: \"" + response.toString() + "\"");

                            if (App.getInstance().authorize(response)) {

                                Log.e("Profile", "Malformed JSON: inside authorize == " + response.toString() + "\"");

                                App.getInstance().updateGeoLocation();

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            } else {

                                switch (App.getInstance().getErrorCode()) {

                                    case 300 : {

                                        username.setError(getString(R.string.error_login_taken));
                                        break;
                                    }

                                    case 301 : {

                                        email.setError(getString(R.string.error_email_taken));
                                        break;
                                    }

                                    default: {

                                        Log.e("Profile", "Could not parse malformed JSON: \"" + response.toString() + "\"");
                                        break;
                                    }
                                }
                            }

                            loading = false;

                            hidepDialog();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getActivity(), getText(R.string.error_data_loading), Toast.LENGTH_LONG).show();

                    loading = false;

                    hidepDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    String photo = "";
                    String facebookId = "";
                    params.put("username", usernameStr);
                    params.put("fullname", fullnameStr);
                    params.put("password", passwordStr);
                    params.put("photo", photo);
                    params.put("email", emailStr);
                    params.put("language", language);
                    params.put("facebookId", facebookId);
                    params.put("sex", genderSel);
                    params.put("year", bYearSel);
                    params.put("month", bMonthSel);
                    params.put("day", bDaySel);
                    params.put("clientId", CLIENT_ID);
                    params.put("gcm_regId", App.getInstance().getGcmToken());

                    return params;
                }
            };

            App.getInstance().addToRequestQueue(jsonReq);

        } else {

            Toast.makeText(getActivity(), R.string.msg_network_error, Toast.LENGTH_SHORT).show();
        }
    }


}
