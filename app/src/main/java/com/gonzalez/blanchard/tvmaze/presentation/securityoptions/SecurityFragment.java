package com.gonzalez.blanchard.tvmaze.presentation.securityoptions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gonzalez.blanchard.tvmaze.config.config;
import com.gonzalez.blanchard.tvmaze.databinding.FragmentSecurityBinding;

public class SecurityFragment extends Fragment {

    private FragmentSecurityBinding binding;

    SwitchCompat switchEnabledPin;
    EditText inputPin;
    AppCompatButton btnSavePin;
    View root;
    private boolean isEnabled = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SecurityViewModel securityViewModel =
                new ViewModelProvider(this).get(SecurityViewModel.class);

        binding = FragmentSecurityBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        inputPin = binding.inputPin;
        btnSavePin = binding.btnSavePin;
        switchEnabledPin = binding.switchEnabledPin;
        switchEnabledPin.setTextOn("Enabled"); // displayed text of the Switch whenever it is in checked or on state
        switchEnabledPin.setTextOff("Disabled"); // displayed text of the Switch whenever it is in unchecked i.e. off state

        this.isEnabled = checkPinEnabled();
        initPin();

        switchEnabledPin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isEnabled = isChecked;
            initPin();
            if(!isEnabled){
                disablePin();
            }
        });


        btnSavePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePinAction();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initPin(){
        if(isEnabled == true){
            switchEnabledPin.setText("Enabled");
            switchEnabledPin.setChecked(true);
            inputPin.setEnabled(true);
            btnSavePin.setEnabled(true);
        }else{
            switchEnabledPin.setText("Disabled");
            switchEnabledPin.setChecked(false);
            inputPin.setEnabled(false);
            btnSavePin.setEnabled(false);
        }
    }

    public void savePinAction() {
        // Do something in response to button click
        if(inputPin.getText().length() < 4){
            Toast.makeText(root.getContext(), "Pin must have 4 digits. Current length " + inputPin.getText().length(), Toast.LENGTH_SHORT).show();
        }else{
            savePin();
        }
    }

    public boolean checkPinEnabled(){
        SharedPreferences sharedPref = root.getContext().getSharedPreferences(config.PREF_ROOT, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(config.PREF_PIN_ENABLED, false);
    }

    public void savePin(){
        SharedPreferences sharedPref = root.getContext().getSharedPreferences(config.PREF_ROOT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(config.PREF_PIN_VALUE, inputPin.getText().toString());
        editor.putBoolean(config.PREF_PIN_ENABLED, true);
        editor.apply();
        Toast.makeText(root.getContext(), "PIN Enabled", Toast.LENGTH_SHORT).show();
    }

    public void disablePin(){
        SharedPreferences sharedPref = root.getContext().getSharedPreferences(config.PREF_ROOT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(config.PREF_PIN_VALUE, "0000");
        editor.putBoolean(config.PREF_PIN_ENABLED, false);
        editor.apply();
        Toast.makeText(root.getContext(), "PIN Disabled", Toast.LENGTH_SHORT).show();
    }
}