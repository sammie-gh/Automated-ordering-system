package com.project.sam.knustclient.Redundants;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.sam.knustclient.MainActivity;
import com.project.sam.knustclient.R;

import java.util.Calendar;

public class TablesSelection extends AppCompatActivity {

    GridLayout mainGrid;
    TextView Welcome,Info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables_selection);

        mainGrid = (GridLayout)findViewById(R.id.table_2_grid);

        //welcome
        Welcome  =(TextView) findViewById(R.id.greeting);
        Info  =(TextView) findViewById(R.id.info);
        Info.setSelected(true);  // Set focus to the textview


        Timeofday();

             //set event
             SetSingleEvent(mainGrid);
            // setToggleEvent(mainGrid);


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

    private void setToggleEvent(GridLayout mainGrid) {
        //loop all child from main grid

        for(int i=0;i
                <mainGrid.getChildCount();
            i++ )
        {
            //you can see, all child is cardview , so we just cast object to cardView
            final CardView cardView = (CardView)mainGrid.getChildAt(i);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1 )
                    {
                        //change color
                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));

                    }
                    else
                    {
                        //change color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                }
            });
        }
    }

    private void SetSingleEvent(GridLayout mainGrid) {

        //loop all child from main grid
        for(int i=0;i
                <mainGrid.getChildCount();
            i++ )
        {
            //you can see, all child is cardview , so we just cast object to cardView
            CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (finalI == 0) //open activity one
                    {
                    Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                    startActivity(intent);
                    //add shared pre

                    }

                    else if (finalI == 1) //open 2
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }


                    else if (finalI == 2) //open 3
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }

                    else if (finalI == 3) //open 4
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }


                    else if (finalI == 4) //open 5
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }



                    else if (finalI == 5) //open 6
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }


                    else if (finalI == 6) //open 7
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }


                    else if (finalI == 7) //open 8
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }

                    else if (finalI == 8) //open 9
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }

                    else if (finalI == 9) //open 10
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }

                    else if (finalI == 10) //open 11
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }


                    else if (finalI == 11) //open 12
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }


                    else if (finalI == 12) //open 13
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }


                    else if (finalI == 13) //open 14
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }



                    else if (finalI == 14) //open 15
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }


                    else if (finalI == 15) //open 16
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }


                    else if (finalI == 16) //open 17
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }


                    else if (finalI == 17) //open 18
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }


                    else if (finalI == 18) //open 19
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }



                    else if (finalI == 19) //open 20
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }


                    else if (finalI == 20) //open 21
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }



                    else if (finalI == 21) //open 22
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }



                    else if (finalI == 22) //open 23
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }

                    else if (finalI == 23) //open 24
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }

                    else if (finalI == 24) //open 25
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }

                    else if (finalI == 25) //open 26
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }

                    else if (finalI == 26) //open 27
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else if (finalI == 27) //open 28
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }


                    else if (finalI == 28) //open 29
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }


                    else if (finalI == 29) //open 30
                    {
                        Intent intent = new Intent(TablesSelection.this,MainActivity.class);
                        startActivity(intent);
                    }

                    else {
                        Toast.makeText(TablesSelection.this, "Please set activity for this card ", Toast.LENGTH_LONG).show();
                    }



                }
            });

        }
    } //click listeners here

}
