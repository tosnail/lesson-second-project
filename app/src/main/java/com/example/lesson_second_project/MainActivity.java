package com.example.lesson_second_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private Fragment listFragment, calendarFragment, userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_search);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);

        listFragment = new ListFragment();
        calendarFragment = new CalenderFragment();
        userFragment = new UserFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, listFragment).commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener listener =  new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.bottom_nav_list_button:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, listFragment).commitAllowingStateLoss();
                    return true;

                case R.id.bottom_nav_calendar_button:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, calendarFragment).commitAllowingStateLoss();
                    return true;

                case R.id.bottom_nav_add_button:
                    Intent intent = new Intent(getApplicationContext(), ItemAdd.class);
                    startActivity(intent);
                    return true;

                case R.id.bottom_nav_user_button:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, userFragment).commitAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };
}