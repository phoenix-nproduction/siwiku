package com.example.siwiku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signAwalActivity extends AppCompatActivity {

    Button gotologin, gotosignup;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_awal);

        gotologin = findViewById(R.id.button_gotologin);
        gotosignup = findViewById(R.id.button_gotosignup);

        mAuth = FirebaseAuth.getInstance();

        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signAwalActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        gotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signAwalActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void reload(){
        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            reload();
        }
    }

}