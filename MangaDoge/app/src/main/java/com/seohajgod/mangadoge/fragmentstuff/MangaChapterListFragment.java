package com.seohajgod.mangadoge.fragmentstuff;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.seohajgod.mangadoge.R;

import java.util.ArrayList;

public class MangaChapterListFragment extends ListFragment {

    TextView titleTextView;
    String mangaName;
    ArrayList<String> mangaChapters;

    public static MangaChapterListFragment newInstance(){
        return new MangaChapterListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view = inflater.inflate(R.layout.manga_chapter_list_fragment, container, false);
        Log.i("MangaChapterListFragment", "onCreateView");
        titleTextView = view.findViewById(R.id.mangaTitleTextView);
        Bundle bundle = getArguments();
        if(bundle != null) {
            mangaName = bundle.getString("mangaName");
            titleTextView.setText(mangaName);
        }
        getMangaChapters();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){//onCreateView goes first, put stuff there
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        MangaChapterFragment fragment = MangaChapterFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString("chapterNumber", mangaName + "/" + l.getItemAtPosition(position).toString());
        fragment.setArguments(bundle);
        Log.i("MangaChapterListFragment", l.getItemAtPosition(position).toString());

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
    private void getMangaChapters(){
        mangaChapters = new ArrayList<>();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Manga/" + mangaName);
        storageReference.listAll()
                .addOnSuccessListener(
                        listResult -> {
                            for(StorageReference item : listResult.getPrefixes()){
                                Log.i("MangaChapterListFragment", item.getName());
                                mangaChapters.add(item.getName());

                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mangaChapters);
                            setListAdapter(adapter);
                        })
                .addOnFailureListener(exception -> {
                    Log.i("MangaChapterListFragment", exception.getMessage());
                });
    }
}
