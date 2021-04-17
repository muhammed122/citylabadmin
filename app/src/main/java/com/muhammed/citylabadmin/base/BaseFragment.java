package com.muhammed.citylabadmin.base;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";
    public static final int REQUEST_CAMERA_CODE = 101;
    public static final int REQUEST_GALLERY_CODE = 102;
    private static final int PDF_REQUEST_CODE =103 ;

    //storage data
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_CAMERA = 122;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_GALLERY = 123;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_PDF = 124;


    ByteArrayOutputStream bytes;


    public void cameraIntent() {
        if (checkStoragePermission(MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA_CODE);
        }
    }

    public void galleryIntent() {
        if (checkStoragePermission(MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_GALLERY)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_GALLERY_CODE);
        }
    }


    public void pdfIntent() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), PDF_REQUEST_CODE);
    }


    private Boolean isStoragePermissionAllowed() {
        return ContextCompat.
                checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission(final int REQUEST_CODE) {
        if (ActivityCompat.shouldShowRequestPermissionRationale
                (getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //explain why do u want
            showUIMessage("امكانية الوصول الي الملفات لتنفيذ العملية", REQUEST_CODE);
        } else {
            //request permission
            askStoragePermission(REQUEST_CODE);
        }
    }


    private void askStoragePermission(final int REQUEST_CODE) {
        requestPermissions(
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE);
    }


    private void showUIMessage(String message, final int REQUEST_CODE) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //request permission again
                askStoragePermission(REQUEST_CODE);
            }
        });

        builder.setCancelable(false);
        builder.show();

    }

    public Boolean checkStoragePermission(final int REQUEST_CODE) {
        if (isStoragePermissionAllowed())
            return true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            requestStoragePermission(REQUEST_CODE);
        } else {
            return true;
        }

        return true;
    }


//    private void onCaptureImageResult(Intent data) {
//        if (data != null) {
//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            bytes = new ByteArrayOutputStream();
//            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//            photoImage.setImageBitmap(thumbnail);
//
//            confirmImageLayout.setVisibility(View.VISIBLE);
//            selectImageLayout.setVisibility(View.GONE);
//        }
//
//    }


    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        int buffSize = 1024;
        byte[] buff = new byte[buffSize];


        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    private void uploadImage(byte[] imageBytes) {
        Log.d(TAG, imageBytes.toString());
        RequestBody requestFile = RequestBody.create(imageBytes, MediaType.parse("image/jpeg"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", "image.jpg",
                requestFile);
        Log.d(TAG, "ddddd uploadImage: " + body);
        Log.d(TAG, "ddddd uploadImage: " + requestFile);

//        uploadFilePresenter.uploadFile(body);
    }


    public void showToast(String message) {
        Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
    }

}
