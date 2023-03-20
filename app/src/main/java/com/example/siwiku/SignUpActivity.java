package com.example.siwiku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class SignUpActivity extends AppCompatActivity {

    EditText txtfullname,txtnickname,txtemail,txtpassword,txtconfirmpassword;
    ImageView show_pass, show_confirm, back_sign;
    Button signup_signup;
    TextView login_signup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtfullname = findViewById(R.id.FullName_signupActivity);
        txtnickname = findViewById(R.id.Nickname_signupActivity);
        txtemail = findViewById(R.id.Email_signupActivity);
        txtpassword = findViewById(R.id.password_signupActivity);
        txtconfirmpassword = findViewById(R.id.passwordconfirm_signupActivity);

        show_confirm = findViewById(R.id.btn_show_confirm);
        show_pass = findViewById(R.id.show_pass_btn_signupActivity);
        back_sign = findViewById(R.id.back_signupActivity);

        signup_signup = findViewById(R.id.signup_btn_signupActivity);
        login_signup = findViewById(R.id.signin_signupActivity);

        mAuth = FirebaseAuth.getInstance();


        signup_signup.setOnClickListener(view -> {
            if (txtfullname.getText().length()>0 && txtnickname.getText().length()>0 && txtemail.getText().length()>0 && txtpassword.getText().length()>0 && txtconfirmpassword.getText().length()>0){
                if (txtpassword.getText().toString().equals(txtconfirmpassword.getText().toString())){
                    register(txtfullname.getText().toString(),txtnickname.getText().toString(),txtemail.getText().toString(),txtpassword.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(),"Password tidak sama!",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),"Silahkan isi semua data!",Toast.LENGTH_SHORT).show();
            }
        });


        login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        back_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, signAwalActivity.class);
                startActivity(intent);
                finish();
            }
        });

        show_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId()==R.id.show_pass_btn_signupActivity){
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

        show_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId()==R.id.btn_show_confirm){
                    if (txtconfirmpassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                        show_confirm.setImageResource(R.drawable.hidden);
                        txtconfirmpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        show_confirm.setImageResource(R.drawable.show_password);
                        txtconfirmpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }
            }
        });
    }

    private void register(String fullname, String nickname, String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    String uid = user.getUid();
                    HashMap<Object,String> hashMap = new HashMap<>();
                    hashMap.put("uid",uid);
                    hashMap.put("name",fullname);
                    hashMap.put("nickname",nickname);
                    hashMap.put("email",email);
                    hashMap.put("password",password);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("user");
                    reference.child(uid).setValue(hashMap);

                    startActivity(new Intent(SignUpActivity.this,DashboardActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(),task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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