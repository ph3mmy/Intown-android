package com.intownexec.chat.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.intownexec.chat.R;
import com.intownexec.chat.app.App;
import com.intownexec.chat.constants.Constants;
import com.intownexec.chat.dialogs.PostImageChooseDialog;
import com.intownexec.chat.util.CustomRequest;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by OLUWAPHEMMY on 6/2/2017.
 */

public class ProfileThreeFragment extends Fragment implements Constants, View.OnClickListener{

    private static final String TAG = "ProfileThreeFragment";
    ImageView addFirstImg, addSecondImg, addThirdImage, delFirstImg, delSecondImg, delThirdImg, prevFirstImg, prevSecImg, preThirdImg;
    EditText aboutMeTxt;
    Button saveProfileButton;
    ArrayList<String> hereList, interestList, industList;
    String iamSelStr, toConStr, thatAreStr;

    private int postMode = 0;
    private int itemType = 0;

    private Boolean loading = false;

    public static final int RESULT_OK = -1;

    public static final int REQUEST_TAKE_GALLERY_VIDEO = 1001;

    private ProgressDialog pDialog;

    Dialog dialog;

    ImageView mChoicePostImg;

    String commentText = "", imgUrl = "", videoUrl = "", originImgUrl = "", previewImgUrl = "",  postArea = "", postCountry = "", postCity = "", postLat = "", postLng = "";
    private String selectedPostImg = "";
    private Uri selectedImage;
    private Uri outputFileUri;
    RelativeLayout prevRemLayout;

    private String selectedImagePath;


    //required empty constructor
    public ProfileThreeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle profileOneBundle = getArguments();
        iamSelStr = profileOneBundle.getString("iamSel");
        toConStr = profileOneBundle.getString("toCon");
        thatAreStr = profileOneBundle.getString("thatAre");
        hereList = profileOneBundle.getStringArrayList("hereList");
        interestList = profileOneBundle.getStringArrayList("intList");
        industList = profileOneBundle.getStringArrayList("indList");

        initpDialog();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_three, container, false);
        addFirstImg = (ImageView) view.findViewById(R.id.addFirstProImageView);
        delFirstImg = (ImageView) view.findViewById(R.id.addFirstProImageRemove);
        prevFirstImg = (ImageView) view.findViewById(R.id.addFirstProImagePreview);
//        addSecondImg = (ImageView) view.findViewById(R.id.addSecProImageView);
//        delSecondImg = (ImageView) view.findViewById(R.id.addSecProRemove);
//        prevSecImg = (ImageView) view.findViewById(R.id.addSecProImgPreview);
        aboutMeTxt = (EditText) view.findViewById(R.id.etProThreeAboutMe);
        saveProfileButton = (Button) view.findViewById(R.id.btSaveProThree);
        prevRemLayout = (RelativeLayout) view.findViewById(R.id.prevAndRemLayout);


        dialog = new Dialog(getActivity());

        saveProfileButton.setOnClickListener(this);
        addFirstImg.setOnClickListener(this);
//        addSecondImg.setOnClickListener(this);
        delFirstImg.setOnClickListener(this);
