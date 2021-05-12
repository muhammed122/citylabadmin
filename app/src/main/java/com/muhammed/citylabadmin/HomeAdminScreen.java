package com.muhammed.citylabadmin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

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

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(1000);
        final CardView splash = view.findViewById(R.id.send_result_item);
        splash.startAnimation(animation);



        Animation animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        animation2.setInterpolator(new LinearInterpolator());
        animation2.setRepeatCount(Animation.INFINITE);
        animation2.setDuration(1100);
        final CardView splash2 = view.findViewById(R.id.send_offers_item);
        splash2.startAnimation(animation2);



        Animation animation3 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        animation3.setInterpolator(new LinearInterpolator());
        animation3.setRepeatCount(Animation.INFINITE);
        animation3.setDuration(1200);
        final CardView splash3 = view.findViewById(R.id.add_user_item);
        splash3.startAnimation(animation3);




        Animation animation4 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        animation4.setInterpolator(new LinearInterpolator());
        animation4.setRepeatCount(Animation.INFINITE);
        animation4.setDuration(1300);
        final CardView splash4 = view.findViewById(R.id.show_users_item);
        splash4.startAnimation(animation4);




        Animation animation5 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        animation5.setInterpolator(new LinearInterpolator());
        animation5.setRepeatCount(Animation.INFINITE);
        animation5.setDuration(1400);
        final CardView splash5 = view.findViewById(R.id.See_All_Result);
        splash5.startAnimation(animation5);





        binding.sendOffersItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_homeAdminScreen_to_uploadOfferScreen);

            }
        });
        binding.SeeAllResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_homeAdminScreen_to_reservationScreen);

            }
        });

        binding.sendResultItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_homeAdminScreen_to_sendUserResultScreen);
            }
        });


        binding.addUserItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.navigate(R.id.action_homeAdminScreen_to_addUserFragment);
            }
        });


        binding.showUsersItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_homeAdminScreen_to_usersScreen);
            }
        });
    }
}