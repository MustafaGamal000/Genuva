package com.example.genuvaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
EditText restPasswordTxt;
Button restBtn;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mAuth=FirebaseAuth.getInstance();
        restPasswordTxt=findViewById(R.id.restPasswordTXT);
        restBtn=findViewById(R.id.restBtn);
        restBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=restPasswordTxt.getText().toString().trim();
                if(email.isEmpty()){
                    restPasswordTxt.setError("Please enter your email");
                    restPasswordTxt.requestFocus();
                    return;
                }
                else{
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgetPassword.this, "Please check your account...", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgetPassword.this, Login.class));
                            }
                            else{
                                String message=task.getException().getMessage();
                                Toast.makeText(ForgetPassword.this, "Error occured: "+message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
