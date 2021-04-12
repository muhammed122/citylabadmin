package com.muhammed.citylabadmin.service;

import com.muhammed.citylabadmin.data.model.login.LoginResponse;
import com.muhammed.citylabadmin.data.model.offer.AddOfferResponse;

import java.util.Date;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitService {

    @POST("api/Users/Login")
    @FormUrlEncoded
    Single<LoginResponse> userLogin(@Field("PhoneNumber") String phone);


    @Multipart
    @POST("api/offers/upload")
    @Headers("Accept:application/json")
    Single<AddOfferResponse> uploadOffer(@Part MultipartBody.Part part,
                                         @Part("title") String title,
                                         @Part("description") String desc,
                                         @Part("StartTime") String sDate,
                                         @Part("EndTime") String eDate,
                                         @Part("PreviousPrice") Double oldPrice,
                                         @Part("CurrentPrice") Double newPrice );



}
