package com.assigment.presenter;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;

public interface ContractUserActionListener {

    //An action taken by user to load a image from gallery into ImageView widget
    void loadImage(Context context, Uri uri) throws IOException;

    //User initiates action to display image file information in textView UI
    void loadImageInfo();

}
