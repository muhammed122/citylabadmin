package com.muhammed.citylabadmin.helper;

import com.google.gson.annotations.SerializedName;

public class ResultRequest {

    @SerializedName("Files")
    String Files;
    @SerializedName("PhoneNumber")
    String PhoneNumber;

    public ResultRequest(String files, String phoneNumber) {
        Files = files;
        PhoneNumber = phoneNumber;
    }

    public String getFiles() {
        return Files;
    }

    public void setFiles(String files) {
        Files = files;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
