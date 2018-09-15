package com.assigment.presenter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.assigment.models.ImageFile;
import com.assigment.view.ContractView;

import java.io.File;
import java.io.IOException;

public class ImagePresenter implements ContractUserActionListener {

    @NonNull
    private ImageFile mImageFile;

    //Hold reference to View contract to update UI
    @NonNull private ContractView mImageViewer;

    public ImagePresenter(ContractView imageViewer, ImageFile imageFile) {
        mImageViewer = imageViewer;
        mImageFile = imageFile;

        mImageViewer.setUserActionListener(this);
    }

    //Fills-in model (image) details
    //Sends out request to View to show image in UI
    @Override
    public void loadImage(Context context, Uri uri) throws IOException {
        mImageFile.create(context, uri.toString());
        mImageFile.setContentUri(uri);
        mImageViewer.showImagePreview(uri);
    }

    @Override
    public void loadImageInfo() {
        String infoString = mImageFile.toString();

        mImageViewer.showImageInfo(infoString);
    }


}
