package com.project.sam.knustclient.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.sam.knustclient.Interface.ItemClickListener;
import com.project.sam.knustclient.R;

/**
 * Created by A.Richard on 04/09/2017.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{


    public TextView food_name;
    public ImageView food_image;
    public  TextView  time,price;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

//    public FoodViewHolder(View itemView, ItemClickListener itemClickListener) {
//        super(itemView);
//        this.itemClickListener = itemClickListener;
//    }

    public FoodViewHolder(View itemView) {
        super(itemView);

        food_name =  (TextView)itemView.findViewById(R.id.food_name);
        food_image = (ImageView)itemView.findViewById(R.id.food_image);
        price = (TextView)itemView.findViewById(R.id.food_price);


        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view ,getAdapterPosition(),false);


    }
}
