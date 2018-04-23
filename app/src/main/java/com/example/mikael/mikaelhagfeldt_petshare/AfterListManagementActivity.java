package com.example.mikael.mikaelhagfeldt_petshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AfterListManagementActivity extends AppCompatActivity
{
    private DatabaseReference fieldDatabaseReference;
    private FirebaseDatabase fieldFirebaseDatabase;
    private FirebaseUser fieldFirebaseUser;
    private FirebaseAuth fieldFirebaseAuth;

    /*
        Deklarationer av fält för att kunna skapa det "nyhetsflöde" där alla inlägg kommer
        att ligga efter varandra.
     */
    private RecyclerView fieldRecyclerView;
    private Adapter fieldAdapter;
    private List<Blog> fieldBlogList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_list_management);

        fieldRecyclerView = findViewById(R.id.id_afterlistmanagementactivity_recyclerView);
        fieldRecyclerView.setHasFixedSize(true);
        fieldRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fieldBlogList = new ArrayList<>();

        fieldFirebaseAuth = FirebaseAuth.getInstance();
        fieldFirebaseUser = fieldFirebaseAuth.getCurrentUser();
        fieldFirebaseDatabase = FirebaseDatabase.getInstance();
        fieldDatabaseReference = fieldFirebaseDatabase.getReference().child("TestNode");
        fieldDatabaseReference.keepSynced(true);    // Ser till att databasen hålls synkad.
    }

    /*
        Innan aktiviteten syns på skärmen så kan man göra allting redo i onStart() metoden.
        Då slipper man bry sig om det medan appen är igång.
     */

    @Override
    protected void onStart()
    {
        super.onStart();

        fieldDatabaseReference.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                // När en ny nod läggs till i databasen

                Blog blog = dataSnapshot.getValue(Blog.class); // Mappar till klass Blog
                fieldBlogList.add(blog); // Alla objekt i Databasen läggs nu till i listan
                fieldAdapter = new Adapter(AfterListManagementActivity.this, fieldBlogList);
                fieldRecyclerView.setAdapter(fieldAdapter);
                fieldAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                // När en form av uppdatering sker av en nod i databasen
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {
                // När en specifik nod tas bort från databasen
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
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
