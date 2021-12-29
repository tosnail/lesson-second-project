package com.example.lesson_second_project;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemClient {
    private static ItemClient  instance;
    private final ItemService itemService;


    public ItemClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.219.101:8812/item/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        itemService = retrofit.create(ItemService.class);

    }

    public static ItemClient getInstance(){
        if(instance == null){
            instance = new ItemClient();
        }
        return  instance;
    }
    public  ItemService getItemService(){
        return itemService;
    }
}
