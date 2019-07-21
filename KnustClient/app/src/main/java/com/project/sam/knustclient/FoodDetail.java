package com.project.sam.knustclient;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.project.sam.knustclient.Common.Common;
import com.project.sam.knustclient.Database.Database;
import com.project.sam.knustclient.Model.Food;
import com.project.sam.knustclient.Model.Order;
import com.project.sam.knustclient.Utils.BottomViewUtils;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class FoodDetail extends AppCompatActivity {

    TextView food_name,food_price,food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;
    String foodId="";
    FirebaseDatabase database;
    DatabaseReference foods;
    Food currentFood;
    FloatingTextButton cartBtn;
    private Context mContext;
    private Toolbar mtoolbar;
    private static final int ACTIVITY_NUM = 1;
    private CoordinatorLayout FoodLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

//        TOOLBAR
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setup the backarrow for navigating back to prof....
        ImageView backArrow = (ImageView) findViewById(R.id.back_btn);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FoodLayout = (CoordinatorLayout) findViewById(R.id.foodLayout);

        //Firebase
        database = FirebaseDatabase.getInstance();
        foods =  database.getReference("Foods");

        //bottomview
        setupBottomNavigationView();

        //init view
        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
//        btnCart =  (FloatingActionButton)findViewById(R.id.btnCart);
        cartBtn = findViewById(R.id.btnCart);

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getBaseContext()).addToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice(),
                        currentFood.getDiscount()

                ));
//                Toasty.success(FoodDetail.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                Snackbar snackbar =  Snackbar.make(FoodLayout, "Added to cart ", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Open Cart", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent order =  new Intent(FoodDetail.this,Cart.class);
                                order.putExtra("cart",foodId);
                                order.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );//remove
                                startActivity(order);//remove
                                finish();

                            }
                        });
                snackbar.show();
            }
        });


        food_description = (TextView) findViewById(R.id.food_description);
        food_price = (TextView) findViewById(R.id.food_Price);
        food_name = (TextView) findViewById(R.id.food_name);
        food_image = (ImageView) findViewById(R.id.img_food);

        collapsingToolbarLayout =  (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);




        //Get Food from Intent

        if(getIntent() != null)
            foodId =  getIntent().getStringExtra("FoodId");
        if (!foodId.isEmpty())
        {
            if (Common.isConnectedToInternet(getBaseContext()))
                getDetailFood(foodId);

            else {
                Toasty.warning(FoodDetail.this, "Please check your connection", Toast.LENGTH_LONG).show();
                return;
            }

        }
    }

    private void setupBottomNavigationView() {

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomViewUtils.setupBottomNavigationView(bottomNavigationViewEx);
        BottomViewUtils.enableNavigation(FoodDetail.this,this,bottomNavigationViewEx);

    }

    private void getDetailFood(final String foodId) {

        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                currentFood = dataSnapshot.getValue(Food.class);


                Picasso.with(getBaseContext()).load(currentFood.getImage())
                        .into(food_image);

                collapsingToolbarLayout.setTitle(currentFood.getName());

                food_price.setText(currentFood.getPrice());
                food_name.setText(currentFood.getName());
                food_description.setText(currentFood.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    /**
     * BottomNavigationView setup
     */
//    private void setupBottomNavigationView(){
//
//        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
//        BottomViewUtils.setupBottomNavigationView(bottomNavigationViewEx);
//        BottomNavigationViewHelper.enableNavigation(mContext, this,bottomNavigationViewEx);
//        Menu menu = bottomNavigationViewEx.getMenu();
////        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
////        menuItem.setChecked(true);
//    }



}

