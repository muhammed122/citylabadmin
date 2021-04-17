package com.muhammed.citylabadmin.helper;

import android.graphics.Bitmap;

import java.io.InputStream;

public class FileData {

    Bitmap bitmap;
    String base64;

    public FileData(Bitmap bitmap, String base64) {
        this.bitmap = bitmap;
        this.base64 = base64;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getBase64() {
        return base64;
    }
}
