package com.muhammed.citylabadmin.service;

import com.muhammed.citylabadmin.data.model.login.LoginResponse;
import com.muhammed.citylabadmin.data.model.general.SimpleResponse;
import com.muhammed.citylabadmin.data.model.login.UserData;
import com.muhammed.citylabadmin.data.model.user.UsersResponse;
import com.muhammed.citylabadmin.helper.ResultRequest;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface RetrofitService {

    @FormUrlEncoded
    @POST("api/Users/Login")
    Single<LoginResponse> userLogin(@Field("PhoneNumber") String phone);


    @FormUrlEncoded
    @POST("api/offers/upload")
    @Headers("Accept:application/json")
    Single<SimpleResponse> uploadOffer(@Field("Files")String image,
                                       @Field("title") String title,
                                       @Field("description") String desc,
                                       @Field("StartTime") String sDate,
                                       @Field("EndTime") String eDate,
                                       @Field("PreviousPrice") Double oldPrice,
                                       @Field("CurrentPrice") Double newPrice);


//    @Multipart
//    @POST("api/offers/upload")
//    @Headers("Accept:application/json")
//    Single<SimpleResponse> sendResult(@PartMap Map<String, List<MultipartBody.Part>> Files,
//                                      @Part("PhoneNumber") String phone);


    @FormUrlEncoded
    @POST("api/Results/Upload")
    @Headers("Accept:application/json")
    Single<SimpleResponse> sendResult(@Field("Files") List<String> files,
                                      @Field("PhoneNumber") String phone,
                                      @Field("notes") String note);


//    @POST("api/Results/Upload")
//    @Headers("Accept:application/json")
//    Single<SimpleResponse> sendResult(@Body ResultRequest request);


    @FormUrlEncoded
    @POST("api/Users/Register")
    @Headers("Accept:application/json")
    Single<SimpleResponse> addUser(@Field("Name") String name,
                                   @Field("PhoneNumber") String phone);

    @GET("api/users/all")
    @Headers("Accept:application/json")
    Single<UsersResponse> getAllUsers();

}
