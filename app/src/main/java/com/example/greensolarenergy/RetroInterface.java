package com.example.greensolarenergy;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetroInterface {

    @POST("/hello")
    Call<Void> execute (@Body HashMap<String, String> map);
    @POST("/megrendelesek")
    Call<Void> addmegrendelesek (@Body  HashMap<String, String> map);


//    Call<Void> execute2 (@Body HashMap<String, String> map);
}
