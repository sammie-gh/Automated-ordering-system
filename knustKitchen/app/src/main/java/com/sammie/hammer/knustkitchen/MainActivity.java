package com.sammie.hammer.knustkitchen;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.sammie.hammer.knustkitchen.Common.Common;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    FirebaseDatabase db;
    DatabaseReference requests;
    DatabaseReference saverequest;

    SwipeRefreshLayout swipeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase
        db = FirebaseDatabase.getInstance();
        requests = db.getReference("Requests");
        saverequest = db.getReference("TotalSales");


        //init
        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager =  new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        swipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_layout);
        swipeLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_light,
                android.R.color.white,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
loadOrders();

                }//ends here

        });


        swipeLayout.post(new Runnable() {
            @Override
            public void run() {
                loadOrders();
            }
        });

        loadOrders( ); // load all order





    }


    private void loadOrders( ) {


        FirebaseRecyclerOptions<Request> options = new FirebaseRecyclerOptions.Builder<Request>()
                .setQuery(requests, Request.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder viewHolder, final int position, @NonNull final Request model) {

                viewHolder.txtOrderId.setText(" Order number " + adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText("Status " + Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderAddress.setText(model.getAddress());
                viewHolder.txtTableNumber.setText("Table number " + model.getTableNumber());
                viewHolder.txtDate.setText(Common.getDate(Long.parseLong(adapter.getRef(position).getKey())));


                //new event button
                viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showUpdateDialog(adapter.getRef(position).getKey(),
                                adapter.getItem(position));

                    }
                });

                viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteOrder(adapter.getRef(position).getKey());
                    }
                });

//                viewHolder.btnDetail.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        Intent orderdeatil =  new Intent(OrderStatus.this,OrderDetail.class);
//                        Common.currentRequest = model;
//                        orderdeatil.putExtra("OrderId",adapter.getRef(position).getKey());
//                        startActivity(orderdeatil);
//
//
//                    }
//                });
//
//                viewHolder.btnDirections.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        Intent trackingOrder =  new Intent(OrderStatus.this,TrackingOrder.class);
//                        Common.currentRequest = model;
//                        startActivity(trackingOrder);
//
//                    }
//                });
                viewHolder.btnDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent orderdeatil =  new Intent(MainActivity.this,OrderDetail.class);
                        Common.currentRequest = model;
                        orderdeatil.putExtra("OrderId",adapter.getRef(position).getKey());
                        startActivity(orderdeatil);


                    }
                });


            }

            @Override
            public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_layout,parent,false);
                return new OrderViewHolder(itemView);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        swipeLayout.setRefreshing(false);



//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        if (item.getTitle().equals(Common.UPDATE))
//            //check to fix double update
//            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
//
//
//        else if (item.getTitle().equals(Common.DELETE))
//            deleteOrder(adapter.getRef(item.getOrder()).getKey());
//        return super.onContextItemSelected(item);
//
//    }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(adapter != null)
            adapter.startListening();

    }

    private void deleteOrder(String key) {
        saverequest.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        adapter.notifyDataSetChanged();

    }

    private void showUpdateDialog(String key, final Request item) {
        final AlertDialog.Builder alertDialog  = new AlertDialog.Builder(this,R.style.alertDialog);
        alertDialog.setTitle("Update Order");
        alertDialog.setMessage("Please choose status");


        LayoutInflater inflater = this.getLayoutInflater();
        final  View view = inflater.inflate(R.layout.update_order_layout,null);

        final MaterialSpinner spinner = (MaterialSpinner) view.findViewById(R.id.statusSpinner);
        spinner.setItems("Placed","Delivered");//add order received later or more

        alertDialog.setView(view);

        final String localKey = key;
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));

                requests.child(localKey).setValue(item);

                adapter.notifyDataSetChanged();//add to update itemsize

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
