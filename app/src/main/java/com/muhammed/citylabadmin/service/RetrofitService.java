package com.muhammed.citylabadmin.service;

import com.muhammed.citylabadmin.data.model.login.LoginResponse;
import com.muhammed.citylabadmin.data.model.general.SimpleResponse;
import com.muhammed.citylabadmin.data.model.login.UserData;
import com.muhammed.citylabadmin.data.model.user.UsersResponse;

import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitService {

    @FormUrlEncoded
    @POST("api/Users/Login")
    Single<LoginResponse> userLogin(@Field("PhoneNumber") String phone);


    @Multipart
    @POST("api/offers/upload")
    @Headers("Accept:application/json")
    Single<SimpleResponse> uploadOffer(@Part MultipartBody.Part part,
                                       @Part("title") String title,
                                       @Part("description") String desc,
                                       @Part("StartTime") String sDate,
                                       @Part("EndTime") String eDate,
                                       @Part("PreviousPrice") Double oldPrice,
                                       @Part("CurrentPrice") Double newPrice);


    @POST("api/offers/upload")
    @Headers("Accept:application/json")
    Single<SimpleResponse> sendResult(@Part List<MultipartBody.Part> files,
                                      @Part("phone") String phone);


    @FormUrlEncoded
    @POST("api/Users/Register")
    @Headers("Accept:application/json")
    Single<SimpleResponse> addUser(@Field("Name") String name,
                                   @Field("PhoneNumber") String phone);

    @GET("api/users/all")
    @Headers("Accept:application/json")
    Single<UsersResponse>getAllUsers();

}
