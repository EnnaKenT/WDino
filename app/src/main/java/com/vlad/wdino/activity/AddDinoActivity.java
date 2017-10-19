package com.vlad.wdino.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vlad.wdino.R;
import com.vlad.wdino.WDinoApp;
import com.vlad.wdino.api.model.playload.CreateImagePlayload;
import com.vlad.wdino.api.model.playload.createDino.CreateDinoPlayload;
import com.vlad.wdino.api.model.playload.createDino.FieldDinoAbout;
import com.vlad.wdino.api.model.playload.createDino.FieldDinoBirthDate;
import com.vlad.wdino.api.model.playload.createDino.FieldDinoColor;
import com.vlad.wdino.api.model.playload.createDino.FieldDitoImage;
import com.vlad.wdino.api.model.playload.createDino.Und;
import com.vlad.wdino.api.model.playload.createDino.Und_;
import com.vlad.wdino.api.model.playload.createDino.Und__;
import com.vlad.wdino.api.model.playload.createDino.Und___;
import com.vlad.wdino.api.model.playload.createDino.Value;
import com.vlad.wdino.api.model.response.CreateDinoResponse;
import com.vlad.wdino.api.model.response.CreateImageResponse;
import com.vlad.wdino.api.model.response.login.LoginResponse;
import com.vlad.wdino.background.DefaultBackgroundCallback;
import com.vlad.wdino.manager.RetrofitManager;
import com.vlad.wdino.utils.Constants;
import com.vlad.wdino.utils.InternetUtil;
import com.vlad.wdino.utils.SharedPreferenceHelper;
import com.vlad.wdino.view.dialog.RegisterUserError;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddDinoActivity extends AppCompatActivity {

    private ImageButton mUploadImageButton;
    private Spinner mColorSpinner;
    private Button mOkButton;
    private EditText mNameText;
    private EditText mAboutText;
    private TextView mUploadDescriptionText;
    private DatePicker mDatePicker;
    private Bitmap mBitmap;
    private Uri selectedImageURI;
    private String name;
    private String about;
    private String color;
    private int day;
    private int month;
    private int year;

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dino);

        restoreBitmap();
        initWidgets();
        restoreWidgets(savedInstanceState);
    }

    private void restoreBitmap() {
        mBitmap = (Bitmap) getLastCustomNonConfigurationInstance();
    }

    private void restoreWidgets(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.NAME_FIELD)) {
            mNameText.setText(savedInstanceState.getString(Constants.NAME_FIELD));
            mAboutText.setText(savedInstanceState.getString(Constants.ABOUT_FIELD));
            mColorSpinner.setSelection(savedInstanceState.getInt(Constants.COLOR_SPINNER));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Constants.NAME_FIELD, String.valueOf(mNameText.getText()));
        outState.putString(Constants.ABOUT_FIELD, String.valueOf(mAboutText.getText()));
        outState.putInt(Constants.COLOR_SPINNER, mColorSpinner.getSelectedItemPosition());
    }

    private void initWidgets() {
        mColorSpinner = (Spinner) findViewById(R.id.input_color);
        mUploadImageButton = (ImageButton) findViewById(R.id.upload_button);
        mUploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        mOkButton = (Button) findViewById(R.id.ok_button);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okButtonPressed();
            }
        });

        mNameText = (EditText) findViewById(R.id.input_name);
        mAboutText = (EditText) findViewById(R.id.input_about);
        mUploadDescriptionText = (TextView) findViewById(R.id.upload_picture_description);
        if (mBitmap != null) {
            mUploadDescriptionText.setText(getString(R.string.chosen_the_dino_profile_picture));
        }
        mDatePicker = (DatePicker) findViewById(R.id.datePicker);
    }

    private void choosePicture() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        mBitmap = null;
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    selectedImageURI = imageReturnedIntent.getData();
                    try {
                        mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageURI);
                        mUploadDescriptionText.setText(getString(R.string.chosen_the_dino_profile_picture));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    private void sendNewDinoImage() {
        CreateImagePlayload imagePlayload = createNewDinoImage();

        LoginResponse user = SharedPreferenceHelper.getObject(this, SharedPreferenceHelper.KEY_LOGIN_USER, LoginResponse.class);

        RetrofitManager.getInstance().createImage(imagePlayload, user, new DefaultBackgroundCallback<CreateImageResponse>() {
            @Override
            public void doOnSuccess(CreateImageResponse result) {
                sendNewDino();
            }
        });
    }

    private void sendNewDino() {
        CreateDinoPlayload dino = createNewDino();

        LoginResponse user = SharedPreferenceHelper.getObject(this, SharedPreferenceHelper.KEY_LOGIN_USER, LoginResponse.class);

        RetrofitManager.getInstance().createDino(dino, user, new DefaultBackgroundCallback<CreateDinoResponse>() {
            @Override
            public void doOnSuccess(CreateDinoResponse result) {
                Toast.makeText(WDinoApp.getCurrentActivity().getApplicationContext(), "Dino successfully added. Just refresh list to see it.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startDinosActivity() {
        Intent intent = new Intent(this, DinosViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private CreateDinoPlayload createNewDino() {
        //add color
        FieldDinoColor dinoColor = new FieldDinoColor();
        Und und = new Und();
        und.setTid(color);
        dinoColor.setUnd(und);

        //add about
        FieldDinoAbout dinoAbout = new FieldDinoAbout();
        Und_ und_ = new Und_();
        und_.setValue(about);
        List<Und_> listUnd_ = new ArrayList<>();
        listUnd_.add(und_);
        dinoAbout.setUnd(listUnd_);

        // add birthday
        FieldDinoBirthDate dinoBD = new FieldDinoBirthDate();
        List<Und__> listUnd__ = new ArrayList<>();
        Und__ und__ = new Und__();
        Value value = new Value();
        value.setDay(Integer.toString(day));
        value.setMonth(Integer.toString(month));
        value.setYear(Integer.toString(year));
        und__.setValue(value);
        listUnd__.add(und__);
        dinoBD.setUnd(listUnd__);

        String fid = SharedPreferenceHelper.getString(this, SharedPreferenceHelper.KEY_FID, getString(R.string.no_internet_connection));
        FieldDitoImage dinoImage = new FieldDitoImage();
        List<Und___> listUnd___ = new ArrayList<>();
        Und___ und___ = new Und___();
        und___.setFid(fid);
        listUnd___.add(und___);
        dinoImage.setUnd(listUnd___);

        CreateDinoPlayload dino = new CreateDinoPlayload();
        dino.setTitle(name);
        dino.setName(Constants.BERDNIKOV);
        dino.setStatus("1");
        dino.setType("dino");
        dino.setFieldDinoColor(dinoColor);
        dino.setFieldDinoAbout(dinoAbout);
        dino.setFieldDinoColor(dinoColor);
        dino.setFieldDinoBirthDate(dinoBD);
        dino.setFieldDitoImage(dinoImage);

        return dino;
    }

    private CreateImagePlayload createNewDinoImage() {
        // get path
        String path = getFilePath();

        File file = new File(path);

        // get file size (bytes)
        long bytes = file.length();

        // name and format of image
        String[] nameAndFormat = file.getName().split("\\.");

        //encode image to base64
        String encodedString = encodeTo64(file);

        CreateImagePlayload imagePlayload = new CreateImagePlayload();
        imagePlayload.setFile(encodedString);
        imagePlayload.setFilemime("image/" + nameAndFormat[1]);
        imagePlayload.setFilename(nameAndFormat[0]);
        imagePlayload.setFilesize(Long.toString(bytes));
        imagePlayload.setTargetUri("pictures/" + file.getPath());

        return imagePlayload;
    }

    private String getFilePath() {
        String path = null;
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = this.getContentResolver().query(selectedImageURI, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return path;
    }

    private String encodeTo64(File file) {
        InputStream inputStream = null;//You can get an inputStream using any IO API
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] bytes1;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes1 = output.toByteArray();
        String encodedString = Base64.encodeToString(bytes1, Base64.DEFAULT);

        return encodedString;
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mBitmap;
    }

    private void okButtonPressed() {
        int selectedColor = mColorSpinner.getSelectedItemPosition();

        String color = Constants.COLORS[selectedColor];
        int day = mDatePicker.getDayOfMonth();
        int month = mDatePicker.getMonth() + 1;
        int year = mDatePicker.getYear();

        // Reset errors.
        mAboutText.setError(null);
        mNameText.setError(null);

        // Store values.
        String about = mAboutText.getText().toString();
        String uploadDescription = mUploadDescriptionText.getText().toString();
        String name = mNameText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (uploadDescription.equals(getString(R.string.choose_the_dino_profile_picture))) {
            cancel = true;
            focusView = mUploadDescriptionText;
            showCustomDialog(getString(R.string.choose_the_dino_profile_picture));
        }

        if (TextUtils.isEmpty(about)) {
            mAboutText.setError(getString(R.string.error_field_required));
            focusView = mAboutText;
            cancel = true;
        }

        if (TextUtils.isEmpty(name)) {
            mNameText.setError(getString(R.string.error_field_required));
            focusView = mNameText;
            cancel = true;
        }

        if (cancel) {
            // There was an error; focus the first
            // form field with an error.
            if (focusView != mUploadDescriptionText) {
                focusView.requestFocus();
            }
        } else {

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            if (InternetUtil.isInternetTurnOn(this)) {
                this.name = name;
                this.about = about;
                this.color = color;
                this.day = day;
                this.month = month;
                this.year = year;
                sendNewDinoImage();
                startDinosActivity();
            } else {
                showCustomDialog(getString(R.string.no_internet_connection));
            }
        }
    }

    private void showCustomDialog(final String string) {
        new RegisterUserError(AddDinoActivity.this, string, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        })
                .showDialog();
    }
}
