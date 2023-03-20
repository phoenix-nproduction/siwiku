package com.example.siwiku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NavigatorActivity extends AppCompatActivity {

    ViewPager sliderViewPager;
    LinearLayout dotIndicator;
    ViewPagerAdapter viewPagerAdapter;
    Button backbutton, skipbutton, nextbutton;
    TextView[] dots;

    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setDotIndicator(position);

            if (position>0){
                backbutton.setVisibility(View.VISIBLE);
            } else {
                backbutton.setVisibility(View.INVISIBLE);
            }
            if (position==2){
                nextbutton.setText("Finish");
            }else {
                nextbutton.setText("Next");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);
        backbutton = findViewById(R.id.backbutton);
        nextbutton = findViewById(R.id.nextbutton);
        skipbutton = findViewById(R.id.skipbutton);

        backbutton.setOnClickListener(new View.OnClickListener(){

            /**
             * @param view
             */
            @Override
            public void onClick(View view) {
                if (getItem(0)>0){
                    sliderViewPager.setCurrentItem(getItem(-1),true);
                }
            }
        });

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getItem(0)<2){
                    sliderViewPager.setCurrentItem(getItem(1),true);
                } else {
                    Intent intent=new Intent(NavigatorActivity.this,GetStartedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        skipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NavigatorActivity.this,GetStartedActivity.class);
                startActivity(intent);
                finish();
            }
        });

        sliderViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        dotIndicator = (LinearLayout) findViewById(R.id.dotIndicator);

        viewPagerAdapter = new ViewPagerAdapter(this);
        sliderViewPager.setAdapter(viewPagerAdapter);

        setDotIndicator(0);
        sliderViewPager.addOnPageChangeListener(viewPagerListener);
    }

    public void setDotIndicator(int position){
        dots = new TextView[3];
        dotIndicator.removeAllViews();

        for (int i = 0; i< dots.length;i++){
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226",Html.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.grey, getApplicationContext().getTheme()));
            dotIndicator.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.btn_onboarding,getApplicationContext().getTheme()));
    }
    private int getItem(int i){
        return sliderViewPager.getCurrentItem()+i;
    }
}