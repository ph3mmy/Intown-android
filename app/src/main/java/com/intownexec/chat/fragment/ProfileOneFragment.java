package com.intownexec.chat.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.intownexec.chat.R;

/**
 * Created by OLUWAPHEMMY on 5/31/2017.
 */

public class ProfileOneFragment extends Fragment {

    private static final String TAG = "ProfileOneFragment";
    Spinner iamSpinner, toConnectWithSpinner, thatArespinner;
    Button nextProfileButton;
    CheckBox entertainmentChk, meetProChk, networkingChk, businessOppChk;

    String[] iamSpinnerArray = {"Professional", "Mid-Level", "Entrepreneur", "Celebrity", "Executive"};
    String[] toConnectSpinnerArray = {"Heterosexuals", "LGBTQ", "No Preference"};
    String[] thatAreSpinnerArray = {"Men", "Women", "Both"};

    String iamSel, toConnectSel, thatAreSel;
    StringBuilder stringBuilder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_one, container, false);
        iamSpinner = (Spinner) view.findViewById(R.id.spinnerProfileOneIAm);
        toConnectWithSpinner = (Spinner) view.findViewById(R.id.spinnerProfileOneConnectWith);
        thatArespinner = (Spinner) view.findViewById(R.id.spinnerProfileOneThatAre);
        entertainmentChk = (CheckBox) view.findViewById(R.id.chkboxEntertainment);
        meetProChk = (CheckBox) view.findViewById(R.id.chkboxMeetPro);
        networkingChk = (CheckBox) view.findViewById(R.id.chkboxNetworking);
        businessOppChk = (CheckBox) view.findViewById(R.id.chkboxBusinessOpp);
        nextProfileButton = (Button) view.findViewById(R.id.btProfileSetupOne);




        ArrayAdapter<String> iamSpAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, iamSpinnerArray);
        iamSpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        iamSpinner.setAdapter(iamSpAdapter);
        iamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
//                ((TextView) parent.getChildAt(0)).setTextSize(5);
                iamSel = iamSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> toConSpAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, toConnectSpinnerArray);
        toConSpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toConnectWithSpinner.setAdapter(toConSpAdapter);
        toConnectWithSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toConnectSel = toConnectWithSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> thatAreSpAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, thatAreSpinnerArray);
        thatAreSpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        thatArespinner.setAdapter(thatAreSpAdapter);
        thatArespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                thatAreSel = thatArespinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nextProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringBuilder = new StringBuilder();
                if (entertainmentChk.isChecked()) {
                    stringBuilder.append("Entertainment:");
                }
                if (meetProChk.isChecked()) {
                    stringBuilder.append("Meet Professionals:");
                }
                if (networkingChk.isChecked()) {
                    stringBuilder.append("Networking:");
                }
                if (businessOppChk.isChecked()) {
                    stringBuilder.append("Business Opportunities");
                }

                if (TextUtils.isEmpty(stringBuilder.toString())) {
                    Toast.makeText(getActivity(), "Please select one or more checkboxes for why you are here", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "onClick: StringBuilder checkedText = " + stringBuilder.toString());
//                    stringBuilder.delete(0, );
                }

            }
        });

        return view;
    }
}
