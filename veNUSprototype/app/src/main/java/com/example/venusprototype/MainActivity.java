package com.example.venusprototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public EditText emailLoginText;
    public EditText passwordLoginText;
    Button loginBtn;
    TextView signUpTextView;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailLoginText = findViewById(R.id.emailLoginText);
        passwordLoginText = findViewById(R.id.passwordLoginText);
        loginBtn = findViewById(R.id.loginBtn);
        signUpTextView = findViewById(R.id.signUpTextView);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent moveToHome = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(moveToHome);
                } else {
                    Toast.makeText(MainActivity.this, "Please Login to continue", Toast.LENGTH_SHORT).show();
                }
            }
        };

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailLoginText.getText().toString();
                String password = passwordLoginText.getText().toString();
                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter email address and password", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    emailLoginText.setError("Please enter email address");
                    emailLoginText.requestFocus();
                } else if (password.isEmpty()) {
                    passwordLoginText.setError("Please enter password");
                    passwordLoginText.requestFocus();
                } else if (!(email.isEmpty() && password.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Wrong email/password. Please try again", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Intent moveToHome = new Intent(MainActivity.this, HomeActivity.class);
                                        startActivity(moveToHome);
                                    }
                                }
                            });
                } else {
                    Toast.makeText(MainActivity.this, "Error Occurred! Try again later!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToSignUp = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(moveToSignUp);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
