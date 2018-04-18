package com.example.mikael.mikaelhagfeldt_petshare;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity
{
    private Button fieldButtonLogin;
    private Button fieldButtonCreateNewAcc;
    private EditText fieldEditTextEmail;
    private EditText fieldEditTextPassword;

    private FirebaseAuth fieldFirebaseAuth;
    private FirebaseAuth.AuthStateListener fieldFirebaseAuthStateListener;
    private FirebaseUser fieldFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fieldButtonLogin = findViewById(R.id.id_main_buttonLogin);
        fieldButtonCreateNewAcc = findViewById(R.id.id_main_buttonCreateAcc);
        fieldEditTextEmail = findViewById(R.id.id_main_editTextEmail);
        fieldEditTextPassword = findViewById(R.id.id_main_editTextPassw);

        fieldFirebaseAuth = FirebaseAuth.getInstance();
        fieldFirebaseAuthStateListener = new FirebaseAuth.AuthStateListener()
        {
            /*
                En metod som "lyssnar efter" förändringar i databasens "authentification state". Ifall
                användaren är inloggad så skapas en intent som tar användaren vidare till nästa sida.
                Ifall användaren inte är inloggad så skickas ett felmeddelande.
             */

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                fieldFirebaseUser = fieldFirebaseAuth.getCurrentUser();

                if (fieldFirebaseUser != null)
                {
                    Toast.makeText(MainActivity.this, "FirebaseUser is not null", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, AfterListManagementActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "FirebaseUser is null", Toast.LENGTH_LONG).show();
                }
            }
        };

        // Logik då användaren försöker logga in.

        fieldButtonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (TextUtils.isEmpty(fieldEditTextEmail.getText().toString()) && TextUtils.isEmpty(fieldEditTextPassword.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Error. Email and Password has empty fields.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String strEmail = fieldEditTextEmail.getText().toString();
                    String strPassword = fieldEditTextPassword.getText().toString();
                    login(strEmail, strPassword);
                }
            }
        });

    }

    /*
        Logik för själva "inloggningen". Användaren loggar in med användarnamn och lösenord, och påkallar
        en OnCompleteListener för att berätta för användaren om man lyckades med det eller inte. Dvs genom
        Toast meddelanden i detta fall.
     */

    private void login(String strEmail, String strPassword)
    {
        fieldFirebaseAuth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "Success. You signed in.", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(MainActivity.this, AfterListManagementActivity.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Error. Could not log in.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Metoder för att få menyn att fungera korrekt.

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_one, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.id_menu_signout)
        {
            fieldFirebaseAuth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
        När applikationen startas kopplas FirebaseAuthStateListener till FirebaseAuth. Då appen
        avslutas tar man bort denna koppling, dvs så länge inte FirebaseAuthStateListener redan är
        null.
     */

    @Override
    protected void onStart()
    {
        super.onStart();
        fieldFirebaseAuth.addAuthStateListener(fieldFirebaseAuthStateListener);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if (fieldFirebaseAuthStateListener != null)
        {
            fieldFirebaseAuth.removeAuthStateListener(fieldFirebaseAuthStateListener);
        }
    }
}
