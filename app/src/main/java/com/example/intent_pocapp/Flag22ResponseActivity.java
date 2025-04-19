package com.example.intent_pocapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.intent_pocapp.databinding.ActivityResponseFlag22Binding;

public class Flag22ResponseActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityResponseFlag22Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityResponseFlag22Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        Intent intent = getIntent();
        Toast.makeText(this, intent.getStringExtra("flag"), Toast.LENGTH_LONG).show();


    }

}