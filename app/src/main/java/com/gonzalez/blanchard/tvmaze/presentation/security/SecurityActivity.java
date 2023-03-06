package com.gonzalez.blanchard.tvmaze.presentation.security;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.gonzalez.blanchard.tvmaze.MainActivity;
import com.gonzalez.blanchard.tvmaze.R;
import com.gonzalez.blanchard.tvmaze.config.config;
import com.gonzalez.blanchard.tvmaze.databinding.ActivitySecurityBinding;

public class SecurityActivity extends AppCompatActivity {

    private ActivitySecurityBinding binding;

    EditText inputPin;
    AppCompatButton btnSavePin;
    ImageButton imageButtonFingerPrint;
    TextView txtOr, labelFingerprint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        binding = ActivitySecurityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inputPin = binding.inputPin;
        btnSavePin = binding.btnSavePin;

        imageButtonFingerPrint = binding.imageButtonFingerPrint;
        txtOr = binding.txtOr;
        labelFingerprint = binding.labelFingerprint;

        btnSavePin.setOnClickListener(v -> {
            if(inputPin.getText().length() < 4){
                Toast.makeText(SecurityActivity.this, "PIN must have 4 digits", Toast.LENGTH_SHORT).show();
            }else{
                VerifyPin();
            }
        });

        checkBiometricSupport();

    }

    private void VerifyPin(){
        SharedPreferences sharedPref = SecurityActivity.this.getSharedPreferences(config.PREF_ROOT, Context.MODE_PRIVATE);
        String pin =  sharedPref.getString(config.PREF_PIN_VALUE, "0000");
        if(pin.equals(inputPin.getText().toString())){
            goToMainScreen();
        }else{
            Toast.makeText(SecurityActivity.this, "Invalid PIN", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToMainScreen(){
        Intent intent = new Intent(SecurityActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("checkPin", 0);
        intent.putExtras(bundle);startActivity(intent);
        finish();
    }

    private Boolean checkBiometricSupport() {

        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        PackageManager packageManager = this.getPackageManager();

        if (!keyguardManager.isKeyguardSecure()) {
            notifyUser("Lock screen security not enabled in Settings");
            imageButtonFingerPrint.setVisibility(View.GONE);
            txtOr.setVisibility(View.GONE);
            labelFingerprint.setVisibility(View.GONE);
            return false;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint authentication permission not enabled");
            imageButtonFingerPrint.setVisibility(View.GONE);
            txtOr.setVisibility(View.GONE);
            labelFingerprint.setVisibility(View.GONE);
            return false;
        }

        if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT))
        {
            imageButtonFingerPrint.setVisibility(View.VISIBLE);
            txtOr.setVisibility(View.VISIBLE);
            labelFingerprint.setVisibility(View.VISIBLE);
            return true;
        }

        return true;
    }

    private void notifyUser(String message) {
        Toast.makeText(SecurityActivity.this,
                message,
                Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private BiometricPrompt.AuthenticationCallback getAuthenticationCallback() {

        return new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              CharSequence errString) {
                notifyUser("Authentication error: " + errString);
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationHelp(int helpCode,
                                             CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }

            @Override
            public void onAuthenticationSucceeded(
                    BiometricPrompt.AuthenticationResult result) {
                goToMainScreen();
                notifyUser("Authentication Succeeded");
                super.onAuthenticationSucceeded(result);
            }
        };
    }


    private CancellationSignal cancellationSignal;
    private CancellationSignal getCancellationSignal() {

        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            @Override
            public void onCancel() {
                notifyUser("Cancelled via signal");
            }
        });
        return cancellationSignal;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void authenticateUser(View view) {
        BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)
                .setTitle("Fingerprint authentication")
                .setSubtitle("Authentication is required to continue")
                .setDescription("This app uses biometric authentication to protect your data.")
                .setNegativeButton("Cancel", this.getMainExecutor(),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notifyUser("Authentication cancelled");
                            }
                        })
                .build();

        biometricPrompt.authenticate(getCancellationSignal(), getMainExecutor(),
                getAuthenticationCallback());
    }



}