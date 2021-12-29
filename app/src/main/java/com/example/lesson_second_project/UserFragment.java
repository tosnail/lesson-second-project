package com.example.lesson_second_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserFragment extends Fragment {
    MainActivity activity;
    TextView tvUserTitle, tvUserId;
    EditText edtUserName, edtPassword;
    Button btn_logout, btn_update, btn_delete;
    User user;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_user, container, false);

        tvUserTitle = rootView.findViewById(R.id.tvUserTitle);
        tvUserId   = rootView.findViewById(R.id.tvUserid);
        edtUserName = rootView.findViewById(R.id.edtUsername);
        edtPassword = rootView.findViewById(R.id.edtPassword);

        btn_update = (Button) rootView.findViewById(R.id.btnUpdate);
        btn_logout = (Button) rootView.findViewById(R.id.btnLogout);
        btn_delete = (Button) rootView.findViewById(R.id.btnDelete);

        UserService userService = UserClient.getInstance().getUserService();


        Call<User> call = userService.find(LoginActivity.user_id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();

                assert user != null;
                if (user.getUsername() != null) {
                    tvUserTitle.setText(user.getUsername() + " 님");
                }
                tvUserId.setText(user.getUserid());
                edtUserName.setText(user.getUsername());
                edtPassword.setText(user.getPassword());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        //회원정보 변경
        btn_update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                User user1 = new User();
                user1.setId(user.getId());
                user1.setUserid(tvUserId.getText().toString());
                user1.setUsername(edtUserName.getText().toString());
                user1.setPassword(edtPassword.getText().toString());

                Call<User> call = userService.update(user1);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user3 = response.body();
                        edtUserName.setText(user3.getUsername());
                        edtPassword.setText(user3.getPassword());
                        Toast.makeText(activity, "변경완료", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });

        //로그아웃
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.user_id ="";
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
            }
        });

        //탈퇴
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = userService.delete(user.getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(activity, "삭제완료", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, JoinActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });


        return rootView;
    }
}