package com.project.sam.knustclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.mancj.materialsearchbar.MaterialSearchBar;

import com.project.sam.knustclient.Interface.ItemClickListener;
import com.project.sam.knustclient.Model.Food;
import com.project.sam.knustclient.Utils.BottomViewUtils;
import com.project.sam.knustclient.ViewHolder.FoodViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity {

    private static final String TAG = "FoodList";



    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodlist;

    String categoryId = "";
    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;


    //Search Functionality
    FirebaseRecyclerAdapter<Food,FoodViewHolder> searchAdapter;
    List<String> suggestion = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    private static final int ACTIVITY_NUM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist);

        //Firebase
        database =  FirebaseDatabase.getInstance();
        foodlist = database.getReference("Foods");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager  = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//        bottomView
        setupBottomNavigationView();

        //Get Intent here
        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");

        if (!categoryId.isEmpty() && categoryId != null){

//            if (Common.isConnectedToInternet(getBaseContext()))
                loadListFood(categoryId);


        }

        //Search
        materialSearchBar = (MaterialSearchBar) findViewById(R.id.search_bar);
        materialSearchBar.setHint("Enter your food");

        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestion);
        materialSearchBar.setCardViewElevation(10);

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //when user type their text, we will change sugesst list
                List<String> suggest = new ArrayList<String>();
                for (String search:suggestion)
                {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);

                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //when search is closed
                // restore original adapter

                if (!enabled)
                    recyclerView.setAdapter(adapter);

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //when search finish
                //show of search adapter

                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    private void startSearch(CharSequence text) {
        searchAdapter =  new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodlist.orderByChild("name").equalTo(text.toString())//compare name
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {

                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.food_image);

                final Food local = model;

                viewHolder.setItemClickListener(new ItemClickListener() {

                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //start new Activity
                        Intent fooddetail  = new Intent(FoodList.this,FoodDetail.class);
                        fooddetail.putExtra("FoodId",searchAdapter.getRef(position).getKey());// send food Id to new new act...
                        startActivity(fooddetail);


                    }
                });
            }
        };
        recyclerView.setAdapter(searchAdapter); //set adpter for Recyc... is search result

    }

    private void loadSuggest() {

        foodlist.orderByChild("menuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){

                            Food item = postSnapshot.getValue(Food.class);
                            suggestion.add(item.getName()); //add name of food to suggest list
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void setupBottomNavigationView() {

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomViewUtils.setupBottomNavigationView(bottomNavigationViewEx);
        BottomViewUtils.enableNavigation(FoodList.this,this,bottomNavigationViewEx);

    }

    private void loadListFood(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Food,FoodViewHolder>(Food.class
                ,R.layout.food_item,
                FoodViewHolder.class,
                foodlist.orderByChild("menuId").equalTo(categoryId) // like select from foods where menuid
        )
        {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                viewHolder.price.setText(String.format("GH¢ %s",model.getPrice()));
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.food_image);

                final Food local = model;

                viewHolder.setItemClickListener(new ItemClickListener() {

                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //start new Activity
                        Intent foodDetails  = new Intent(FoodList.this,FoodDetail.class);
                        foodDetails.putExtra("FoodId",adapter.getRef(position).getKey());// send food Id to new new act...
                        startActivity(foodDetails);

                    }
                });

            }
        };
        //set Adapter
        Log.d(TAG, ""+ adapter.getItemCount());
        recyclerView.setAdapter(adapter);
    }

}
