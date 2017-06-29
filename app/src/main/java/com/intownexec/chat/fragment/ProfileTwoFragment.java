package com.intownexec.chat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.intownexec.chat.R;
import com.intownexec.chat.dialogs.IndustryListDialog;

import java.util.ArrayList;

/**
 * Created by OLUWAPHEMMY on 6/2/2017.
 */

public class ProfileTwoFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ProfileTwoFragment";
    Button nextButton, selectIndButton, selectIntButton;
    ArrayList<String> industList, interestList;
    String hereForStr, iamSelStr, toConStr, thatAreStr;
    ArrayList<String> hereList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle profileOneBundle = getArguments();
//        hereForStr = profileOneBundle.getString("hereFor");
        iamSelStr = profileOneBundle.getString("iamSel");
        toConStr = profileOneBundle.getString("toCon");
        thatAreStr = profileOneBundle.getString("thatAre");
        hereList = profileOneBundle.getStringArrayList("hereList");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_two, container, false);

        selectIndButton = (Button) view.findViewById(R.id.btFromThisInd);
        selectIntButton = (Button) view.findViewById(R.id.btProSelInt);
        nextButton = (Button) view.findViewById(R.id.btProfileSetupTwo);

        Log.e(TAG, "onCreateView: passed strings == " + iamSelStr + hereForStr);

        selectIndButton.setOnClickListener(this);
        selectIntButton.setOnClickListener(this);

        nextButton.setOnClickListener(this); /*{


                if (interestList.size() == 0 || industList.size() == 0) {
                    Toast.makeText(getActivity(), "Please select one or more Interests & Industries", Toast.LENGTH_SHORT).show();
                } else {

                    ProfileThreeFragment fragment = new ProfileThreeFragment();
                    Bundle args = new Bundle();
                    args.putStringArrayList("hereForList",hereList);
                    args.putStringArrayList("interestList", interestList);
                    args.putStringArrayList("indList",industList);
                    args.putString("iamSel", iamSelStr);
                    args.putString("toCon", toConStr);
                    args.putString("thatAre", thatAreStr);
                    fragment.setArguments(args);

                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction ft = manager.beginTransaction();
                    ft.replace(R.id.profileSetUpFrame, fragment);
                    ft.commit();

                }


            }
        });*/


        return view;
    }

    @Override
    public void onClick(View v) {
        Bundle mArgs = new Bundle();
        IndustryListDialog dialog = new IndustryListDialog();
        switch (v.getId()) {

            case R.id.btFromThisInd:
                mArgs.putString("industry", "INDUSTRY");
                dialog.setArguments(mArgs);
                dialog.setTargetFragment(this, 0);
                dialog.show(getFragmentManager(), "industry_list_dialog");

                break;
            case R.id.btProSelInt:
                mArgs.putString("industry", "INTEREST");
                dialog.setArguments(mArgs);
                dialog.setTargetFragment(this, 0);
                dialog.show(getFragmentManager(), "industry_list_dialog");

                break;
            case R.id.btProfileSetupTwo:

                ProfileThreeFragment fragment = new ProfileThreeFragment();
//                Bundle args = new Bundle();
//                fragment.setArguments(args);

                FragmentManager manager = getFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.profileSetUpFrame, fragment);
                ft.commit();

                break;
            default:
                break;
        }
    }
}
