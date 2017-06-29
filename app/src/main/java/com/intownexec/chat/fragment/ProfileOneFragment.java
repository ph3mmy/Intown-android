package com.intownexec.chat.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.intownexec.chat.dialogs.IndustryListDialog;
import com.intownexec.chat.util.Helper;

import java.util.ArrayList;

/**
 * Created by OLUWAPHEMMY on 5/31/2017.
 */

public class ProfileOneFragment extends Fragment implements View.OnClickListener, IndustryListDialog.GetIndustryListListener{

    private static final String TAG = "ProfileOneFragment";
    Spinner iamSpinner, toConnectWithSpinner, thatArespinner;
    Button nextProfileButton;
    CheckBox entertainmentChk, meetProChk, networkingChk, businessOppChk;

    Button nextButton, selectIndButton, selectIntButton;
    ArrayList<String> industList, interestList;
    String hereForStr, iamSelStr, toConStr, thatAreStr;
    ArrayList<String> hereList;

    String[] iamSpinnerArray = {"Professional", "Mid-Level", "Entrepreneur", "Celebrity", "Executive"};
    String[] toConnectSpinnerArray = {"Heterosexuals", "LGBTQ", "No Preference"};
    String[] thatAreSpinnerArray = {"Men", "Women", "Both"};

    String iamSel, toConnectSel, thatAreSel;
    ArrayList<String> hereForList;


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

        selectIndButton = (Button) view.findViewById(R.id.btFromThisInd);
        selectIntButton = (Button) view.findViewById(R.id.btProSelInt);
        nextButton = (Button) view.findViewById(R.id.btProfileSetupTwo);

        selectIndButton.setOnClickListener(this);
        selectIntButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        hereForList = new ArrayList<>();

//        iamSpinner.getBackground().setColorFilter(getResources().getColor(R.color.sim_grey), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> iamSpAdapter = Helper.generateAdapter(getActivity(),iamSpinnerArray);
        iamSpAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
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
//        iamSpinner.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.sim_grey), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> toConSpAdapter = Helper.generateAdapter(getActivity(),toConnectSpinnerArray);
        toConSpAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
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


        ArrayAdapter<String> thatAreSpAdapter = Helper.generateAdapter(getActivity(),thatAreSpinnerArray);
        thatAreSpAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
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

        return view;
    }

    @Override
    public void onClick(View v) {

        Bundle mArgs = new Bundle();
        IndustryListDialog dialog = new IndustryListDialog();
        dialog.setTargetFragment(ProfileOneFragment.this, 0);
        switch (v.getId()) {

            case R.id.btFromThisInd:
                mArgs.putString("industry", "INDUSTRY");
                dialog.setArguments(mArgs);
                dialog.show(getFragmentManager(), "industry_list_dialog");

                break;
            case R.id.btProSelInt:
                mArgs.putString("industry", "INTEREST");
                dialog.setArguments(mArgs);
                dialog.show(getFragmentManager(), "industry_list_dialog");

                break;
            case R.id.btProfileSetupTwo:

            hereForList.clear();
            if (entertainmentChk.isChecked()) {
                hereForList.add(entertainmentChk.getText().toString());
            }
            if (meetProChk.isChecked()) {
                hereForList.add(meetProChk.getText().toString());
            }
            if (networkingChk.isChecked()) {
                hereForList.add(networkingChk.getText().toString());
            }
            if (businessOppChk.isChecked()) {
                hereForList.add(businessOppChk.getText().toString());
            }

            if (hereForList.size() == 0) {
                Toast.makeText(getActivity(), "Please select one or more checkboxes for why you are here", Toast.LENGTH_SHORT).show();
            }

            else if (interestList ==null || interestList.size() == 0) {
                Toast.makeText(getActivity(), "Please select one or more Interest", Toast.LENGTH_SHORT).show();
            }

            else if (industList ==null ||industList.size() == 0) {
                Toast.makeText(getActivity(), "Please select one or more Industry of Interest", Toast.LENGTH_SHORT).show();
            } else {

                ProfileThreeFragment fragment = new ProfileThreeFragment();
                Bundle args = new Bundle();
                args.putString("iamSel", iamSel);
                args.putString("toCon", toConnectSel);
                args.putString("thatAre", thatAreSel);
                args.putStringArrayList("hereList", hereForList);
                args.putStringArrayList("indList", industList);
                args.putStringArrayList("intList", interestList);
                fragment.setArguments(args);

                FragmentManager manager = getFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.profileSetUpFrame, fragment);
                ft.commit();
            }
            break;
            default:
                break;

        }

    }

    @Override
    public void onIndustryListSelected(ArrayList<String> industryList, String listType) {
        if (listType.equalsIgnoreCase("INDUSTRY")) {
            this.industList = industryList;
        }
        else if (listType.equalsIgnoreCase("INTEREST")) {
            this.interestList = industryList;
        }
    }
}
