package com.example.mikael.mikaelhagfeldt_petshare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

    /*
        Kopplar FirebaseAuthStateListener till FirebaseAuth när applikationen startar, och tar bort
        den då applikationen avslutas. För att undvika framtida problem.
     */

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

        /*
            Logik för "Login" knappen
         */

        fieldButtonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (TextUtils.isEmpty(fieldEditTextEmail.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Error. Email and password has empty fields.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String strEmail = fieldEditTextEmail.getText().toString();
                    String strPassword = fieldEditTextPassword.getText().toString();
                    loginMethod(strEmail, strPassword);
                }
            }
        });
    }

    public void loginMethod(String strParam1, String strParam2)
    {
        fieldFirebaseAuth.signInWithEmailAndPassword(strParam1, strParam2).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "Task successful.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Error. Task failed.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_one, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.menu_signout)
        {
            fieldFirebaseAuth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }
}
