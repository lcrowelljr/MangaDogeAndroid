package com.seohajgod.mangadoge.fragmentstuff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.seohajgod.mangadoge.MainActivity;
import com.seohajgod.mangadoge.R;

public class LoginFragment extends Fragment {

    FirebaseAuth auth;
    EditText emailEditText;
    EditText passwordEditText;
    TextView registerTextView;
    Button loginButton;

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        auth = FirebaseAuth.getInstance();
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        registerTextView = view.findViewById(R.id.registerTextView);
        loginButton = view.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(getContext(), "Email or password are incorrect", Toast.LENGTH_SHORT).show();
            }

            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                Toast.makeText(getContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();
                MainActivity.isLoggedIn = true;
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MangaListFragment())
                        .addToBackStack(null)
                        .commit();
            }).addOnFailureListener(exception -> {
                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });

        registerTextView.setOnClickListener(x -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RegisterFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
