package com.project.sam.knustclient.TablesGrid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.sam.knustclient.Common.Common;
import com.project.sam.knustclient.MainActivity;
import com.project.sam.knustclient.Model.User;
import com.project.sam.knustclient.R;

import java.util.Calendar;

public class Table2 extends AppCompatActivity {

    android.support.v7.widget.GridLayout table_two_grid;
    TextView Welcome,Info;
    public static final String PREFS_NAME = "MyPrefsFile";
    private Toolbar mToolbar;
    TextView mOne,mTwo,mThree,mFour,mFive,mSix,mSeven,mEight;
    TextView mLAbel;
    FirebaseDatabase database;
    DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table2);

        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Table for Two");

        //init Firebase
        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");

        table_two_grid = findViewById(R.id.table_2_grid);
//        TableId = findViewById(R.id.)

        //welcome
        Welcome  =(TextView) findViewById(R.id.greeting);
        Info  =(TextView) findViewById(R.id.info);
        Info.setSelected(true);
        mLAbel = findViewById(R.id.Grid);

        Timeofday();

        //set event
        SetSingleEvent(table_two_grid);
        // setToggleEvent(mainGrid);

        //TableIds
        mOne = findViewById(R.id.m_one);
        mTwo = findViewById(R.id.m_two);
        mThree = findViewById(R.id.m_three);
        mFour  = findViewById(R.id.mfour);

    }

    private void SetSingleEvent(android.support.v7.widget.GridLayout table_two_grid) {

        //loop all child from main grid
        for (int i = 0; i
                < table_two_grid.getChildCount();
             i++) {
            //you can see, all child is cardview , so we just cast object to cardView
            CardView cardView = (CardView) table_two_grid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (finalI == 0) //open activity one
                    {
                        table_user.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                User user = new User(mLAbel.getText().toString(),mLAbel.getText().toString());// remove from phone
                                table_user.child(mOne.getText().toString()).setValue(user);
                                user.setTableNumber(mOne.getText().toString());
                                Intent intent2 = new Intent(Table2.this, MainActivity.class);
                                Common.currentUser = user;
                                startActivity(intent2);
                                finish();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                    else if (finalI == 1) //open 2
                    {
                        table_user.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                User user = new User(mLAbel.getText().toString(),mLAbel.getText().toString());// remove from phone
                                table_user.child(mTwo.getText().toString()).setValue(user);
                                user.setTableNumber(mTwo.getText().toString());
                                Intent intent2 = new Intent(Table2.this, MainActivity.class);
                                Common.currentUser = user;
                                startActivity(intent2);
                                finish();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

//                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = settings.edit();
//                        editor.putString("tableNo", nameStr);
//                        editor.apply();

                    }
                    else if (finalI == 2) //open 3
                    {

                        User user = new User(mLAbel.getText().toString(),mLAbel.getText().toString());// remove from phone
                        table_user.child(mThree.getText().toString()).setValue(user);
                        user.setTableNumber(mThree.getText().toString());
                        Intent intent2 = new Intent(Table2.this, MainActivity.class);
                        Common.currentUser = user;
                        startActivity(intent2);
                        finish();


                    }

                    else if (finalI == 3) //open 4
                    {

                        User user = new User(mLAbel.getText().toString(),mLAbel.getText().toString());// remove from phone
                        table_user.child(mFour.getText().toString()).setValue(user);
                        user.setTableNumber(mFour.getText().toString());
                        Intent intent2 = new Intent(Table2.this, MainActivity.class);
                        Common.currentUser = user;
                        startActivity(intent2);
                        finish();

                    }



                    else {
                        Toast.makeText(Table2.this, "Please set activity for this card ", Toast.LENGTH_LONG).show();
                    }


                }
            });
        }

    }


    //time
    private void Timeofday() {


        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if(timeOfDay >= 9 && timeOfDay < 12){
            Welcome.setText("Good morning !");
            Welcome.setSelected(true);  // Set focus to the textview


        }

        if(timeOfDay >= 0 && timeOfDay < 9){
            Welcome.setText("Good morning sorry our kitchen are closed at this time");

            Welcome.setSelected(true);  // Set focus to the textview



        }
        if(timeOfDay >= 12 && timeOfDay < 16){
            Welcome.setText("Good Afternoon!");

            Welcome.setSelected(true);  // Set focus to the textview

        }
        if(timeOfDay >= 16 && timeOfDay < 21){
            Welcome.setText("Good evening !");

            Welcome.setSelected(true);  // Set focus to the textview

        }
        if(timeOfDay >= 21 && timeOfDay < 24) {
            Welcome.setText("Good night sorry our shops are closed at this time");

            Welcome.setSelected(true);  // Set focus to the textview
        }
    }



}
