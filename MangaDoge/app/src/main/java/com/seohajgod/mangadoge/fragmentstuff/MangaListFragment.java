package com.seohajgod.mangadoge.fragmentstuff;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.seohajgod.mangadoge.R;

import java.util.ArrayList;

public class MangaListFragment extends ListFragment {
    private static final String TAG = "MangaListFragment";

    public static MangaListFragment newInstance(){
        return new MangaListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.manga_list_fragment, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ArrayList<String> stuff = new ArrayList<>();


        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Manga");
        storageReference.listAll()
                .addOnSuccessListener(listResult -> {
//                    for(StorageReference item : listResult.getItems()){
//                        Log.i(TAG, item.getName());
//                        //stuff.add(item.getName());
//                    }
                    for(StorageReference item : listResult.getPrefixes()){
                        Log.i(TAG, item.getName());
                        stuff.add(item.getName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, stuff);
                    setListAdapter(adapter);

                })
                .addOnFailureListener(exception -> {
                    Log.i(TAG, exception.getMessage());
                    Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                });



//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, stuff);
//        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){

        MangaChapterListFragment fragment = MangaChapterListFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString("mangaName", l.getItemAtPosition(position).toString());
        fragment.setArguments(bundle);
        //Log.i(TAG, l.getItemAtPosition(position).toString());

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
