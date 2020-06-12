package com.example.firebaseapproval;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email,pass;
    Button signin,signup,approve;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email_approve);
        pass = findViewById(R.id.password);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        approve = findViewById(R.id.approve);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), signup.class);
                startActivity(intent);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String email_ = email.getText().toString().trim();
                 String pass_ = pass.getText().toString().trim();

                 mAuth.signInWithEmailAndPassword(email_,pass_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {

                         if (task.isSuccessful())
                         {
                             Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                         }else{
                             Toast.makeText(getApplicationContext(),"Invalid username/password",Toast.LENGTH_SHORT).show();
                         }

                     }
                 });


            }
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),approval.class);
                startActivity(intent);
            }
        });
    }
}
