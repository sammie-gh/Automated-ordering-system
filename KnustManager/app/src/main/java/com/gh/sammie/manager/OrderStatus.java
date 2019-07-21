package com.gh.sammie.manager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.gh.sammie.manager.Common.Common;
import com.gh.sammie.manager.Model.Request;
import com.gh.sammie.manager.ViewHolder.OrderViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class OrderStatus extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder>adapter;

    FirebaseDatabase db;
    DatabaseReference requests;

    Toolbar mToolbar;

    MaterialSpinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_order_status);

        //Firebase
        db = FirebaseDatabase.getInstance();
        requests = db.getReference("Requests");

        mToolbar =findViewById(R.id.toolbar);
        mToolbar.setTitle("Orders");
        setSupportActionBar(mToolbar);


        //init
        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager =  new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        
        loadOrders(); // load all order
    }

    private void loadOrders() {
        adapter =  new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests

        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, final Request model, int position) {

                viewHolder.txtOrderId.setText(" Order number " + adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText("Status " + Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderAddress.setText(model.getAddress());
                viewHolder.txtTableNumber.setText("Table number " + model.getTableNumber());
                viewHolder.txtOrderPrice.setText("Price " +model.getTotal());
                viewHolder.txtDate.setText(Common.getDate(Long.parseLong(adapter.getRef(position).getKey())));



            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.UPDATE))
            //ceck to fix double update
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));


        else if (item.getTitle().equals(Common.DELETE))
            deleteOrder(adapter.getRef(item.getOrder()).getKey());
        return super.onContextItemSelected(item);

    }

    private void deleteOrder(String key) {
        requests.child(key).removeValue();
    }

    private void showUpdateDialog(String key, final Request item) {
        final AlertDialog.Builder alertDialog  = new AlertDialog.Builder(this);
        alertDialog.setTitle("Update Order");
        alertDialog.setMessage("Please choose status");


        LayoutInflater inflater = this.getLayoutInflater();
        final  View view = inflater.inflate(R.layout.update_order_layout,null);

        spinner = (MaterialSpinner) view.findViewById(R.id.statusSpinner);
        spinner.setItems("Placed","Delivered");//add order received later

        alertDialog.setView(view);

        final String localKey = key;
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));

                requests.child(localKey).setValue(item);

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
}
