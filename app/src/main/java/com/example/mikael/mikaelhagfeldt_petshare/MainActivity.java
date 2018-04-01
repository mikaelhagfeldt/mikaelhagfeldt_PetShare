package com.example.mikael.mikaelhagfeldt_petshare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity
{
    // Basic deklarationer

    private EditText fieldEditTextEmail;
    private EditText fieldEditTextPassword;
    private Button fieldButtonLogin;
    private Button fieldButtonCreateNewAcc;

    private FirebaseAuth fieldFirebaseAuth;
    private FirebaseAuth.AuthStateListener fieldFirebaseAuthStateListener;
    private FirebaseUser fieldFirebaseUser;

    // Ett enkelt test för att kolla att allting fungerar

    private FirebaseDatabase fieldDatabase;
    private DatabaseReference fieldDatabaseReference;

    @Override
    protected void onStart()
    {
        fieldFirebaseAuth.addAuthStateListener(fieldFirebaseAuthStateListener);
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        fieldFirebaseAuth.removeAuthStateListener(fieldFirebaseAuthStateListener);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fieldEditTextEmail = findViewById(R.id.id_editText_email);
        fieldEditTextPassword = findViewById(R.id.id_editText_password);
        fieldButtonLogin = findViewById(R.id.id_button_login);
        fieldButtonCreateNewAcc = findViewById(R.id.id_button_createNewAcc);

        fieldFirebaseAuth = FirebaseAuth.getInstance();

        // En metod som "lyssnar efter" förändringar för den nuvarande användaren

        fieldFirebaseAuthStateListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                fieldFirebaseUser = fieldFirebaseAuth.getCurrentUser();

                // Ett sätt för mig som utvecklare att hålla reda på värdet för fieldFirebaseUser

                if (fieldFirebaseUser != null)
                {
                    Toast.makeText(MainActivity.this, "FirebaseUser is not null", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "FirebaseUser is null", Toast.LENGTH_LONG).show();
                }
            }
        };

        // Enkelt test för att se att allting fungerar
        fieldDatabase = FirebaseDatabase.getInstance();
        fieldDatabaseReference = fieldDatabase.getReference("Test Node");
        fieldDatabaseReference.setValue("Test Value");
    }

}
