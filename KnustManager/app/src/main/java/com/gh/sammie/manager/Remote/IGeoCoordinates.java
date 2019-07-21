package com.gh.sammie.manager.Remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by A.Richard on 21/09/2017.
 */

public interface IGeoCoordinates {
    @GET ("maps/api/geocode/json")
    Call<String> getGeoCode(@Query("address") String address);

    @GET("maps/api/directions/json")
    Call<String> getDirections(@Query("origin") String origin, @Query("destination") String destination);


}
