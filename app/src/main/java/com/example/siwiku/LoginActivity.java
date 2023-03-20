package com.example.siwiku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText txtemail, txtpassword;
    TextView signup_login, forgotPassword;
    Button login_login;
    ImageView show_pass, back_sign;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       txtemail = findViewById(R.id.EmailAddress);
       txtpassword = findViewById(R.id.text_password);

       show_pass = findViewById(R.id.show_pass_btn);
       back_sign = findViewById(R.id.back_signAwal);
       login_login = findViewById(R.id.login_login);
       signup_login = findViewById(R.id.signup_login);
       forgotPassword = findViewById(R.id.forgot_password);

       mAuth = FirebaseAuth.getInstance();

        login_login.setOnClickListener(view -> {
            if (txtemail.getText().length()>0 && txtpassword.getText().length()>0){
                login(txtemail.getText().toString(),txtpassword.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(),"Silahkan isi semua data!",Toast.LENGTH_SHORT).show();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_forget,null);
                EditText emailBox = dialogView.findViewById(R.id.emailBox);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userEmail = emailBox.getText().toString();

                        if (TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                            Toast.makeText(LoginActivity.this,"Enter your registered email!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this,"Check your email!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(LoginActivity.this,"Unable to send, Failed!", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                });
                dialogView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                if (dialog.getWindow()!=null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });

        signup_login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
               startActivity(intent);
               finish();
           }
       });

        back_sign.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(LoginActivity.this, signAwalActivity.class);
               startActivity(intent);
               finish();
           }
       });

        show_pass.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

                   if (view.getId()==R.id.show_pass_btn){
                       if (txtpassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                           show_pass.setImageResource(R.drawable.hidden);
                           txtpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                       } else {
                           show_pass.setImageResource(R.drawable.show_password);
                           txtpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                       }
                   }

           }
       });

    }

    private void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult() != null){
                    if (task.getResult().getUser() != null){
                        reload();
                    } else {
                        Toast.makeText(getApplicationContext(),"Login Gagal!",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Login Gagal!",Toast.LENGTH_SHORT).show();
                }
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