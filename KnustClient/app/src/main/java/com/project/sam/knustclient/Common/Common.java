package com.project.sam.knustclient.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.project.sam.knustclient.Model.Request;
import com.project.sam.knustclient.Model.User;

import java.net.InetAddress;

/**
 * Created by A.Richard on 03/09/2017.
 */

public class Common {

    public static User currentUser;
    private static final  String BASE_URL = "https://fcm.googleapis.com/";

    public static Request currentRequest;

//    public static APISERVICE getFCMService()
//    {
////        return RetrofitClient.getClient(BASE_URL).create(APISERVICE.class);
//    }


    public  static  String convertCodeToStatus(String status)

    {
        if (status.equals("0"))
            return "Placed";

        else if (status.equals("1"))
            return "Order received";

        else if (status.equals("2"))
            return "On my way";

        else return "Delivered";// shipped for other  items and also add order received  later as if I need to
    }


    public static final String DELETE = "Delete";
    public static final String VIEW = "View Order";

    public static final String USER_KEY  = "User";
    public static final String PWD_KEY = "Password";

    public static boolean isNoInternet() {

         {
            try {
                InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
                return !ipAddr.equals("");

            } catch (Exception e) {
                return false;
            }
        }
    }



     public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager  =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null)
            {
                for (int i = 0;i<info.length;i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }

        return false;
    }






}
