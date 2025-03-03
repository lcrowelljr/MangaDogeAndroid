package com.seohajgod.mangadoge.fragmentstuff;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.seohajgod.mangadoge.R;

import java.util.ArrayList;
import java.util.Arrays;

public class UploadFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    EditText mangaTitleInput;
    EditText mangaChapterInput;
    ImageView imageView;
    Button selectPagesButton;
    Button uploadPagesButton;

    ArrayList<Uri> imageUris = new ArrayList<>();

    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    public static UploadFragment newInstance(){
        return new UploadFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.upload_fragment, container, false);

        mangaTitleInput = view.findViewById(R.id.mangaTitleInput);
        mangaChapterInput = view.findViewById(R.id.mangaChapterInput);
        imageView = view.findViewById(R.id.imageView);
        selectPagesButton = view.findViewById(R.id.selectPagesButton);
        uploadPagesButton = view.findViewById(R.id.uploadPagesButton);

        imageUris.clear();

        selectPagesButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });

        uploadPagesButton.setOnClickListener(v -> {
            if (imageUris.isEmpty()) {
                Toast.makeText(getContext(), "No images selected", Toast.LENGTH_SHORT).show();
            }

            String mangaTitle = mangaTitleInput.getText().toString();
            if (mangaTitle.isEmpty()) {
                Toast.makeText(getContext(), "Enter a manga title", Toast.LENGTH_SHORT).show();
                return;
            }

            String mangaChapter = mangaChapterInput.getText().toString();
            if (mangaChapter.isEmpty()) {
                Toast.makeText(getContext(), "Enter a manga chapter", Toast.LENGTH_SHORT).show();
                return;
            }

            int i = 0;
            for (Uri imageUri : imageUris) {
                i++;
                StorageReference storageReference = firebaseStorage.getReference("Manga/" + mangaTitle + "/" + "Chapter " + mangaChapter + "/" + mangaTitle + "_Chapter"  + mangaChapter + "_Page"+ i + ".jpg");
                Log.i("UploadFragment", storageReference.getPath());
                storageReference.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> {})
                        .addOnFailureListener(e -> Toast.makeText(getContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("UploadFragment", "onActivityResult");
        Log.i("UploadFragment", "Request code: " + requestCode);
        Log.i("UploadFragment", "Result code: " + resultCode);
        Log.i("UploadFragment", "Data: " + data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
        //if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null){
            Log.i("UploadFragment", "Image selected");
            imageUris.add(data.getData());
            imageView.setImageURI(imageUris.get(imageUris.size() - 1));
            for(Uri uri : imageUris){
                Log.i("UploadFragment", uri.toString());
            }
        }
    }

}
