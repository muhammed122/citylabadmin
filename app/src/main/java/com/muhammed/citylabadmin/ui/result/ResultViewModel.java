package com.muhammed.citylabadmin.ui.result;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.muhammed.citylabadmin.data.model.general.SimpleResponse;
import com.muhammed.citylabadmin.helper.NetworkState;
import com.muhammed.citylabadmin.helper.ResultRequest;
import com.muhammed.citylabadmin.service.RetrofitService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


@HiltViewModel
public class ResultViewModel extends ViewModel {


    private final RetrofitService retrofitService;

    @Inject
    public ResultViewModel(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<NetworkState> _sendResultLiveData = new MutableLiveData<NetworkState>();
    public LiveData<NetworkState> sendResultLiveData = _sendResultLiveData;

    public void sendResult(List<String> files, String phone , String note) {

//        Map<String,List<MultipartBody.Part>> map = new HashMap<>();
//        map.put("Files",files);

   //     Log.d("ddddddddddddd", "sendResult: base64    :   "+files.get(0));

        retrofitService.sendResult(files,phone,note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        _sendResultLiveData.postValue(NetworkState.LOADING);
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull SimpleResponse offerResponse) {

                        if (!offerResponse.isStatus())
                            _sendResultLiveData.postValue(NetworkState.getErrorMessage(offerResponse.getMessage()));
                        else
                            _sendResultLiveData.postValue(NetworkState.getLoaded(offerResponse.getMessage()));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        _sendResultLiveData.postValue(NetworkState.getErrorMessage(e));


                    }
                });


    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
