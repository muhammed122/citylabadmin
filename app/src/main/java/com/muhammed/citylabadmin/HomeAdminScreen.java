package com.muhammed.citylabadmin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muhammed.citylabadmin.databinding.FragmentHomeAdminScreenBinding;
import com.muhammed.citylabadmin.databinding.FragmentLoginScreenBinding;

public class HomeAdminScreen extends Fragment {


    private FragmentHomeAdminScreenBinding binding;

    private NavController navController;

    public HomeAdminScreen() {
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
        return inflater.inflate(R.layout.fragment_home_admin_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHomeAdminScreenBinding.bind(view);
        navController = Navigation.findNavController(view);

        binding.sendOffersItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_homeAdminScreen_to_uploadOfferScreen);

            }
        });

        binding.sendResultItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_homeAdminScreen_to_sendUserResultScreen);
            }
        });


    }
}