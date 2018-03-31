package com.example.mikael.mikaelhagfeldt_petshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity
{
    private FirebaseDatabase fieldDatabase;
    private DatabaseReference fieldDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fieldDatabase = FirebaseDatabase.getInstance();
        fieldDatabaseReference = fieldDatabase.getReference("Test Node");
        fieldDatabaseReference.setValue("Test Value");
    }

}
