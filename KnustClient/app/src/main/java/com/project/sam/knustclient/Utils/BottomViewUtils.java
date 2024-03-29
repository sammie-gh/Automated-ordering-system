package com.project.sam.knustclient.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.project.sam.knustclient.AboutActivity;
import com.project.sam.knustclient.Cart;
import com.project.sam.knustclient.MainActivity;
import com.project.sam.knustclient.OrderStatus;
import com.project.sam.knustclient.R;
import com.project.sam.knustclient.TableTypeSelection;
import com.project.sam.knustclient.TablesGrid.Table2;

/**
 * Created by A.Richard on 18/09/2017.
 */

public class BottomViewUtils {

    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx) {
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");
//        bottomNavigationViewEx.enableAnimation(false);
//        bottomNavigationViewEx.enableItemShiftingMode(false);
//        bottomNavigationViewEx.enableShiftingMode(false);
//        bottomNavigationViewEx.setTextVisibility(true);

    }

    public  static void enableNavigation (final Context context, final Activity callingActivity, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_menu:
                        Intent intent1 = new Intent(context, MainActivity.class);//ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        callingActivity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                        break;

                    case R.id.ic_cart:
                            Intent intent2 = new Intent(context, Cart.class);//ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        callingActivity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                        break;


                    case R.id.nav_orders:
                        Intent intent4 = new Intent(context, Table2.class);//ACTIVITY_NUM = 2
                        context.startActivity(intent4);
                        callingActivity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                        break;



                }

                return false;
            }
        });

    }
}

