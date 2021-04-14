package com.muhammed.citylabadmin.ui.offer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.muhammed.citylabadmin.R;
import com.muhammed.citylabadmin.base.BaseFragment;
import com.muhammed.citylabadmin.databinding.FragmentLoginScreenBinding;
import com.muhammed.citylabadmin.databinding.FragmentUploadOfferScreenBinding;
import com.muhammed.citylabadmin.helper.LoadingDialog;
import com.muhammed.citylabadmin.helper.NetworkState;
import com.muhammed.citylabadmin.ui.login.LoginViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


@AndroidEntryPoint
public class UploadOfferScreen extends BaseFragment implements PopupMenu.OnMenuItemClickListener {

    private OfferViewModel viewModel;
    private FragmentUploadOfferScreenBinding binding;

    InputStream inputStream;
    ByteArrayOutputStream bytes;


    final Calendar myCalendar = Calendar.getInstance();
    private Date startOfferDate;
    private Date endOfferDate;

    DatePickerDialog.OnDateSetListener startDateSetListener;
    DatePickerDialog.OnDateSetListener endDateSetListener;


    private SimpleDateFormat getDateFromString(String date) {

        String myFormat = "MM/dd/yy"; //In which you need put here
        return new SimpleDateFormat(myFormat, Locale.US);
    }

    private void initDateDialog() {
        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                binding.startOfferDate.setText(date);
            }
        };

        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month += 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                binding.endOfferDate.setText(date);

            }
        };
    }


    public UploadOfferScreen() {
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
        return inflater.inflate(R.layout.fragment_upload_offer_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentUploadOfferScreenBinding.bind(view);
        binding.toolbar.toolbarTitle.setText(getString(R.string.send_offers));

        viewModel = new ViewModelProvider(this).get(OfferViewModel.class);
        initDateDialog();

        DateFormat dffrom = new SimpleDateFormat("M/dd/yyyy");
        DateFormat dfto = new SimpleDateFormat("yyyy-MM-dd");
        Date today = null;
        try {
            today = dffrom.parse("7/1/2011");
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        String s = dfto.format(today);

        Log.d("dddddd", "onViewCreated: "+s);


        binding.startDateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(
                        requireContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        startDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        binding.endDateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        requireContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        endDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        binding.uploadOfferImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpMenu(v);

            }
        });
        binding.removeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.offerImage.setImageResource(R.drawable.ic_camera);
                binding.removeIcon.setVisibility(View.GONE);
            }
        });
        binding.uploadOfferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bytes == null) {
                    try {
                        if (inputStream != null)
                            uploadOfferData(getBytes(inputStream));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else uploadOfferData(bytes.toByteArray());
            }
        });

        observe();

    }

    public void restAllWidgets() {
        binding.startOfferDate.setText("");
        binding.endOfferDate.setText("");
        binding.oldOfferPrice.setText("");
        binding.newOfferPrice.setText("");
        binding.offerImage.setImageResource(R.drawable.ic_camera);
        binding.offerNote.setText("");
        binding.offerTitle.setText("");
        binding.removeIcon.setVisibility(View.GONE);
    }

    public void observe() {
        viewModel.addOfferLiveData.observe(getViewLifecycleOwner(), new Observer<NetworkState>() {
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
                if (checkStoragePermission(MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_CAMERA))
                    cameraIntent();
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

                showToast("يجب السماح بالوصول للملفات لتمام العملية");

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data.getData() != null) {
                try {
                    inputStream = getActivity().getContentResolver().openInputStream(data.getData());

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
                binding.offerImage.setImageBitmap(bm);
                binding.removeIcon.setVisibility(View.VISIBLE);
                inputStream = requireContext().getContentResolver().openInputStream(data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        if (data != null) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

            binding.offerImage.setImageBitmap(thumbnail);
            binding.removeIcon.setVisibility(View.VISIBLE);


        }

    }

    private void uploadImage(byte[] imageBytes) {
        if (imageBytes == null)
            showToast("يجب اختيار صورة");
        else {
            RequestBody requestFile = RequestBody.create(imageBytes, MediaType.parse("image/jpeg"));
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", "image.jpg",
                    requestFile);
        }
        //upload image viewModel
    }


    private void uploadOfferData(byte[] imageBytes) {
        String title = Objects.requireNonNull(binding.offerTitle.getText()).toString().trim();
        String startDate = binding.startOfferDate.getText().toString().trim();
        String endDate = binding.endOfferDate.getText().toString().trim();
        String startPrice = Objects.requireNonNull(binding.oldOfferPrice.getText()).toString().trim();
        String endPrice = Objects.requireNonNull(binding.newOfferPrice.getText()).toString().trim();
        String desc = Objects.requireNonNull(binding.offerNote.getText()).toString().trim();

        if (title.isEmpty() || startDate.isEmpty() || endDate.isEmpty() ||
                startPrice.isEmpty() || endPrice.isEmpty() || desc.isEmpty()) {
            showToast("ادخل البيانات كاملة");
            return;
        }

        if (imageBytes == null) {
            showToast("يجب اختيار صورة");
        } else {
            RequestBody requestFile = RequestBody.create(imageBytes, MediaType.parse("image/jpeg"));
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", "image.jpg",
                    requestFile);

            viewModel.addOffer(body, title, desc, startDate, endDate,
                    Double.parseDouble(startPrice), Double.parseDouble(endPrice));

        }


    }


}