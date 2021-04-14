package com.muhammed.citylabadmin.ui.users;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.muhammed.citylabadmin.R;
import com.muhammed.citylabadmin.data.model.user.User;
import com.muhammed.citylabadmin.databinding.FragmentAddUserBinding;
import com.muhammed.citylabadmin.databinding.FragmentUsersScreenBinding;
import com.muhammed.citylabadmin.helper.LoadingDialog;
import com.muhammed.citylabadmin.helper.NetworkState;
import com.muhammed.citylabadmin.ui.adapter.user.UserAdapter;
import com.muhammed.citylabadmin.ui.users.UserViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class UsersScreen extends Fragment {


    private FragmentUsersScreenBinding binding;
    private UserViewModel userViewModel;


    private UserAdapter adapter ;
    private RecyclerView.LayoutManager layoutManager;


    private void initRecycler(){

        adapter=new UserAdapter();
        layoutManager=new LinearLayoutManager(requireContext());
        binding.usersRecycler.setLayoutManager(layoutManager);
        binding.usersRecycler.setAdapter(adapter);


    }

    public UsersScreen() {
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
        return inflater.inflate(R.layout.fragment_users_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=FragmentUsersScreenBinding.bind(view);
        userViewModel=new ViewModelProvider(this).get(UserViewModel.class);
        initRecycler();
        userViewModel.getAllUsers();
        observe();
    }

    private void observe(){
        userViewModel.usersLiveData.observe(getViewLifecycleOwner(), new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {

                switch (networkState.status) {
                    case SUCCESS:
                            adapter.addUsers((List<User>) networkState.data);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}