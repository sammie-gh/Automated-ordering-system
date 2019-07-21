package com.gh.sammie.manager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.gh.sammie.manager.Common.Common;
import com.gh.sammie.manager.Common.Interface.ItemClickListener;
import com.gh.sammie.manager.Model.Food;
import com.gh.sammie.manager.ViewHolder.FoodViewHolder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class FoodList extends AppCompatActivity {

RecyclerView recyclerView;
RecyclerView.LayoutManager layoutManager;

FloatingActionButton fab;

//Firebase

    FirebaseDatabase db;
    DatabaseReference foodlst;
    FirebaseStorage storage;
    StorageReference storageReference;

    String categoryId="";

    RelativeLayout rootLayout;

    private final int PICK_IMAGE_REQUEST = 71;

    Uri saveUri;
    Toolbar mToolbar;
    FirebaseRecyclerAdapter<Food,FoodViewHolder>adapter;

    //Add NEw food
    EditText edtName,edtDescription,edtPrice,edtDiscount;
    Button btnSelect,btnUpload;

    Food newFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        db = FirebaseDatabase.getInstance();
        foodlst = db.getReference("Foods");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mToolbar =findViewById(R.id.toolbar);
        mToolbar.setTitle("Orders");
        setSupportActionBar(mToolbar);

        //Init
        recyclerView =(RecyclerView) findViewById( R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        rootLayout = (RelativeLayout) findViewById( R.id.root_Layout);

        fab = (FloatingActionButton) findViewById( R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            showAddFoodDialog();

            }
        });

        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty())

            loadListFood(categoryId);

    }

    private void showAddFoodDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FoodList.this,R.style.alertDialog);
        alertDialog.setTitle("Add new Food");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.add_new_food_layout,null);

        edtName = add_menu_layout.findViewById( R.id.edtName);
        edtDescription = add_menu_layout.findViewById( R.id.edtDescription);
        edtDiscount = add_menu_layout.findViewById( R.id.edtDiscount);
        edtPrice = add_menu_layout.findViewById( R.id.edtPrice);

        btnSelect =  add_menu_layout.findViewById (R.id.btnSelect);
        btnUpload = add_menu_layout.findViewById( R.id.btnUp);

        //  Event for button
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage(); // let user select image from gallery and save URI of this


            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }

        });

        alertDialog.setView(add_menu_layout);
        alertDialog.setIcon( R.drawable.ic_add_shopping_cart_black_24dp);

        //Set button
        alertDialog.setPositiveButton("Add new Food", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//can be yes
                dialogInterface.dismiss();

                //Here ,just create ne cat...
                if (newFood != null){
                    foodlst.push().setValue(newFood);
                    Snackbar.make(rootLayout,"New category" + newFood.getName()+ "was added successful", Snackbar.LENGTH_LONG )
                            .show();
                }


            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });

        alertDialog.show();

    }

    private void uploadImage() {
        if (saveUri != null)

        {
            final ProgressDialog mDialog = new ProgressDialog(this);
            mDialog.setMessage("Uploading...");
            mDialog.show();

            String imgName = UUID.randomUUID().toString();
            final StorageReference imageFolder = storageReference.child("images/" + imgName);
            imageFolder.putFile(saveUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            Toasty.success(FoodList.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //set value for new Category if image is image and we can get download link
                                    newFood = new Food();
                                    newFood.setName(edtName.getText().toString());
                                    newFood.setDescription(edtDescription.getText().toString());
                                    newFood.setPrice(edtPrice.getText().toString());
                                    newFood.setDiscount(edtDiscount.getText().toString());
                                    newFood.setMenuId(categoryId);
                                    newFood.setImage(uri.toString());



                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mDialog.dismiss();
                            Toasty.success(FoodList.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mDialog.setMessage("Uploaded" + progress+ "%");
                        }
                    });
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Food Picture"),Common.PICK_IMAGE_REQUEST);

    }

    private void loadListFood(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                 R.layout.food_item,
                FoodViewHolder.class,
                foodlst.orderByChild("menuId").equalTo(categoryId)
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {

                viewHolder.food_name.setText(model.getName());
                viewHolder.food_price.setText(String.format("GH¢ %s", model.getPrice()));
                Picasso.with(getBaseContext())
                        .load(model.getImage())
                        .into(viewHolder.dress_image);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //code la...
                    }
                });

            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Common.PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() !=null){

            saveUri = data.getData();
            btnSelect.setText("Image Selected !");

        }

    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.UPDATE))
        {
            showUpdateFoodDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }
        else if (item.getTitle().equals(Common.DELETE))
        {
            
            deleteFood(adapter.getRef(item.getOrder()).getKey());
            //add toast later
        }
        return super.onContextItemSelected(item);
    }

    private void deleteFood(String key) {
        foodlst.child(key).removeValue();
    }

    private void showUpdateFoodDialog(final String key, final Food item) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FoodList.this, R.style.alertDialog);
        alertDialog.setTitle("Edit Food");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate( R.layout.add_new_food_layout,null);

        edtName = add_menu_layout.findViewById( R.id.edtName);
        edtDescription = add_menu_layout.findViewById( R.id.edtDescription);
        edtDiscount = add_menu_layout.findViewById( R.id.edtDiscount);
        edtPrice = add_menu_layout.findViewById( R.id.edtPrice);

        //set default value for view
        edtName.setText(item.getName());
        edtDiscount.setText(item.getDiscount());
        edtPrice.setText(item.getPrice());
        edtDescription.setText(item.getDescription());



        btnSelect =  add_menu_layout.findViewById( R.id.btnSelect);
        btnUpload = add_menu_layout.findViewById( R.id.btnUp);

        //  Event for button
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage(); // let user select image from gallery and save URI of this


            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage(item);
            }

        });

        alertDialog.setView(add_menu_layout);
        alertDialog.setIcon( R.drawable.ic_add_shopping_cart_black_24dp);

        //Set button
        alertDialog.setPositiveButton("Add new Food", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//can be yes
                dialogInterface.dismiss();

                    //update info...
                    item.setName(edtName.getText().toString());
                    item.setPrice(edtPrice.getText().toString());
                    item.setDiscount(edtDiscount.getText().toString());
                    item.setDescription(edtDescription.getText().toString());


                    foodlst.child(key).setValue(item);

                    Snackbar.make(rootLayout," Food" + item.getName()+ "was edited successful", Snackbar.LENGTH_LONG )
                            .show();



            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });

        alertDialog.show();

    }

    private void changeImage(final Food item) {
        if (saveUri != null)

        {
            final ProgressDialog mDialog = new ProgressDialog(this);
            mDialog.setMessage("Uploading...");
            mDialog.show();

            String imgName = UUID.randomUUID().toString();
            final StorageReference imageFolder = storageReference.child("images/" + imgName);
            imageFolder.putFile(saveUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            Toasty.success(FoodList.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //set value for new Category if image is image and we can get download link
                                    item.setImage(uri.toString());


                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mDialog.dismiss();
                            Toasty.success(FoodList.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mDialog.setMessage("Uploaded" + progress+ "%");
                        }
                    });
        }
    }
}
