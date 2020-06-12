package com.example.firebaseapproval;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;




public class CustomAdapter extends ArrayAdapter<storedata> {
    private Activity context;
    private  List<storedata> storedataList;

    public CustomAdapter(Activity context, List<storedata> storedataList) {
        super(context, R.layout.sample_layout, storedataList);
        this.context = context;
        this.storedataList = storedataList;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        final View view  = layoutInflater.inflate(R.layout.sample_layout,null,true);
        final storedata Storedata =  storedataList.get(position);
        TextView t1 = view.findViewById(R.id.email_approve);
        TextView t2 = view.findViewById(R.id.pass_approve);
        Button button = view.findViewById(R.id.approve);

        t1.setText(String.format("Email %s", Storedata.getEmail()));
        t2.setText(String.format("Pass %s", Storedata.getPass()));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(Storedata.getEmail(), Storedata.getPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context,"Approved Sucessfully",Toast.LENGTH_SHORT).show();
                        }else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(context,"Already Registered",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("approval").orderByChild("email").equalTo(Storedata.getEmail());

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("onCancelled", String.valueOf(databaseError.toException()));
                    }
                });
            }
        });
        return view;
    }
}
