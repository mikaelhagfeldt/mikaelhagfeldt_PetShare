package com.example.mikael.mikaelhagfeldt_petshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AfterListManagementActivity extends AppCompatActivity
{
    private DatabaseReference fieldDatabaseReference;
    private FirebaseDatabase fieldFirebaseDatabase;
    private FirebaseUser fieldFirebaseUser;
    private FirebaseAuth fieldFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_list_management);

        fieldFirebaseAuth = FirebaseAuth.getInstance();
        fieldFirebaseUser = fieldFirebaseAuth.getCurrentUser();
        fieldFirebaseDatabase = FirebaseDatabase.getInstance();
        fieldDatabaseReference = fieldFirebaseDatabase.getReference().child("TestNode");
        fieldDatabaseReference.keepSynced(true);    // Ser till att databasen hålls synkad.
    }

    /*
        Metoder för menyn.
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_one, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        /*
            En switch-kropp som håller reda på vilken knapp användaren har tryckt på. Ifall användaren trycker på
            "Add New" så skickas användaren vidare till SubmitNewPostActivity, där användaren kan lägga till en ny
            post i nyhetsflödet.

            Ifall användaren trycker på "Sign Out" så loggar användaren helt enkelt ut och skickas tillbaka
            till MainActivity.
         */

        switch (item.getItemId())
        {
            case R.id.id_menu_addnew:
                if (fieldFirebaseUser != null && fieldFirebaseAuth != null)
                {
                    startActivity(new Intent(AfterListManagementActivity.this, SubmitNewPostActivity.class));
                    finish();
                }
                else
                {

                }
                break;
            case R.id.id_menu_signout:
                if (fieldFirebaseUser != null && fieldFirebaseAuth != null)
                {
                    fieldFirebaseAuth.signOut();

                    startActivity(new Intent(AfterListManagementActivity.this, MainActivity.class));
                    finish(); // Återställer aktiviteterna till noll, så att de inte "stockpilas" på varandra.
                }
                else
                {

                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
