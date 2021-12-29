package com.example.lesson_second_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Button join = (Button) findViewById(R.id.btn_join);
        join.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            final EditText edt_user_id = (EditText) findViewById(R.id.edtUserid);
            final EditText edt_pwd = (EditText) findViewById(R.id.edtPassword);
            String userID = edt_user_id.getText().toString();
            String userPassword = edt_pwd.getText().toString();

            if (userID.equals("")){
                Toast.makeText(JoinActivity.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
            }else if (userPassword.equals("")){
                Toast.makeText(JoinActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
            }else {

                UserService userService = UserClient.getInstance().getUserService();
                User userDto = new User();
                userDto.setUserid(userID);
                userDto.setPassword(userPassword);

                Call<User> call = userService.insert(userDto);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Toast.makeText(getApplicationContext(), "회원가입을 축하합니다!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "이미 있는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
        });

        Button login = (Button) findViewById(R.id.btn_to_login);
        login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        });

    }




}