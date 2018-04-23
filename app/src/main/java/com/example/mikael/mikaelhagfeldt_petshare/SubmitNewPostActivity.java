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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class SubmitNewPostActivity extends AppCompatActivity
{
    // Deklarationer av widgets/views från tillhörande klass till aktiviteten.

    private ImageButton fieldImageButton;
    private EditText fieldEditTextTitle;
    private EditText fieldEditTextRandomText;
    private Button fieldButton;

    // Deklarationer av Firebase instanser för att komma i kontakt med databasen.

    private DatabaseReference fieldDatabaseReference;
    private FirebaseAuth fieldFirebaseAuth;
    private FirebaseUser fieldFirebaseUser;
    private StorageReference fieldStorageReference; // För bilduppladdning

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
        fieldStorageReference = FirebaseStorage.getInstance().getReference(); // Refererar till länken i Firebase Storage

        fieldImageButton = findViewById(R.id.id_addpost_imageButton);
        fieldEditTextTitle = findViewById(R.id.id_addpost_editText1);
        fieldEditTextRandomText = findViewById(R.id.id_addpost_editText2);
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

    /*
        Bilder kan inte läggas upp på samma sätt som vanlig "text". Det krävs då Firebase Storage,
        som man får åtkomst till genom att använda sig av en StorageReference. Man tar dess referens,
        kopplar den med väsentlig Uri (URL, path), och kan då ladda upp större filer i form av bilder mm.

        post() inkluderar även en HashMap vilket är en smidigare version av en ArrayList när det kommer till
        att koppla data till specifika "keys". På detta sätt kan vi enklare ladda upp denna data till
        Firebase.
     */

    private void post()
    {
        final String strTitle = fieldEditTextTitle.getText().toString(); // Måste vara final?
        final String strRandomText = fieldEditTextRandomText.getText().toString();

        if ((fieldUri != null) && !TextUtils.isEmpty(strTitle) && !TextUtils.isEmpty(strRandomText))
        {
            StorageReference storageReference = fieldStorageReference.child("TestNode_Pictures").child(fieldUri.getLastPathSegment()); // The URL of the picture
            storageReference.putFile(fieldUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Uri uri = taskSnapshot.getDownloadUrl(); // Path till bilden finns härinne
                    DatabaseReference databaseReference = fieldDatabaseReference.push(); // Skapar nytt element

                    Map<String, String> stringStringMap = new HashMap<>(); // En HashMap är en uppgraderad version av en ArrayList, kopplar key till value
                    stringStringMap.put("fieldStrTitle", strTitle);
                    stringStringMap.put("fieldStrRandomText", strRandomText);
                    stringStringMap.put("fieldStrImageText", uri.toString());
                    stringStringMap.put("fieldStrDateTime", String.valueOf(java.lang.System.currentTimeMillis()));
                    stringStringMap.put("fieldStrUserID", fieldFirebaseUser.getUid());  //Firebase User innehåller sitt ID
                    databaseReference.setValue(stringStringMap);

                    Toast.makeText(SubmitNewPostActivity.this, "Success. Element added.", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(SubmitNewPostActivity.this, AfterListManagementActivity.class));
                }
            });
        }
    }


}
