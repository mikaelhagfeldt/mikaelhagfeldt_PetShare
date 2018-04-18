package com.example.mikael.mikaelhagfeldt_petshare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitNewPostActivity extends AppCompatActivity
{
    // Deklarationer av widgets/views från tillhörande klass till aktiviteten.

    private ImageButton fieldImageButton;
    private EditText fieldEditTextTitle;
    private EditText fieldEditTextDescription;
    private Button fieldButton;

    // Deklarationer av Firebase instanser för att komma i kontakt med databasen.

    private DatabaseReference fieldDatabaseReference;
    private FirebaseAuth fieldFirebaseAuth;
    private FirebaseUser fieldFirebaseUser;

    // Nödvändig konstant för att komma åt fotobiblioteket på ens mobil.

    private static final int ACCESS_GALLERY = 3;

    // En "Uri" är länkat till själva "path" till ens bilder.

    private Uri fieldUri;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_new_post);

        // Instanseringar av tidigare deklarationer.

        fieldFirebaseAuth = FirebaseAuth.getInstance();
        fieldFirebaseUser = fieldFirebaseAuth.getCurrentUser();
        fieldDatabaseReference = FirebaseDatabase.getInstance().getReference().child("TestNode");      // Ett test för att se att allting fungerar.

        fieldImageButton = findViewById(R.id.id_addpost_imageButton);
        fieldEditTextTitle = findViewById(R.id.id_addpost_editText1);
        fieldEditTextDescription = findViewById(R.id.id_addpost_editText2);
        fieldButton = findViewById(R.id.id_addpost_button1);

        fieldButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Metod för att lägga till en post i nyhetsflödet. Ännu inte korrekt implementerat.

                post();
            }
        });

        /*
            Logik för vad som händer när användaren vill lägga till en bild. En "intent" skapas
            och accepterar alla typer av bilder/foton. Därefter startas en aktivitet med hjälp av
            variabeln som "godkänner" mobilens åtkomst till bilderna.
         */

        fieldImageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*"); // Vår typ av intent är alla typer av bilder, därav en "*".
                startActivityForResult(intent, ACCESS_GALLERY);
            }
        });
    }

    private void post()
    {
        String strTitle = fieldEditTextTitle.getText().toString();
        String strDescription = fieldEditTextDescription.getText().toString();

        if (!(TextUtils.isEmpty(strTitle) && TextUtils.isEmpty(strDescription)))
        {
            // Kollar ifall strängarna inte är tomma, och ifall de inte är det, fortsätter med själva uppladdningen. Ännu inte korrekt implementerat.

            Blog blog = new Blog("Title", "Random Text", "ImageURL", "TimeStamp", "UserID");
            fieldDatabaseReference.setValue(blog).addOnSuccessListener(new OnSuccessListener<Void>()
            {
                @Override
                public void onSuccess(Void aVoid)
                {
                    Toast.makeText(getApplicationContext(), "Element Added", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // En metod som först kollar av att väsentliga variabler är godtyckliga, och ifall allting är okej, fortsätter med uppladdningen av bilden.

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACCESS_GALLERY && resultCode == RESULT_OK)
        {
            fieldUri = data.getData();
            fieldImageButton.setImageURI(fieldUri);
        }
    }
}
