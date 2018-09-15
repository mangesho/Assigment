package com.assigment.models;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.IOException;

public interface ImageFile {
    void create(Context context);

    void create(Context context, String filePath) throws IOException;


    String getPath();

    File getFile();

    void setFile(File file);

    //Content uri of file
    Uri getContentUri();
    void setContentUri(Uri uri);
}
