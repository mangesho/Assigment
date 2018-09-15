package com.assigment.view;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.assigment.App;
import com.assigment.R;
import com.assigment.adapters.UserAdapter;
import com.assigment.di.component.DaggerActivityComponent;
import com.assigment.di.module.ActivityModule;
import com.assigment.models.User;
import com.assigment.presenter.BasePresenterImpl;
import com.assigment.presenter.ContractUserActionListener;
import com.assigment.presenter.ImagePresenter;
import com.assigment.presenter.Injection;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BaseView,UserAdapter.UserActions {

    final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int SELECT_PICTURE = 2;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.user_recyclerview)
    RecyclerView userRecyclerview;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Inject
    BasePresenterImpl basePresenterImpl;

    private Dialog addUserDialog,editdeleteUserDialog;
    private List<User> userList = new ArrayList<>();
    private UserAdapter userAdapter;
    private ContractUserActionListener mContractUserActionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaggerActivityComponent.builder()
                .apiComponent(((App) getApplicationContext()).getNetComponent())
                .activityModule(new ActivityModule(this))
                .build().inject(this);

        try{
            init();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void init() {
        initToolbar();
        initVariables();
        getUserList();
    }

    private void getUserList() {
        basePresenterImpl.loadUsers();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
    }

    private void initVariables() {
        userAdapter = new UserAdapter(MainActivity.this,userList);
        userRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        userRecyclerview.setAdapter(userAdapter);
    }

    @Override
    public void showProgressbar() {
        if(progressBar != null){
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void closeProgressbar() {
        if(progressBar != null){
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showusers(List<User> userList) {
        this.userList.clear();
        this.userList.addAll(userList);
        userAdapter.notifyDataSetChanged();

        basePresenterImpl.insertAllUsers(this.userList);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(MainActivity.this,"Server Error",Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateList() {
        userList.clear();
        userList.addAll(basePresenterImpl.getAllUsers());
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_user) {
            openAddUserDialog(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAddUserDialog(final User user) {
        addUserDialog = new Dialog(MainActivity.this);
        addUserDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_add_user,null,false);

        final EditText firstNameEdit = dialogView.findViewById(R.id.first_name_edit);
        final EditText lastNameEdit = dialogView.findViewById(R.id.last_name_edit);
        final ImageView profile_image = dialogView.findViewById(R.id.profile_image);
        final Button submitBtn = dialogView.findViewById(R.id.submit);

        mContractUserActionListener =  new ImagePresenter(new ContractView() {
            @Override
            public void showImagePreview(@NonNull Uri uri) {
                Glide.with(MainActivity.this)
                        .load(uri)
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.ic_launcher_background)
                                .centerCrop())
                        .into(profile_image);
                submitBtn.setTag(uri);
            }

            @Override
            public void showImageInfo(String infoSting) {

            }

            @Override
            public void setUserActionListener(ContractUserActionListener userActionListener) {

            }
        },
        Injection.provideImageFile());

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preparePhotoSelection();
            }
        });

        if(user != null){
            firstNameEdit.setText(user.getFirstName());
            lastNameEdit.setText(user.getLastName());
            submitBtn.setText(getResources().getText(R.string.save));
            Glide.with(MainActivity.this)
                    .load(user.getAvatar())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_launcher_background)
                            .centerCrop())
                    .into(profile_image);
            submitBtn.setTag(user.getAvatar());
        }else{
            submitBtn.setText(getResources().getText(R.string.add));
        }


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserDialog.dismiss();
                String url = "";
                if(submitBtn.getTag() != null){
                    url = submitBtn.getTag().toString();
                }

                if(user == null) {
                    User newUser = new User();
                    newUser.setFirstName(firstNameEdit.getText().toString());
                    newUser.setLastName(lastNameEdit.getText().toString());
                    newUser.setAvatar(url);
                    basePresenterImpl.addUser(newUser);
                }else{
                    user.setFirstName(firstNameEdit.getText().toString());
                    user.setLastName(lastNameEdit.getText().toString());
                    user.setAvatar(url);
                    basePresenterImpl.updateUser(user);
                }

            }
        });

        addUserDialog.setContentView(dialogView);
        addUserDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addUserDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        addUserDialog.setCancelable(true);
        addUserDialog.setCanceledOnTouchOutside(true);
        addUserDialog.show();
    }

    @Override
    public void onUserClicked(User user) {
        openEditDeleteUserDialog(user);
    }

    private void openEditDeleteUserDialog(final User user) {
        editdeleteUserDialog = new Dialog(MainActivity.this);
        editdeleteUserDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_options,null,false);

        final TextView editUser = dialogView.findViewById(R.id.edit_user);
        final TextView deleteUser = dialogView.findViewById(R.id.delete_user);


        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editdeleteUserDialog.dismiss();
                openAddUserDialog(user);
            }
        });

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basePresenterImpl.deleteUser(user);
                editdeleteUserDialog.dismiss();
            }
        });


        editdeleteUserDialog.setContentView(dialogView);
        editdeleteUserDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        editdeleteUserDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        editdeleteUserDialog.setCancelable(true);
        editdeleteUserDialog.setCanceledOnTouchOutside(true);
        editdeleteUserDialog.show();
    }

    public void preparePhotoSelection() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
               // Toast.makeText(MainActivity.this, "Please grant permissions to change profile photo", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                showImagePicker();
            }
        } else if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            showImagePicker();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    showImagePicker();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                  //  Toast.makeText(MainActivity.this, "Permissions Denied to access Photos", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public void showImagePicker() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            try {
                mContractUserActionListener.loadImage(MainActivity.this, selectedImageUri);
                mContractUserActionListener.loadImageInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
