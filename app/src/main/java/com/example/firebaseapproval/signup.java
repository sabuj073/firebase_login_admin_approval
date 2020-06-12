package com.example.firebaseapproval;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    EditText email,pass;
    Button signin;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
         myRef = database.getReference("approval");

        email = findViewById(R.id.email_approve);
        pass = findViewById(R.id.password);
        signin = findViewById(R.id.signup);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_ = email.getText().toString().trim();
                String pass_ = pass.getText().toString().trim();
                storedata data = new storedata(email_,pass_);
                String key = myRef.push().getKey();

                myRef.child(key).setValue(data);
                Toast.makeText(getApplicationContext(),"Data Stored",Toast.LENGTH_SHORT).show();
                email.setText("");
                pass.setText("");

            }
        });

    }
}
