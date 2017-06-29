package com.intownexec.chat.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.intownexec.chat.R;

import java.util.ArrayList;

/**
 * Created by Oluwaphemmy on 20-Jun-17.
 */

public class IndustryListDialog extends DialogFragment {
    private static final String TAG = "IndustryListDialog";
    String intOrInd, titleStr;
    int arrayType;
    String[] indArray, intArray;
    ArrayList<String> industList, interestList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle mBundle = getArguments();
        intOrInd = mBundle.getString("industry");
        Log.e(TAG, "onCreate: returned arr type == " + intOrInd);
    }

    //required empty constructor
    public IndustryListDialog() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (intOrInd.equalsIgnoreCase("INDUSTRY")) {
//            arrayType = R.array.industryArray;
            industList = new ArrayList<>();
            indArray = getActivity().getResources().getStringArray(R.array.industryArray);
            titleStr = "Select your Industry of Interest";
        }else {
            interestList = new ArrayList<>();
//            arrayType = R.array.interestArray;
            indArray = getActivity().getResources().getStringArray(R.array.interestArray);
            titleStr = "What are your Interests ?";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(titleStr);
        builder.setMultiChoiceItems(indArray, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (intOrInd.equalsIgnoreCase("INDUSTRY")) {
                    if (isChecked) {
                        industList.add(indArray[which]);
                    } else {
                        industList.remove(indArray[which]);
                    }
                    Log.e(TAG, "onClick: selected industries = " + indArray[which]);
                }else if (intOrInd.equalsIgnoreCase("INTEREST")) {
                    if (isChecked) {
                        interestList.add(indArray[which]);
                    } else {
                        interestList.remove(indArray[which]);
                    }
                    Log.e(TAG, "onClick: selected industries = " + indArray[which]);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (intOrInd.equalsIgnoreCase("INTEREST")) {
                    GetIndustryListListener listener = (GetIndustryListListener) getTargetFragment();
                    listener.onIndustryListSelected(interestList, intOrInd);
                } else if (intOrInd.equalsIgnoreCase("INDUSTRY")) {
                    GetIndustryListListener listener = (GetIndustryListListener) getTargetFragment();
                    listener.onIndustryListSelected(industList, intOrInd);
                }

            }
        });

        Dialog dialog = builder.create();
//        dialog.show();

        return dialog;
    }

    public interface GetIndustryListListener {
        void onIndustryListSelected (ArrayList<String> industryList, String listType);
    }
}
