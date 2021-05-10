package com.muhammed.citylabadmin.ui.users;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.muhammed.citylabadmin.ui.adapter.user.UserClickListener;
import com.muhammed.citylabadmin.ui.users.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class UsersScreen extends Fragment implements UserClickListener {


    Context context;
    public List<User> all_user= new ArrayList<>();
    private static final int REQUEST_RUNTIME_PERMISSION = 123;
    String[] permissons = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG};
    private FragmentUsersScreenBinding binding;
    private UserViewModel userViewModel;


    private UserAdapter adapter ;
    private RecyclerView.LayoutManager layoutManager;


    private void initRecycler(){

        adapter=new UserAdapter(this);
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

        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        List<User> all_user1= new ArrayList<>();
        //looping through existing elements
        for (User s : all_user) {
            //if the existing elements contains the search input
            if (s.getName().toLowerCase().contains(text.toLowerCase()) || s.getPhoneNumber().contains(text)) {
                //adding the element to filtered list
                all_user1.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.addUsers(all_user1);
    }

    private void observe(){
        userViewModel.usersLiveData.observe(getViewLifecycleOwner(), new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {

                switch (networkState.status) {
                    case SUCCESS:
                            adapter.addUsers((List<User>) networkState.data);
                        all_user=(List<User>) networkState.data;
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

    @Override
    public void openWhatsApp(User user) {
        //abdalaaah whats app
        String contact = "+02 " + user.getPhoneNumber(); // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    @Override
    public void sendResultToUser(User user) {

        Bundle bundle = new Bundle();
        bundle.putString("name",user.getName());
        bundle.putString("phone",user.getPhoneNumber());

        Navigation.findNavController(getView()).
                navigate(R.id.action_usersScreen_to_sendUserResultScreen,bundle);
    }
}