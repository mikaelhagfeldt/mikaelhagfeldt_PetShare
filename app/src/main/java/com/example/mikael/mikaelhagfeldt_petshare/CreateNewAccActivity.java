package com.example.mikael.mikaelhagfeldt_petshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewAccActivity extends AppCompatActivity
{
    // Deklarationer av views/widgets

    private EditText fieldEditTextFirstName;
    private EditText fieldEditTextLastName;
    private EditText fieldEditTextEmail;
    private EditText fieldEditTextPassword;
    private Button fieldButtonCreateAcc;

    // Deklarationer av olika Firebase instanser

    private FirebaseDatabase fieldFirebaseDatabase;
    private FirebaseAuth fieldFirebaseAuth;
    private DatabaseReference fieldDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_acc);

        fieldFirebaseDatabase = FirebaseDatabase.getInstance();
        fieldFirebaseAuth = FirebaseAuth.getInstance();
        fieldDatabaseReference = fieldFirebaseDatabase.getReference().child("DatabaseUsers");
        fieldEditTextFirstName = findViewById(R.id.id_createnewacc_editText_firstName);
        fieldEditTextLastName = findViewById(R.id.id_createnewacc_editText_lastName);
        fieldEditTextEmail = findViewById(R.id.id_createnewacc_editText_email);
        fieldEditTextPassword = findViewById(R.id.id_createnewacc_editText_password);
        fieldButtonCreateAcc = findViewById(R.id.id_createnewacc_button_create);

        fieldButtonCreateAcc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                createAccount();
            }
        });
    }

    /*
        Logik för att skapa ett nytt konto i appen. Metoden tar data från "views", dvs själva texten,
        och ifall dessa inte är tomma, skapar ett nytt konto med denna data.
     */

    private void createAccount()
    {
        final String strFirstName = fieldEditTextFirstName.getText().toString();
        final String strLastName = fieldEditTextLastName.getText().toString();
        String strEmail = fieldEditTextEmail.getText().toString();
        String strPassword = fieldEditTextPassword.getText().toString();

        if (!TextUtils.isEmpty(strFirstName) && !TextUtils.isEmpty(strLastName) && !TextUtils.isEmpty(strEmail) && !TextUtils.isEmpty(strPassword))
        {
            fieldFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>()
            {
                @Override
                public void onSuccess(AuthResult authResult)
                {
                    if (authResult != null)
                    {
                        // Databas referensen använder sig av ID från den nuvarande användaren.
                        // Användarens ID i form av en sträng fås från fältet fieldFirebaseAuth.

                        String strUserID = fieldFirebaseAuth.getCurrentUser().getUid();
                        DatabaseReference databaseReference = fieldDatabaseReference.child(strUserID);
                        databaseReference.child("firstName").setValue(strFirstName);
                        databaseReference.child("lastName").setValue(strLastName);
                        databaseReference.child("userImage").setValue("notYetImplemented");

                        Intent intent = new Intent(CreateNewAccActivity.this, AfterListManagementActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Tar bort ifall det ligger flera intents som inte används
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
