package com.example.lesson_second_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAdd extends AppCompatActivity {
 

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add);

        final EditText etname = (EditText) findViewById(R.id.etname);
        final EditText etdate = (EditText) findViewById(R.id.etdate);
        final EditText etmemo = (EditText) findViewById(R.id.etmemo);
        final EditText etposition = (EditText) findViewById(R.id.etposition);

        final Button btnCancel = (Button) findViewById(R.id.btnCancel);
        final Button btnStart = (Button) findViewById(R.id.btnStart);

        etdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(ItemAdd.this)
                        .inflate(R.layout.datepicker, null);
                final DatePicker datepick = dialogView.findViewById(R.id.datepick);
                AlertDialog.Builder dlg = new AlertDialog.Builder(ItemAdd.this);
                dlg.setTitle("날짜등록");
                dlg.setView(dialogView);
                datepick.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String select = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
//                        Log.d("select ", "select:" + select);
                        etdate.setText(select);
                    }
                });

                dlg.setPositiveButton("등록", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dlg.setNegativeButton("닫기", null);
                dlg.show();


            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etname.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "제품명을 입력하세요.", Toast.LENGTH_SHORT).show();
                }else if (etdate.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "유통기한을 입력하세요.", Toast.LENGTH_SHORT).show();
                }else if (etmemo.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "메모를 입력하세요.", Toast.LENGTH_SHORT).show();
                }else if (etposition.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "냉장고안의 위치를 입력하세요.", Toast.LENGTH_SHORT).show();
                }else{
                    Item itemDto = new Item();
                    itemDto.setItname(etname.getText().toString());
                    itemDto.setItdate(etdate.getText().toString());
                    itemDto.setItmemo(etmemo.getText().toString());
                    itemDto.setItposition(etposition.getText().toString());

                    Long ID = 3L;
                    ItemService itemService = ItemClient.getInstance().getItemService();
                    Call<Item> call = itemService.save(ID, itemDto);
                    call.enqueue(new Callback<Item>() {
                        @Override
                        public void onResponse(Call<Item> call, Response<Item> response) {
                            Intent intent = new Intent(ItemAdd.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Item> call, Throwable t) {

                        }
                    });

                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemAdd.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
