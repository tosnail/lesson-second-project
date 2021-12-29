package com.example.lesson_second_project;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface UserService {

    @POST("insert")
    Call<User> insert(@Body User user);

    @GET("find/{userid}")
    Call<User> find(@Path("userid") String userid);

    @PUT("update")
    Call<User> update(@Body User user);

    @DELETE("delete/{id}")
    Call<Void> delete(@Path("id") Long id);

}
