package com.example.greensolarenergy;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetroInterface {

    @POST("/addraktar")
    Call<Void> execute (@Body HashMap<String, String> map);

    @POST("/addmegrendelesek")
    Call<Void> addmegrendelesek (@Body  HashMap<String, String> map);

    @POST("/addtermekrendeles")
    Call<Void> addtermekrendeles (@Body  HashMap<String, String> map);

    @POST("/deleteraktardata")
    Call<Void> deleteraktardata (@Body  HashMap<String, String> map);

    @POST("/deletemegrendelesekdata")
    Call<Void> deletemegrendelesekdata (@Body  HashMap<String, String> map);

    @POST("/updatefolyamatba")
    Call<Void> updatefolyamatba (@Body  HashMap<String, String> map); //megrendelesek

    @POST("/updatelezarva")
    Call<Void> updatelezarva (@Body  HashMap<String, String> map);

    @POST("/updateprice")
    Call<Void> updateprice (@Body  HashMap<String, String> map); //raktar

}
