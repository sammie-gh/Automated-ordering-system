package com.project.sam.knustclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import com.project.sam.knustclient.Common.Common;
import com.project.sam.knustclient.Interface.ItemClickListener;
import com.project.sam.knustclient.Model.Category;
import com.project.sam.knustclient.Utils.BottomViewUtils;
import com.project.sam.knustclient.ViewHolder.MenuViewHolder;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {



    //Firebase
    private Context mContext;
    FirebaseDatabase database;
    DatabaseReference category;
    RecyclerView recycler_menu;
    private Toolbar mToolbar;
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PREFS_NAM = "";

    String TableID  ;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Food menu");

        database =  FirebaseDatabase.getInstance();
        category = database.getReference("Category");


        recycler_menu = (RecyclerView)findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        loadMenu();

//        TableID = getIntent().getStringExtra("DATA1");

        //bottomview
        setupBottomNavigationView();

    }


    private void loadMenu() {

        //get shared Preferfence
//        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        final String n = settings.getString("tableNo", "Null");



        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,
                MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                viewHolder.txtMenuName.setText(model.getName());

                viewHolder.txtTableNumber.setText("Ordering from table #" + Common.currentUser.getTableNumber());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);

                final Category clickItem =  model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // Toast.makeText(Home.this, ""+ clickItem.getName(), Toast.LENGTH_SHORT).show();
                        //Get category id and send to new activity
                        Intent foodlist = new Intent(MainActivity.this,FoodList.class);
                        //Bcz category is key ,so  we just get key of this item
                        foodlist.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(foodlist);

                    }
                });

            }
        };
        recycler_menu.setAdapter(adapter);

    }


    private void setupBottomNavigationView() {

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
//        BottomViewUtils.setupBottomNavigationView(bottomNavigationViewEx);
        BottomViewUtils.enableNavigation(MainActivity.this,this,bottomNavigationViewEx);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.refresh)
            loadMenu();

        return super.onOptionsItemSelected(item);
    }
}
