package com.assigment.presenter;

import com.assigment.models.ImageFile;
import com.assigment.models.ImageFileImpl;

public class Injection {

    public static ImageFile provideImageFile() {
        return new ImageFileImpl();
    }
}
