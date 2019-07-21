package com.project.sam.knustclient.TablesGrid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.sam.knustclient.MainActivity;
import com.project.sam.knustclient.R;

import java.util.Calendar;

public class TableforFive extends AppCompatActivity {


    android.support.v7.widget.GridLayout table_fiveGrid;
    TextView Welcome,Info;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablefor_five);

        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Table for Five");


        table_fiveGrid = findViewById(R.id.table_5_grid);

        //welcome
        Welcome  =(TextView) findViewById(R.id.greeting);
        Info  =(TextView) findViewById(R.id.info);
        Info.setSelected(true);  // Set focus to the textview


        Timeofday();

        //set event
        SetSingleEvent(table_fiveGrid);
        // setToggleEvent(mainGrid);
    }

    private void SetSingleEvent(android.support.v7.widget.GridLayout table_fiveGrid) {
        //here count start from zero since im using different activity for each grid


        //loop all child from main grid
        for (int i = 0; i
                < table_fiveGrid.getChildCount();
             i++) {
            //you can see, all child is cardview , so we just cast object to cardView
            CardView cardView = (CardView) table_fiveGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (finalI == 0) //open activity one
                    {
                        Intent intent = new Intent(TableforFive.this, MainActivity.class);
                        startActivity(intent);
                        //add shared pre

                    }
                    else if (finalI == 1) //open 2
                    {
                        Intent intent = new Intent(TableforFive.this, MainActivity.class);
                        startActivity(intent);


                    }
                    else if (finalI == 2) //open 3
                    {
                        Intent intent = new Intent(TableforFive.this, MainActivity.class);
                        startActivity(intent);


                    }

                    else if (finalI == 3) //open 4
                    {
                        Intent intent = new Intent(TableforFive.this, MainActivity.class);
                        startActivity(intent);


                    }



                    else {
                        Toast.makeText(TableforFive.this, "Please set activity for this card ", Toast.LENGTH_LONG).show();
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
