package com.example.lesson_second_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListFragment extends Fragment {

    ItemAdapter itemAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager manager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);

        ItemService itemService=ItemClient.getInstance().getItemService();
        Call<List<Item>> call=itemService.findAll();

        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                List<Item> itemList=response.body();
                recyclerView= (RecyclerView) rootView.findViewById(R.id.recyclerView);
                manager=new LinearLayoutManager(getContext());
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(manager);
                recyclerView.scrollToPosition(0);

                itemAdapter=new ItemAdapter(itemList);
                recyclerView.setAdapter(itemAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }

        });

        return rootView;
    }

}