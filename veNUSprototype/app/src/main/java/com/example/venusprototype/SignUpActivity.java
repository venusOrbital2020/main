package com.example.venusprototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

public class SignUpActivity extends AppCompatActivity {
    public EditText emailSignUpText;
    public EditText passwordSignUpText;
    public EditText confirmPwdText;
    Button signUpBtn;
    TextView loginTextView;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailSignUpText = findViewById(R.id.emailSignUpText);
        passwordSignUpText = findViewById(R.id.passwordSignUpText);
        confirmPwdText = findViewById(R.id.confirmPwdText);
        signUpBtn = findViewById(R.id.signUpBtn);
        loginTextView = findViewById(R.id.loginTextView);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailSignUpText.getText().toString();
                String password = passwordSignUpText.getText().toString();
                String confirmPwd = confirmPwdText.getText().toString();
                if ((email.isEmpty() && password.isEmpty()) && confirmPwd.isEmpty()) {
                    Toast.makeText(SignUpActivity.this,
                            "Please fill up your details",
                            Toast.LENGTH_SHORT).show();
                    emailSignUpText.requestFocus();
                    emailSignUpText.setError("Required");
                    passwordSignUpText.requestFocus();
                    passwordSignUpText.setError("Required");
                    confirmPwdText.requestFocus();
                    confirmPwdText.setError("Required");
                } else if(email.isEmpty()) {
                    emailSignUpText.setError("Please enter an email address");
                    emailSignUpText.requestFocus();
                } else if (password.isEmpty()) {
                    passwordSignUpText.setError("Please enter a password");
                    passwordSignUpText.requestFocus();
                } else if (confirmPwd.isEmpty()) {
                    confirmPwdText.setError("Please confirm your password");
                    confirmPwdText.requestFocus();
                } else if (!(email.isEmpty() && password.isEmpty()) && !confirmPwd.isEmpty()) {
                    if (password.equals(confirmPwd)) {
                        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(SignUpActivity.this,
                                                    "SignUp unsuccessful, please contact help desk",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                                        }
                                    }
                                });
                    } else {
                        passwordSignUpText.setError("Password is different");
                        confirmPwdText.setError("Password is different");
                        passwordSignUpText.requestFocus();
                        confirmPwdText.requestFocus();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this,
                            "Error Occurred! Try again later! ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToLogin = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(moveToLogin);
            }

        });
    }


}
