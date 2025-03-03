package com.seohajgod.mangadoge.fragmentstuff;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.seohajgod.mangadoge.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MangaChapterFragment extends Fragment {

    ImageView pageView;
    TextView chapterTitle;
    String chapterNumber;
    ArrayList<String> pages;
    int index = 0;

    public static MangaChapterFragment newInstance(){
        return new MangaChapterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view = inflater.inflate(R.layout.manga_chapter_fragment, container, false);
        pageView = view.findViewById(R.id.chapterImageView);
//        pageView.setOnClickListener(v -> {
//            if(index < pages.size() - 1) {
//                index++;
//                showPage(index);
//            }
//        });
        pageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    float x = event.getX();
                    int width = pageView.getWidth();

                    if(x < width / 2){
                        if(index > 0) {
                            index--;
                            showPage(index);
                        }
                    } else {
                        if(index < pages.size() - 1) {
                            index++;
                            showPage(index);
                        }
                    }
                    return true;
                }
                return false;
            }
        });


        Bundle bundle = getArguments();
        if(bundle != null) {
            chapterNumber = bundle.getString("chapterNumber");
            Log.i("MangaChapterFragment", chapterNumber);
            chapterTitle = view.findViewById(R.id.mangaTitleTextView);
            chapterTitle.setText(chapterNumber);
        }
        getMangaPages(chapterNumber);
        //showPage(index);

        return view;
    }

    private void getMangaPages(String chapter){
        pages = new ArrayList<>();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Manga/" + chapter);
        storageReference.listAll().addOnSuccessListener(listResult -> {
            for(StorageReference item : listResult.getItems()){
                pages.add(item.getPath());
                Log.i("MangaChapterFragment/getMangaPages", "Manga/" + chapter + "/" + item.getName());
            }
            showPage(index);
        }).addOnFailureListener(exception -> {
            Log.i("MangaChapterFragment", exception.getMessage());
        });
    }

    private void showPage(int index){
        StorageReference path = FirebaseStorage.getInstance().getReference().child(pages.get(index));
        Log.i("MangaChapterFragment", path.getPath());
        path.getDownloadUrl().addOnSuccessListener(uri -> {
            Log.i("MangaChapterFragment", "Got download URL for picture: " + uri);
            Picasso.get().load(uri).into(pageView);
        });


    }
}
