package com.project.sam.knustclient;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.project.sam.knustclient.TablesGrid.Table2;
import com.project.sam.knustclient.TablesGrid.TableForFour;
import com.project.sam.knustclient.TablesGrid.TableSix;
import com.project.sam.knustclient.TablesGrid.TableforFive;

public class TableTypeSelection extends AppCompatActivity {

    android.support.v7.widget.GridLayout Grid;

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_type_selection);



        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        //        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Table Order");

        Grid =   findViewById(R.id.TableGrid);//check id well e
        //set event
        SetSingleEvent(Grid);
        // setToggleEvent(mainGrid);

//
//        int screenWidth = getScreenWidth();
//        int cellWidth = screenWidth / 4;
//        int margin = cellWidth / 8;
//        GridLayout.LayoutParams p;
//        for(int i = 0; i < Grid.getChildCount(); i++){
//            p = (GridLayout.LayoutParams)Grid.getChildAt(i).getLayoutParams();
//            p.width = cellWidth;
//            p.setMargins(margin, margin, margin, margin);
//        }
    }

//    private int getScreenWidth(){
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        return  size.x;
//    }

    private void SetSingleEvent(android.support.v7.widget.GridLayout grid) {


        //loop all child from main grid
        for (int i = 0; i
                < Grid.getChildCount();
             i++) {
            //you can see, all child is cardview , so we just cast object to cardView
            CardView cardView = (CardView) Grid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (finalI == 0) //open activity one
                    {
                        Intent intent = new Intent(TableTypeSelection.this, Table2.class);
                        startActivity(intent);
                        //add shared pre

                    }
                    else if (finalI == 1) //open 2
                    {
                        Intent intent = new Intent(TableTypeSelection.this, TableForFour.class);
                        startActivity(intent);


                    }
                    else if (finalI == 2) //open 3
                    {
                        Intent intent = new Intent(TableTypeSelection.this, TableforFive.class);
                        startActivity(intent);

                    }

                    else if (finalI == 3) //open 4
                    {
                        Intent intent = new Intent(TableTypeSelection.this, TableSix.class);
                        startActivity(intent);

                    }

                    else {
                        Toast.makeText(TableTypeSelection.this, "Please set activity for this card ", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }

    }
}