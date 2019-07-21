package com.gh.sammie.manager.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gh.sammie.manager.Common.Common;
import com.gh.sammie.manager.Common.Interface.ItemClickListener;
import com.gh.sammie.manager.R;


/**
 * Created by A.Richard on 19/09/2017.
 */



public class FoodViewHolder extends RecyclerView.ViewHolder
        implements
        View.OnClickListener,
        View.OnCreateContextMenuListener


{


    public TextView food_name, food_price;
    public ImageView dress_image;


    private ItemClickListener itemClickListener;



    public FoodViewHolder(View itemView) {
        super(itemView);

        food_name =  (TextView)itemView.findViewById(R.id.food_name);
        dress_image = (ImageView)itemView.findViewById(R.id.food_image);
        food_price = itemView.findViewById(R.id.dress_price);


        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);


    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }



    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select the action");
        contextMenu.setHeaderIcon(R.drawable.ic_add_shopping_cart_black_24dp);

        contextMenu.add(0,0,getAdapterPosition(), Common.UPDATE);
        contextMenu.add(0,1,getAdapterPosition(), Common.DELETE);

    }
}
