package com.gh.sammie.manager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gh.sammie.manager.Common.Common;
import com.gh.sammie.manager.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class SignIn extends AppCompatActivity {
    EditText edtPhone,edtPassword;
    Button btnSignIn;

    FirebaseDatabase db;
  DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = (EditText) findViewById(R.id.editPassword);
        edtPhone =  (EditText)  findViewById(R.id.edtPhone);
        btnSignIn = (Button) findViewById(R.id.LOGIN);

        //Init Firebase
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Management");


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUser(edtPhone.getText().toString(),edtPassword.getText().toString());


            }
        });


    }

    private void signUser(final String phone, String password) {
        final ProgressDialog  mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please wait...");
        mDialog.show();

        final String localPhone = phone;
        final String localPassword = password;

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(localPhone).exists()){
                    mDialog.dismiss();
                    User user =  dataSnapshot.child(localPhone).getValue(User.class);
                    user.setPhone(localPhone);

                    if (Boolean.parseBoolean(user.getIsStaff())) //if isStaff == true
                    {
                        if (user.getPassword().equals(localPassword)){

                            Intent login = new Intent(SignIn.this,Home.class);
                            Common.currentUser = user;
                            startActivity(login);
                            finish();
                        }
                        else
                            Toasty.error(SignIn.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(SignIn.this, "Please login with Staff account \n or contact Admin", Toast.LENGTH_SHORT).show();

                }
                else {
                    mDialog.dismiss();
                    Toasty.error(SignIn.this, "User not exist in Database \n contact Admin or try again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
