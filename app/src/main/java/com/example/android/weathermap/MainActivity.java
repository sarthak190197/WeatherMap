package com.example.android.weathermap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView;
        imageView = (ImageView) findViewById(R.id.image);
//        Glide.with(this).load("http://openweathermap.org/img/w/50d.png").into(imageView);
        Picasso.with(this).load("http://openweathermap.org/img/w/50d.png").placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView);

    }
}
