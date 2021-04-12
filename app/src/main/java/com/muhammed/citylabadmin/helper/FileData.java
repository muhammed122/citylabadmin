package com.muhammed.citylabadmin.helper;

import android.graphics.Bitmap;

import java.io.InputStream;

public class FileData {

    Bitmap bitmap;
    byte [] bytes ;

    public FileData(Bitmap bitmap, byte[] bytes) {
        this.bitmap = bitmap;
        this.bytes = bytes;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
