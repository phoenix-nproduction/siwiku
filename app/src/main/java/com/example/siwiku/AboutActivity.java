package com.example.siwiku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    Animation splash_top, splash_bottom, right_to_left;
    ImageView logo_about,back_button;
    TextView desc_logo, group, member1, member2, member3, member4, member5, member6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        back_button = findViewById(R.id.back_group);

        logo_about = findViewById(R.id.logo);
        desc_logo = findViewById(R.id.desclogo);
        group = findViewById(R.id.group2);
        member1 = findViewById(R.id.member1);
        member2 = findViewById(R.id.member2);
        member3 = findViewById(R.id.member3);
        member4 = findViewById(R.id.member4);
        member5 = findViewById(R.id.member5);
        member6 = findViewById(R.id.member6);

        splash_top = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        splash_bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        right_to_left = AnimationUtils.loadAnimation(this, R.anim.right_to_left);

        back_button.setAnimation(right_to_left);
        logo_about.setAnimation(splash_top);
        desc_logo.setAnimation(splash_top);
        group.setAnimation(splash_bottom);
        member1.setAnimation(splash_bottom);
        member2.setAnimation(splash_bottom);
        member3.setAnimation(splash_bottom);
        member4.setAnimation(splash_bottom);
        member5.setAnimation(splash_bottom);
        member6.setAnimation(splash_bottom);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}