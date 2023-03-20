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

public class GetStartedActivity extends AppCompatActivity {

    Button startButton;
    ImageView slide;
    TextView title, desctitle;
    Animation splash_top, splash_bottom, fade_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        slide = findViewById(R.id.sliderImageStarted);
        title = findViewById(R.id.sliderTitleStarted);
        desctitle = findViewById(R.id.sliderDescStarted);
        startButton = findViewById(R.id.startButton);

        splash_top = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        splash_bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        fade_in = AnimationUtils.loadAnimation(this,R.anim.fade_in);

        slide.setAnimation(splash_top);
        title.setAnimation(splash_bottom);
        desctitle.setAnimation(splash_bottom);
        startButton.setAnimation(fade_in);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetStartedActivity.this, signAwalActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}