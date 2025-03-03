package com.seohajgod.mangadoge.fragmentstuff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.seohajgod.mangadoge.R;

public class ProfileSideFragment extends Fragment {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    ImageView profileImageView;
    TextView profileEmailTextView;
    TextView profileUploadTextView;

    public static ProfileSideFragment newInstance(){
        return new ProfileSideFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.profile_side_fragment, container, false);

        profileImageView = view.findViewById(R.id.profileImageView);
        profileEmailTextView = view.findViewById(R.id.profileEmailTextView);
        profileUploadTextView = view.findViewById(R.id.profileUploadTextView);

        profileEmailTextView.setText("Email: " + auth.getCurrentUser().getEmail());

        profileUploadTextView.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Upload your own chapters here", Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, UploadFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
