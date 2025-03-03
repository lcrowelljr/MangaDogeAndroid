package com.seohajgod.mangadoge;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.seohajgod.mangadoge.fragmentstuff.LoginFragment;
import com.seohajgod.mangadoge.fragmentstuff.MangaListFragment;
import com.seohajgod.mangadoge.fragmentstuff.ProfileSideFragment;

public class MainActivity extends AppCompatActivity {

    public static boolean isLoggedIn = false;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, LoginFragment.newInstance())
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(isLoggedIn){
            if(item.getItemId() == R.id.profileButton){
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, ProfileSideFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                return true;
            }
            if(item.getItemId() == R.id.refreshButton){
                Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, MangaListFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                return true;
            }
        } else {
            Toast.makeText(this, "You must be logged in to use this feature.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}