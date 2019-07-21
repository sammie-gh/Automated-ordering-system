package com.project.sam.knustclient;

import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.sam.knustclient.Common.Common;
import com.project.sam.knustclient.Database.Database;
import com.project.sam.knustclient.Model.Order;

import com.project.sam.knustclient.Model.Request;
import com.project.sam.knustclient.ViewHolder.CartAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference requests,backUpReq;
    TextView txtTotalPrice;
    FButton btnPlace;

    List<Order> cart = new ArrayList<>();

    CartAdapter adapter;

    //Toolbar
    private Toolbar mToolbar;
    private static final int ACTIVITY_NUM = 1;
    private Context mContext = Cart.this;;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

//        //Toolbar
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setTitle("Cart");


        //Firebase

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        backUpReq = database.getReference("backUpReq");

        //init
        recyclerView  =  (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPlace  = (FButton) findViewById(R.id.btnPlaceOrder);
        btnPlace.setButtonColor(getResources().getColor(R.color.materialBlue));
        btnPlace.setShadowColor(getResources().getColor(R.color.fbutton_color_midnight_blue));

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cart.size() > 0)
                    showAlertDialog();

                else
                    Toasty.warning(mContext, "Your Cart is Empty :(", Toast.LENGTH_LONG).show();

            }
        });


        loadListFood();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this,R.style.alertDialog);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Please confirm your action");

        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Log.d(TAG, "onClick: crashes");
                //create new request

                Request request = new Request(
                        Common.currentUser.getTableNumber(),
                        "Customer",
                        "Main Branch",
                        txtTotalPrice.getText().toString(),
                        cart
                );

                //submit to Firebase
                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);

                backUpReq.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);

                //delete cart
                new Database(getBaseContext()).cleanCart();
                Toasty.success(Cart.this, "Thank You, \n " +
                        "Order was placed Successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();


    }

    private void loadListFood() {

        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        adapter.notifyDataSetChanged(); //handles delete referesh
        recyclerView.setAdapter(adapter);


        //Calculate total price
        int total = 0;
        for (Order order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));


        Locale locale = new Locale("en","GH");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.DELETE))
            deleteCart(item.getOrder());

        return true;

    }

    private void deleteCart(int position) {

        cart.remove(position );
        //delete from sql

        new Database(this).cleanCart();

        for (Order item:cart)
            new Database(this).addToCart(item);

        loadListFood();

    }
}