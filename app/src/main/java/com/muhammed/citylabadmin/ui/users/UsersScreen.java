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

    @Override
    public void openWhatsApp(User user) {
        //abdalaaah whats app

        if (CheckPermission(getContext(), permissons[0])) {
            // you have permission go ahead
if(contactExists(getContext(),user.getPhoneNumber())) {
    String contact = "+02 "+user.getPhoneNumber(); // use country code with your phone number
    String url = "https://api.whatsapp.com/send?phone=" + contact;
    Intent i = new Intent(Intent.ACTION_VIEW);
    i.setData(Uri.parse(url));
    startActivity(i);
}
else
    {

        // Creates a new Intent to insert a contact
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
// Sets the MIME type to match the Contacts Provider
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        /*
         * Inserts new data into the Intent. This data is passed to the
         * contacts app's Insert screen
         */
// Inserts an email address
        intent.putExtra(ContactsContract.Intents.Insert.NAME, user.getName()+"lab")
/*
 * In this example, sets the email type to be a work email.
 * You can set other email types as necessary.
 */

// Inserts a phone number
                .putExtra(ContactsContract.Intents.Insert.PHONE, user.getPhoneNumber());
/*
 * In this example, sets the phone type to be a work phone.
 * You can set other phone types as necessary.
 */
        startActivity(intent);
    }
        } else {
            // you do not have permission go request runtime permissions
            RequestPermission(getActivity(), permissons, REQUEST_RUNTIME_PERMISSION);
        }



    }
    public boolean contactExists(Context context, String number) {
        /// number is the phone number
        Uri lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String[] mPhoneNumberProjection = { ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME };
        Cursor cur = context.getContentResolver().query(lookupUri,mPhoneNumberProjection, null, null, null);
        try {
            if (cur.moveToFirst()) {
                cur.close();
                return true;
            }
        } finally {
            if (cur != null)
                cur.close();
        }
        return false;
    }

    private void YourTaskNow() {
        //your task now

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {

            case REQUEST_RUNTIME_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // you have permission go ahead
                    YourTaskNow();
                } else {
                    // you do not have permission show toast.
                }
                return;
            }
        }
    }

    public void RequestPermission(Activity thisActivity, String[] Permission, int Code) {
        if (ContextCompat.checkSelfPermission(thisActivity,
                Permission[0])
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    Permission[0])) {
            } else {
                ActivityCompat.requestPermissions(thisActivity, Permission,
                        Code);
            }
        }
    }

    public boolean CheckPermission(Context context, String Permission) {
        if (ContextCompat.checkSelfPermission(context,
                Permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
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