package com.gonzalez.blanchard.tvmaze.presentation.security;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gonzalez.blanchard.tvmaze.MainActivity;
import com.gonzalez.blanchard.tvmaze.R;
import com.gonzalez.blanchard.tvmaze.config.config;
import com.gonzalez.blanchard.tvmaze.databinding.ActivityDetailEpisodeBinding;
import com.gonzalez.blanchard.tvmaze.databinding.ActivitySecurityBinding;

public class SecurityActivity extends AppCompatActivity {

    private ActivitySecurityBinding binding;

    EditText inputPin;
    AppCompatButton btnSavePin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        binding = ActivitySecurityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inputPin = binding.inputPin;
        btnSavePin = binding.btnSavePin;

        btnSavePin.setOnClickListener(v -> {
            if(inputPin.getText().length() < 4){
                Toast.makeText(SecurityActivity.this, "PIN must have 4 digits", Toast.LENGTH_SHORT).show();
            }else{
                VerifyPin();
            }
        });
    }

    private void VerifyPin(){
        SharedPreferences sharedPref = SecurityActivity.this.getSharedPreferences(config.PREF_ROOT, Context.MODE_PRIVATE);
        String pin =  sharedPref.getString(config.PREF_PIN_VALUE, "0000");
        if(pin.equals(inputPin.getText().toString())){
            Intent intent = new Intent(SecurityActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("checkPin", 0);
            intent.putExtras(bundle);startActivity(intent);
            finish();
        }else{
            Toast.makeText(SecurityActivity.this, "Invalid PIN", Toast.LENGTH_SHORT).show();
        }
    }
}