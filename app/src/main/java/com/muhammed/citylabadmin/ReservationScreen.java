package com.muhammed.citylabadmin;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.muhammed.citylabadmin.data.model.reservation.Booking;
import com.muhammed.citylabadmin.data.model.reservation.Datum;
import com.muhammed.citylabadmin.data.model.user.User;
import com.muhammed.citylabadmin.databinding.FragmentUsersScreenBinding;
import com.muhammed.citylabadmin.helper.LoadingDialog;
import com.muhammed.citylabadmin.helper.NetworkState;
import com.muhammed.citylabadmin.service.RetrofitService;
import com.muhammed.citylabadmin.ui.adapter.resvrvation.ReservationAdapter;
import com.muhammed.citylabadmin.ui.adapter.user.UserAdapter;
import com.muhammed.citylabadmin.ui.reservationes.ReservationViewmodle;
import com.muhammed.citylabadmin.ui.users.UserViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;
import dagger.hilt.android.internal.lifecycle.HiltViewModelMap;
import dagger.hilt.android.lifecycle.HiltViewModel;

public class ReservationScreen extends Fragment {

     RetrofitService retrofitService;
    Context context;
    public List<Datum> all_reservation= new ArrayList<>();
    private ReservationAdapter adapter ;
    private FragmentUsersScreenBinding binding;
    private ReservationViewmodle reservationViewmodle;
    ProgressBar progressBar;
    RecyclerView recyclerView;

    private void initRecycler(){

    }

    private RecyclerView.LayoutManager layoutManager;

    public ReservationScreen() {
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
        return inflater.inflate(R.layout.fragment_reservation_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //binding=FragmentUsersScreenBinding.bind(view);
        recyclerView=view.findViewById(R.id.reservation_recycler);
        progressBar=view.findViewById(R.id.prograsssreservation);
        adapter=new ReservationAdapter(getContext(),retrofitService);
        layoutManager=new LinearLayoutManager(requireContext());
        reservationViewmodle= ViewModelProviders.of(getActivity()).get(ReservationViewmodle.class);
        reservationViewmodle.getAllReservation().observe(getViewLifecycleOwner(), new Observer<Booking>() {
            @Override
            public void onChanged(Booking booking) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter.addreserv(booking.getData());
                recyclerView.setAdapter(adapter);
            }
        });

    }


}