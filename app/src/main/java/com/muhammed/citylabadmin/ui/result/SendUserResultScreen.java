package com.muhammed.citylabadmin.ui.result;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.muhammed.citylabadmin.R;
import com.muhammed.citylabadmin.base.BaseFragment;
import com.muhammed.citylabadmin.databinding.FragmentSendUserResultScreenBinding;
import com.muhammed.citylabadmin.helper.FileData;
import com.muhammed.citylabadmin.helper.LoadingDialog;
import com.muhammed.citylabadmin.helper.NetworkState;
import com.muhammed.citylabadmin.ui.adapter.result.ResultFileClickListener;
import com.muhammed.citylabadmin.ui.adapter.result.ResultImageAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


@AndroidEntryPoint
public class SendUserResultScreen extends BaseFragment
        implements PopupMenu.OnMenuItemClickListener, ResultFileClickListener {

    private FragmentSendUserResultScreenBinding binding;
    private ResultViewModel viewModel;


    InputStream inputStream;
    ByteArrayOutputStream bytes;


    ResultImageAdapter adapter;
    RecyclerView.LayoutManager layoutManager;


    List<FileData> files = new ArrayList<>();
    List<MultipartBody.Part> parts;

    private void initRecycler() {
        adapter = new ResultImageAdapter(this);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.resultImagesRecycler.setAdapter(adapter);
        binding.resultImagesRecycler.setLayoutManager(layoutManager);
    }


    public SendUserResultScreen() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_user_result_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentSendUserResultScreenBinding.bind(view);

        binding.toolbar.toolbarTitle.setText(getString(R.string.send_results));


        viewModel = new ViewModelProvider(this).get(ResultViewModel.class);
        initRecycler();

        binding.uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpMenu(v);

            }
        });
        observe();

        binding.uploadPdfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkStoragePermission(MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_PDF)) {
                    pdfIntent();
                }
            }
        });

        binding.sendResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendResult();
            }
        });

    }


    private void popUpMenu(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.file_type_items);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.camera_item:
                if (checkStoragePermission(MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_CAMERA)) {
                    cameraIntent();
                }
                return true;
            case R.id.gallery_item:
                if (checkStoragePermission(MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_GALLERY))
                    galleryIntent();
                return true;
        }


        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraIntent();
                    return;
                }
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent();
                    return;
                }
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_PDF:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pdfIntent();
                    return;
                }

                showToast("يجب السماح بالوصول للملفات لتمام العملية");

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data.getData() != null) {
                try {
                    inputStream = getActivity().getContentResolver()
                            .openInputStream(data.getData());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (requestCode == REQUEST_GALLERY_CODE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA_CODE)
                onCaptureImageResult(data);

        }
    }


    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), data.getData());
                //add image to adapter
                inputStream = requireContext().getContentResolver().openInputStream(data.getData());
                files.add(new FileData(bm, encodeToBase64(bm)));
                Log.d("dddddddddd", "onSelectFromGalleryResult: ");
                adapter.addImage(files);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        if (data != null) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            bytes = new ByteArrayOutputStream();
          //  thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

            //add image to adapter
            files.add(new FileData(thumbnail,encodeToBase64(thumbnail)));

            adapter.addImage(files);


        }

    }

    private void observe() {
        viewModel.sendResultLiveData.observe(getViewLifecycleOwner(), new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {

                Log.d("dddddd", "onChanged: " + networkState.message);
                switch (networkState.status) {
                    case SUCCESS:
                        LoadingDialog.hideDialog();
                        restAllWidgets();
                        Toast.makeText(requireContext(), "" + networkState.data.toString(),
                                Toast.LENGTH_SHORT).show();
                        break;
                    case FAILED:
                        LoadingDialog.hideDialog();
                        Toast.makeText(requireContext(), "" + networkState.message.toString(),
                                Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        LoadingDialog.showDialog(requireActivity());
                        break;


                }


            }
        });


    }

    private void restAllWidgets() {
    }

    @Override
    public void removeFile(int pos) {
        files.remove(pos);
        adapter.addImage(files);

    }

    private void sendResult() {
        String phone = binding.userPhoneResult.getText().toString();
        if (phone.isEmpty()) {
            Toast.makeText(requireContext(), "ادخل البيانات", Toast.LENGTH_SHORT).show();
            return;
        }
        if (files.size() == 0) {
            Toast.makeText(requireContext(), "ادخل النتائج", Toast.LENGTH_SHORT).show();
            return;
        }
        parts = new ArrayList<>();

        List<String> base64 = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
//            Log.d("dddddddddd", "sendResult: "+i);
//            RequestBody requestFile = RequestBody.create(files.get(i).getBytes(), MediaType.parse("image/jpeg"));
//            MultipartBody.Part body = MultipartBody.Part.createFormData("Files", "image.jpg",
//                    requestFile);


            base64.add(files.get(i).getBytes());

            // parts.add(body);
        }

        viewModel.sendResult(base64, phone);


    }


    public static String encodeToBase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

}