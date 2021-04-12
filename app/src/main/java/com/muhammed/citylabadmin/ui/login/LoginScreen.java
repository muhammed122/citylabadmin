package com.muhammed.citylabadmin.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.muhammed.citylabadmin.R;
import com.muhammed.citylabadmin.data.model.login.UserData;
import com.muhammed.citylabadmin.databinding.FragmentLoginScreenBinding;
import com.muhammed.citylabadmin.helper.LoadingDialog;
import com.muhammed.citylabadmin.helper.MyPreference;
import com.muhammed.citylabadmin.helper.NetworkState;
import com.muhammed.citylabadmin.helper.Utile;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class LoginScreen extends Fragment {

    private LoginViewModel viewModel;
    private FragmentLoginScreenBinding binding;

    private String phone = "";


    public LoginScreen() {
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
        return inflater.inflate(R.layout.fragment_login_screen, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentLoginScreenBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);



        binding.loginUserPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    phone = s.toString();
                    Utile.hideKeyboard(getActivity());
                    binding.loginBtn.setEnabled(true);
                }
                else
                    binding.loginBtn.setEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.userLogin(phone);
            }
        });


        viewModel.loginLiveData.observe(getViewLifecycleOwner(), new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {

                switch (networkState.status) {
                    case SUCCESS:
                        MyPreference.saveUser((UserData) networkState.data);
                        Log.d("dddddddddd", "onChanged: token " + networkState.data);
                        LoadingDialog.hideDialog();
                        Navigation.findNavController(getView()).navigate(R.id.action_loginScreen_to_homeAdminScreen);
                        break;
                    case FAILED:
                        LoadingDialog.hideDialog();
                        Toast.makeText(getContext(), ""+networkState.message, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        LoadingDialog.showDialog(getActivity());
                        break;
                }
            }
        });


    }

}