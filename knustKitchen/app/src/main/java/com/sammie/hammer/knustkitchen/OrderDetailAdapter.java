package com.sammie.hammer.knustkitchen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

/**
 * Created by A.Richard on 03/10/2017.
 */


class MyViewHolder extends RecyclerView.ViewHolder
        implements  View.OnClickListener{

    public TextView name,quantity,price,discount,displayName;
    private ItemClickListener itemClickListener;


    public MyViewHolder(View itemView) {
        super(itemView);

        name= (TextView)itemView.findViewById(R.id.product_name);
        quantity= (TextView)itemView.findViewById(R.id.product_quantity);
        price= (TextView)itemView.findViewById(R.id.product_price);
        discount= (TextView)itemView.findViewById(R.id.product_discount);



    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }


    @Override
    public void onClick(View view) {
      itemClickListener.onClick(view,getAdapterPosition(),false);
        return;
    }
}

//ends

public class OrderDetailAdapter extends RecyclerView.Adapter<MyViewHolder>{

    List<Order>myOrder;

    public OrderDetailAdapter(List<Order> myOrder) {
        this.myOrder = myOrder;
    }

               @Override
                 public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                  View itemView = LayoutInflater.from(parent.getContext())
                  .inflate(R.layout.order_detail_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Order order = myOrder.get(position);
        holder.name.setText(String.format("Name :%s", order.getProductName()));
        holder.price .setText(String.format("Price :%s", order.getPrice()));
        holder.discount.setText(String.format("Discount :%s", order.getDiscount()));
        holder.quantity.setText(String.format("Quantity :%s", order.getQuantity()));


    }

    @Override
    public int getItemCount() {
        return myOrder.size();
    }

}
