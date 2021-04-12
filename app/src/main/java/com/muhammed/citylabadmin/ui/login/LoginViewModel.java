package com.muhammed.citylabadmin.ui.login;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.muhammed.citylabadmin.data.model.login.LoginResponse;
import com.muhammed.citylabadmin.helper.NetworkState;
import com.muhammed.citylabadmin.service.RetrofitService;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    private final RetrofitService retrofitService;

    @Inject
    public LoginViewModel(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<NetworkState> _loginLiveData = new MutableLiveData<NetworkState>();
    public LiveData<NetworkState> loginLiveData = _loginLiveData;
    public void userLogin(String phone) {

        retrofitService.userLogin(phone)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<LoginResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        _loginLiveData.postValue(NetworkState.LOADING);
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull LoginResponse loginResponse) {

                        if (!loginResponse.isStatus())
                            _loginLiveData.postValue(NetworkState.getErrorMessage(loginResponse.getMessage()));
                        else
                            _loginLiveData.postValue(NetworkState.getLoaded(loginResponse.getData()));

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        _loginLiveData.postValue(NetworkState.getErrorMessage(e));


                    }
                });


    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
