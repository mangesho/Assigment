package com.assigment.models;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class ImageFileImpl implements ImageFile {

    File mImageFile;
    Context mContext;
    Uri mImageUri;

    @Override
    public void create(Context context) {}

    @Override
    public void create(Context context, String filePath) throws IOException {
        mContext = context;
        mImageFile = new File(filePath);
    }

    public File getFile() {
        return mImageFile;
    }

    public void setFile(File file) {
        mImageFile = file;
    }



    @Override
    public String getPath() {
        if (mImageFile == null) {
            return "";
        }
        mImageUri = Uri.fromFile(mImageFile);
        return mImageUri.toString();
    }

    @Override
    public Uri getContentUri() {
        if (mImageUri == null) {
            return Uri.parse(getPath());
        }

        return mImageUri;
    }

    @Override
    public void setContentUri(Uri uri) {
        mImageUri = uri;
    }

    @Override
    public String toString() {
        if (mImageFile == null) return "";

        return "File Name: " + mImageFile.getName() + "\n" +
                "File Path: " + mImageFile.getAbsolutePath() + "\n"+
                "File URI: " + mImageUri;
    }

}
