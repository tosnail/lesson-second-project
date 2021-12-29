package com.example.lesson_second_project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ItemService {

    @GET("list")
    Call<List<Item>>findAll();

    @POST("insert/{ituser}")
    Call<Item> save(@Path("ituser") Long ituser, @Body Item itemDto);

    @GET("list/{itdate}")
    Call<List<Item>>findByItdate(@Query("itdate") String itdate);

    // 수정
    @PUT("update/{id}")
    Call<Item> update(@Path("id") Long id, @Body Item itemDto);

    // 삭제
    @DELETE("delete/{id}")
    Call<Void> deleteById(@Path("id") Long id);
}
