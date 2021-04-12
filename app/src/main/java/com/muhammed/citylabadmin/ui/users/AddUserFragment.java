package com.muhammed.citylabadmin.ui.users;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.muhammed.citylabadmin.R;
import com.muhammed.citylabadmin.data.model.login.UserData;
import com.muhammed.citylabadmin.databinding.FragmentAddUserBinding;
import com.muhammed.citylabadmin.databinding.FragmentLoginScreenBinding;
import com.muhammed.citylabadmin.helper.LoadingDialog;
import com.muhammed.citylabadmin.helper.MyPreference;
import com.muhammed.citylabadmin.helper.NetworkState;
import com.muhammed.citylabadmin.ui.login.LoginViewModel;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class AddUserFragment extends Fragment {

    private UserViewModel viewModel;
    private FragmentAddUserBinding binding;

    public AddUserFragment() {
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
        return inflater.inflate(R.layout.fragment_add_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAddUserBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        binding.addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addUser();
            }
        });
        observe();

    }

    private void observe() {
        viewModel.addUserLiveData.observe(getViewLifecycleOwner(), new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                switch (networkState.status) {
                    case SUCCESS:
                        Toast.makeText(getContext(), "" + networkState.data.toString(),
                                Toast.LENGTH_SHORT).show();

                        binding.newUserName.setText("");
                        binding.newUserPhone.setText("");
                        LoadingDialog.hideDialog();
                        break;
                    case FAILED:
                        LoadingDialog.hideDialog();
                        Toast.makeText(getContext(), "" + networkState.message, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        LoadingDialog.showDialog(getActivity());
                        break;
                }
            }

        });


    }

    private void addUser() {
        String name = binding.newUserName.getText().toString().trim();
        String phone = binding.newUserPhone.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(requireContext(), "ادخل البيانات كاملة", Toast.LENGTH_SHORT).show();
        }
        viewModel.addNewUser(name, phone);


    }
}