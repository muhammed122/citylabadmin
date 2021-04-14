package com.muhammed.citylabadmin.ui.users;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.muhammed.citylabadmin.data.model.general.SimpleResponse;
import com.muhammed.citylabadmin.data.model.login.UserData;
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

@HiltViewModel
public class UserViewModel extends ViewModel {

    private final RetrofitService retrofitService;

    @Inject
    public UserViewModel(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<NetworkState> _addUserLiveData = new MutableLiveData<NetworkState>();
    public LiveData<NetworkState> addUserLiveData = _addUserLiveData;

    public void addNewUser(String name, String phone) {
        retrofitService.addUser(name, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        _addUserLiveData.postValue(NetworkState.LOADING);
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull SimpleResponse offerResponse) {

                        if (!offerResponse.isStatus())
                            _addUserLiveData.postValue(NetworkState.getErrorMessage(offerResponse.getMessage()));
                        else
                            _addUserLiveData.postValue(NetworkState.getLoaded(offerResponse.getMessage()));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        _addUserLiveData.postValue(NetworkState.getErrorMessage(e));


                    }
                });


    }


    private final MutableLiveData<NetworkState> _usersLiveData = new MutableLiveData<NetworkState>();
    public LiveData<NetworkState> usersLiveData = _usersLiveData;

    public void getAllUsers() {
        retrofitService.getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UsersResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                        _usersLiveData.postValue(NetworkState.LOADING);
                    }
                    @Override
                    public void onSuccess(@NonNull UsersResponse userData) {
                        if (!userData.isStatus())
                            _usersLiveData.postValue(NetworkState.getErrorMessage(userData.getMessage()));
                        else
                            _usersLiveData.postValue(NetworkState.getLoaded(userData.getData()));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _usersLiveData.postValue(NetworkState.getErrorMessage(e.getLocalizedMessage()));
                    }
                });


    }

    @Override
    protected void onCleared () {
        super.onCleared();
        disposable.clear();
    }

}