package com.muhammed.citylabadmin.ui.reservationes;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.muhammed.citylabadmin.data.model.reservation.Booking;
import com.muhammed.citylabadmin.data.model.reservation.Datum;
import com.muhammed.citylabadmin.data.model.user.UsersResponse;
import com.muhammed.citylabadmin.helper.NetworkState;
import com.muhammed.citylabadmin.service.RetrofitService;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class ReservationViewmodle extends ViewModel {

    private final RetrofitService retrofitService;
    @Inject
    public ReservationViewmodle(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    MutableLiveData<Booking> allreservation=new MediatorLiveData<>();
    public  MutableLiveData<Booking>  getAllReservation() {
        //login

        retrofitService.getAllReservation().enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(Call<Booking> call, Response<Booking> response) {
                if (response.isSuccessful())
                {
                    Log.d("TAG", "onResponse:reservation "+response.body().getMessage());

                    allreservation.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Booking> call, Throwable t) {
                Log.d("TAG", "onResponse:error"+t.getMessage());
            }
        });


    return  allreservation;
    }



}
