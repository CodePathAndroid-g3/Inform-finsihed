package com.example.inform.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.inform.Post;
import com.example.inform.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ComposeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG = "Compose Fragment";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    public static final int PICK_IMAGE = 1;
    private EditText etDescription;
    private Button btnCaptureImage;
    private ImageView ivPostImage;
    private Button btnSubmit;
    private File photoFile;
    public String photoFileName = "photo.jpg";

    private EditText etLocation;
    private EditText etStatus;
    private EditText etContact;

    Context context = this.getContext();

    Uri imageUri;

    // TODO: Rename and change types of parameters


    public ComposeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return inflater.inflate(R.layout.fragment_compose,container,false);
    }
    @Override
    public void onViewCreated(@Nullable View view,@Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        etDescription = view.findViewById(R.id.etDescription);
        btnCaptureImage =  view.findViewById(R.id.btnCaptureImage);
        ivPostImage = (ImageView) view.findViewById(R.id.ivPostImage);
        etLocation = view.findViewById(R.id.etLocation);
        etStatus = view.findViewById(R.id.etStatus);
        etContact = view.findViewById(R.id.etContact);

        btnSubmit =  view.findViewById(R.id.btnSubmit);
        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LaunchGallery();
            }
        });

        //queryPosts();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = etDescription.getText().toString();
                String status = etStatus.getText().toString();
                String location = etLocation.getText().toString();
                String contact = etContact.getText().toString();
                if(description.isEmpty()){
                    Toast.makeText(getContext(),"Description cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
//
//                if(photoFile == null || ivPostImage.getDrawable() == null)
//                {
//                    Toast.makeText(getContext(),"there is no image!",Toast.LENGTH_SHORT).show();
//                    return;
//                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost( description, status, contact, location, currentUser, photoFile);
            }
        });
    }


    private void LaunchGallery() {
        Intent gallery = new Intent();

        photoFile = getPhotoFileUri(photoFileName);
        Uri fileProvider = FileProvider.getUriForFile(getContext(),"com.example.inform",photoFile);
        gallery.putExtra(MediaStore.EXTRA_OUTPUT,fileProvider);

        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);

        if (gallery.resolveActivity(this.getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(Intent.createChooser(gallery, "select image"), PICK_IMAGE);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(),imageUri );
                ivPostImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /*
    private void launchCamera() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && resultData != null) {
            Uri selectedImage = resultData.getData();
            ivPostImage.setImageURI(selectedImage);
        }
    }
    */
    //----------added 4/29
    /*
    private void launchCamera() {
        Intent intent =  new Intent(Intent.ACTION_GET_CONTENT);
        photoFile = getPhotoFileUrl(photoFileName);
        Uri fileProvider = FileProvider.getUriForFile(getContext(),"com.example.inform.fileProvider",photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,fileProvider);
        if(intent.resolveActivity(getContext().getPackageManager()) != null){
            startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)  {
            if(resultCode == RESULT_OK) {
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                ivPostImage.setImageBitmap(takenImage);
            }else{
                Toast.makeText(getContext(), "Picture wasn't taken", Toast.LENGTH_SHORT).show();
            }
        }
    }

     */
/*
    public void LaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(context, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }
    */



    private File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),TAG);
        if(!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG,"failed to create directory");
        }
        return new File(mediaStorageDir.getPath() + File.separator+ fileName);


    }

    private void savePost(String description, String status,String contact,String location, ParseUser currentUser, File photoFile) {
        Post post = new Post();
        post.setDescription(description);
        post.setStatus(status);
        post.setContact(contact);
        post.setLocation(location);

        if(photoFile != null || ivPostImage.getDrawable() != null) {
            post.setImage(new ParseFile(photoFile));
            Log.e(TAG, "image here");
        }
        post.setUser(currentUser);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.e(TAG,"Error while saving");
                    Toast.makeText(getContext(),"Error while saving!",Toast.LENGTH_SHORT).show();

                }
                Log.i(TAG,"Post save was successful!");
                etDescription.setText("");
                ivPostImage.setImageResource(0);
            }
        });
    }


}
