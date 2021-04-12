package com.muhammed.citylabadmin.data.request;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;

public class ResultRequest {

    String StartTime;
    String EndTime;
    String title;
    MultipartBody.Part file ;
    String description;
    Double CurrentPrice;
    Double PreviousPrice;

    public ResultRequest(String startTime, String endTime,
                         String title, MultipartBody.Part file,
                         String description, Double currentPrice, Double previousPrice) {
        StartTime = startTime;
        EndTime = endTime;
        this.title = title;
        this.file = file;
        this.description = description;
        CurrentPrice = currentPrice;
        PreviousPrice = previousPrice;
    }
}
