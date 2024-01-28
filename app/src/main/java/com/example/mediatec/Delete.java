package com.example.mediatec;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mediatec.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Delete extends AppCompatActivity {
    Button btnDelete;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_window);

        EditText name = (EditText) findViewById(R.id.deleteId);
        //String text = name.getText().toString();
        btnDelete = (Button) findViewById(R.id.button);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        Query nameQuerry= ref.child("Users").orderByChild("name").equalTo(name.getText().toString());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Delete.this, name.getText().toString(),Toast.LENGTH_LONG).show();
                nameQuerry.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(Delete.this,"Error",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}