//        delSecondImg.setOnClickListener(this);

        Log.e(TAG, "onCreateView: all passed items == " + industList + " \ninterest == " + interestList
        + " \nthat are == " + thatAreStr + " \nhereforList == " + hereList + " \nto constr == " + toConStr + " \ni am == " + iamSelStr);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.addFirstProImageView:
                Log.e(TAG, "onClick: selec at first button click= " +selectedPostImg );

                if (selectedPostImg.length() == 0) {

                    if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_PHOTO);

                        } else {

                            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_PHOTO);
                        }

                    } else {

//                        choiceImage();
                        pickImage();
                    }

                } else {
                    if (selectedPostImg != null && selectedPostImg.length() > 0) {
                        prevFirstImg.setImageURI(Uri.fromFile(new File(selectedPostImg)));
                        prevRemLayout.setVisibility(View.VISIBLE);
                        addFirstImg.setVisibility(View.GONE);
                    }
                }


                break;/*
            case R.id.addSecProImageView:

                break;*/
            case R.id.addFirstProImageRemove:
                selectedPostImg="";
                selectedImage=null;
                selectedImagePath="";
                prevRemLayout.setVisibility(View.GONE);
                addFirstImg.setVisibility(View.VISIBLE);
                itemType = GALLERY_ITEM_TYPE_IMAGE;

                break;/*
            case R.id.addSecProRemove:

                break;*/
            case R.id.btSaveProThree:

                //first check if device is connected
                if (App.getInstance().isConnected()) {

                    /*commentText = mCommentEdit.getText().toString();
                    commentText = commentText.trim();*/

                    if (selectedPostImg != null && selectedPostImg.length() > 0) {

                        loading = true;

                        showpDialog();

                            File f = new File(Environment.getExternalStorageDirectory() + File.separator + APP_TEMP_FOLDER, "photo.jpg");

                            uploadFile(METHOD_PHOTOS_UPLOAD_IMG, f);

                    } else {

                        Toast toast= Toast.makeText(getActivity(), getText(R.string.msg_enter_photo), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                } else {

                    Toast toast= Toast.makeText(getActivity(), getText(R.string.msg_network_error), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                break;
            case R.id.tvPickCamera:
                imageFromCamera();
                dialog.dismiss();
                break;
            case R.id.tvPickGallery:
                imageFromGallery();
                dialog.dismiss();
                break;
            default:
                break;
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
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_PHOTO: {

                // If request is cancelled, the result arrays are empty.

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//                    choiceImage();
                    pickImage();

                } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        showNoStoragePermissionSnackbar();
                    }
                }

                return;
            }
        }
    }

    public void showNoStoragePermissionSnackbar() {

        Snackbar.make(getView(), getString(R.string.label_no_storage_permission) , Snackbar.LENGTH_LONG).setAction(getString(R.string.action_settings), new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                openApplicationSettings();

                Toast.makeText(getActivity(), getString(R.string.label_grant_storage_permission), Toast.LENGTH_SHORT).show();
            }

        }).show();
    }

    public void showNoLocationPermissionSnackbar() {

        Snackbar.make(getView(), getString(R.string.label_no_location_permission) , Snackbar.LENGTH_LONG).setAction(getString(R.string.action_settings), new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                openApplicationSettings();

                Toast.makeText(getActivity(), getString(R.string.label_grant_location_permission), Toast.LENGTH_SHORT).show();
            }

        }).show();
    }

    public void openApplicationSettings() {

        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getActivity().getPackageName()));
        startActivityForResult(appSettingsIntent, 10001);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

       if (requestCode == SELECT_POST_IMG && resultCode == RESULT_OK && null != data) {

            selectedImage = data.getData();
           Log.e(TAG, "onActivityResult: data == " + data);

            selectedPostImg = getImageUrlWithAuthority(getActivity(), selectedImage, "photo.jpg");

            try {

                save(selectedPostImg, "photo.jpg");

                selectedPostImg = Environment.getExternalStorageDirectory() + File.separator + APP_TEMP_FOLDER + File.separator + "photo.jpg";

                prevFirstImg.setImageURI(Uri.fromFile(new File(selectedPostImg)));
                prevRemLayout.setVisibility(View.VISIBLE);
                addFirstImg.setVisibility(View.GONE);
//                mChoicePostImg.setImageURI(Uri.fromFile(new File(selectedPostImg)));

                itemType = GALLERY_ITEM_TYPE_IMAGE;

            } catch (Exception e) {

                Log.e("OnSelectPostImage", e.getMessage());
            }

        } else if (requestCode == CREATE_POST_IMG && resultCode == getActivity().RESULT_OK) {

            try {

                selectedPostImg = Environment.getExternalStorageDirectory() + File.separator + APP_TEMP_FOLDER + File.separator + "photo.jpg";

                save(selectedPostImg, "photo.jpg");

                prevFirstImg.setImageURI(Uri.fromFile(new File(selectedPostImg)));
                prevRemLayout.setVisibility(View.VISIBLE);
                addFirstImg.setVisibility(View.GONE);
//                mChoicePostImg.setImageURI(Uri.fromFile(new File(selectedPostImg)));

                itemType = GALLERY_ITEM_TYPE_IMAGE;

            } catch (Exception ex) {

                Log.v("OnCameraCallBack", ex.getMessage());
            }

        }
    }

    public String getRealPathFromURI(Uri contentUri) {

        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);

        if (cursor.moveToFirst()) {

            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }

        cursor.close();

        return res;
    }

    public static String getImageUrlWithAuthority(Context context, Uri uri, String fileName) {

        InputStream is = null;

        if (uri.getAuthority() != null) {

            try {

                is = context.getContentResolver().openInputStream(uri);
                Bitmap bmp = BitmapFactory.decodeStream(is);

                return writeToTempImageAndGetPathUri(context, bmp, fileName).toString();

            } catch (FileNotFoundException e) {

                e.printStackTrace();

            } finally {

                try {

                    if (is != null) {

                        is.close();
                    }

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static String writeToTempImageAndGetPathUri(Context inContext, Bitmap inImage, String fileName) {

        String file_path = Environment.getExternalStorageDirectory() + File.separator + APP_TEMP_FOLDER;
        File dir = new File(file_path);
        if (!dir.exists()) dir.mkdirs();

        File file = new File(dir, fileName);

        try {

            FileOutputStream fos = new FileOutputStream(file);

            inImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {

            Toast.makeText(inContext, "Error occured. Please try again later.", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {

            e.printStackTrace();
        }

        return Environment.getExternalStorageDirectory() + File.separator + APP_TEMP_FOLDER + File.separator + fileName;
    }

    private void pickImage() {
        dialog.setContentView(R.layout.dialog_pick_image);
        dialog.setTitle("Add Photo");
        selectedPostImg = "";
        dialog.findViewById(R.id.tvPickCamera).setOnClickListener(this);
        dialog.findViewById(R.id.tvPickGallery).setOnClickListener(this);
        dialog.show();
    }

    public void choiceImage() {

        android.app.FragmentManager fm = getActivity().getFragmentManager();

        PostImageChooseDialog alert = new PostImageChooseDialog();

        alert.show(fm, "alert_dialog_image_choose");
    }

    public void imageFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, getText(R.string.label_select_img)), SELECT_POST_IMG);
    }

    public void imageFromCamera() {

        try {

            File root = new File(Environment.getExternalStorageDirectory(), APP_TEMP_FOLDER);

            if (!root.exists()) {

                root.mkdirs();
            }

            File sdImageMainDirectory = new File(root, "photo.jpg");
            outputFileUri = Uri.fromFile(sdImageMainDirectory);

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(cameraIntent, CREATE_POST_IMG);

        } catch (Exception e) {

            Toast.makeText(getActivity(), "Error occured. Please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap resize(String path){

        int maxWidth = 512;
        int maxHeight = 512;

        // create the options
        BitmapFactory.Options opts = new BitmapFactory.Options();

        //just decode the file
        opts.inJustDecodeBounds = true;
        Bitmap bp = BitmapFactory.decodeFile(path, opts);

        //get the original size
        int orignalHeight = opts.outHeight;
        int orignalWidth = opts.outWidth;

        //initialization of the scale
        int resizeScale = 1;

        //get the good scale
        if (orignalWidth > maxWidth || orignalHeight > maxHeight) {

            final int heightRatio = Math.round((float) orignalHeight / (float) maxHeight);
            final int widthRatio = Math.round((float) orignalWidth / (float) maxWidth);
            resizeScale = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        //put the scale instruction (1 -> scale to (1/1); 8-> scale to 1/8)
        opts.inSampleSize = resizeScale;
        opts.inJustDecodeBounds = false;

        //get the futur size of the bitmap
        int bmSize = (orignalWidth / resizeScale) * (orignalHeight / resizeScale) * 4;

        //check if it's possible to store into the vm java the picture
        if (Runtime.getRuntime().freeMemory() > bmSize) {

            //decode the file
            bp = BitmapFactory.decodeFile(path, opts);

        } else {

            return null;
        }

        return bp;
    }

    public void save(String outFile, String inFile) {

        try {

            Bitmap bmp = resize(outFile);

            File file = new File(Environment.getExternalStorageDirectory() + File.separator + APP_TEMP_FOLDER, inFile);
            FileOutputStream fOut = new FileOutputStream(file);

            bmp.compress(Bitmap.CompressFormat.JPEG, 90, fOut);
            fOut.flush();
            fOut.close();

        } catch (Exception ex) {

            Log.e("Error", ex.getMessage());
        }
    }

    public Boolean uploadFile(String serverURL, File file) {

        final OkHttpClient client = new OkHttpClient();

        try {

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("uploaded_file", file.getName(), RequestBody.create(MediaType.parse("text/csv"), file))
                    .addFormDataPart("accountId", Long.toString(App.getInstance().getId()))
                    .addFormDataPart("accessToken", App.getInstance().getAccessToken())
                    .build();

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                    .url(serverURL)
                    .addHeader("Accept", "application/json;")
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(com.squareup.okhttp.Request request, IOException e) {

                    loading = false;

                    hidepDialog();

                    Log.e("failure", request.toString());
                }

                @Override
                public void onResponse(com.squareup.okhttp.Response response) throws IOException {

                    String jsonData = response.body().string();

                    Log.e("response", jsonData);

                    try {

                        JSONObject result = new JSONObject(jsonData);

                        if (!result.getBoolean("error")) {

                            originImgUrl = result.getString("originPhotoUrl");
                            imgUrl = result.getString("normalPhotoUrl");
                            previewImgUrl = result.getString("previewPhotoUrl");
                            Log.e(TAG, "onResponse: oriUrl = " + originImgUrl + " normal photo url = "+ imgUrl +
                            " preview img url = " + previewImgUrl);
                        }

                        Log.d("My App", response.toString());

                    } catch (Throwable t) {

                        Log.e("My App", "Could not parse malformed JSON: \"" + t.getMessage() + "\"");

                    } finally {

                        Log.e("response", jsonData);

                        sendPost();
                    }

                }
            });

            return true;

        } catch (Exception ex) {
            // Handle the error

            loading = false;

            hidepDialog();
        }

        return false;
    }


    public void sendPost() {

        CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_PHOTOS_NEW, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if (!response.getBoolean("error")) {


                            }

                        } catch (JSONException e) {

                            e.printStackTrace();

                        } finally {

                            sendSuccess();

                            Log.e("Result", response.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                sendSuccess();

//                     Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", Long.toString(App.getInstance().getId()));
                params.put("accessToken", App.getInstance().getAccessToken());
                params.put("accessMode", Integer.toString(postMode));
                params.put("itemType", Integer.toString(itemType));
                params.put("comment", "No comment");
//                params.put("comment", commentText);
                params.put("imgUrl", imgUrl);
                params.put("videoUrl", videoUrl);
                params.put("originImgUrl", originImgUrl);
                params.put("previewImgUrl", previewImgUrl);
                params.put("postArea", postArea);
                params.put("postCountry", postCountry);
                params.put("postCity", postCity);
                params.put("postLat", postLat);
                params.put("postLng", postLng);

                return params;
            }
        };

        App.getInstance().addToRequestQueue(jsonReq);
    }

    public void sendSuccess() {

        loading = false;

        hidepDialog();

        Intent i = new Intent();
        getActivity().setResult(RESULT_OK, i);

        Toast.makeText(getActivity(), getText(R.string.msg_item_added), Toast.LENGTH_SHORT).show();

        getActivity().finish();
    }

    @Override
    public void onAttach(Activity context) {
            super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface UpdateProfileListener {
        void onProfileUpdateClickListerner();
    }

}
