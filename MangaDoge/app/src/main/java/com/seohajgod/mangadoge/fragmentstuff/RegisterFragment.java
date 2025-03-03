package com.seohajgod.mangadoge.fragmentstuff;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.seohajgod.mangadoge.R;

public class RegisterFragment extends Fragment {

    FirebaseAuth auth;
    EditText emailEditText;
    EditText confirmEmailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    Button registerButton;

    public static RegisterFragment newInstance(){
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.register_fragment, container, false);

        auth = FirebaseAuth.getInstance();
        emailEditText = view.findViewById(R.id.emailRegisterEditText);
        confirmEmailEditText = view.findViewById(R.id.emailRegisterConfirmEditText);
        passwordEditText = view.findViewById(R.id.passwordRegisterEditText);
        confirmPasswordEditText = view.findViewById(R.id.passwordRegisterConfirmEditText);

        registerButton = view.findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> {
            if(!emailEditText.getText().toString().equals(confirmEmailEditText.getText().toString())){
                Toast.makeText(getContext(), "Emails do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())){
                Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnSuccessListener(authResult -> {
                        Toast.makeText(getContext(), "User created successfully", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new LoginFragment())
                                .addToBackStack(null)
                                .commit();
                    })
                    .addOnFailureListener(exception -> {
                        Log.i("RegisterFragment", "Error creating user");
                        exception.printStackTrace();
                    });
        });


        return view;
    }
}
