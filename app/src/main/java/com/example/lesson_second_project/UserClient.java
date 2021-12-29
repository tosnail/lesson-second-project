package com.example.lesson_second_project;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserClient {
    private static UserClient instance;
    private final UserService userService;

    private UserClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.219.101:8812/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userService = retrofit.create(UserService.class);

    }

    public static UserClient getInstance(){
        if (instance==null){
            instance = new UserClient();
        }
        return instance;
    }

    public UserService getUserService(){
        return userService;
    }

}
