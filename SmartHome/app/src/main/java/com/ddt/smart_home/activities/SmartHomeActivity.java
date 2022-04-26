package com.ddt.smart_home.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.ddt.smart_home.R;
import com.ddt.smart_home.constraints.AirConditionConstraint;
import com.ddt.smart_home.constraints.FanSpeedConstraint;
import com.ddt.smart_home.constraints.LightConstraint;

public class SmartHomeActivity extends AppCompatActivity {
    ImageView imgViewFan1, imgViewLight1, imgViewLight2, imgViewAir;
    SeekBar seekBar;

    private int valueFan = FanSpeedConstraint.OFF;
    private String valueLight1 = LightConstraint.ON;
    private String valueLight2 = LightConstraint.OFF;
    private String valueAirCondition = AirConditionConstraint.HOT;
    private int valueSeekBar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_home);

        // binding
        this.binding();
        this.loadingStored();

        imgViewFan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valueFan == FanSpeedConstraint.OFF) {
                    valueFan = FanSpeedConstraint.LOW_SPEED;
                }
                else if (valueFan == FanSpeedConstraint.LOW_SPEED) {
                    valueFan = FanSpeedConstraint.MAX_SPEED;
                }
                else {
                    valueFan = FanSpeedConstraint.OFF;
                }
                runFan(valueFan);
            }
        });

        imgViewLight1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("stored", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                valueLight1 = valueLight1.equals(LightConstraint.OFF) ? LightConstraint.ON : LightConstraint.OFF;

                editor.putString("valueLight1", valueLight1);
                editor.apply();
                imgViewLight1.setImageResource(defineImageResource(valueLight1));
            }
        });

        imgViewLight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("stored", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                valueLight2 = valueLight2.equals(LightConstraint.OFF) ? LightConstraint.ON : LightConstraint.OFF;

                editor.putString("valueLight2", valueLight2);
                editor.apply();
                imgViewLight2.setImageResource(defineImageResource(valueLight2));
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                System.out.println(">>> progress: " + i);

                SharedPreferences sharedPreferences = getSharedPreferences("stored", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (i <= 20) {
                    valueAirCondition = AirConditionConstraint.HOT;
                }
                else if ( i > 20 && i < 80) {
                    valueAirCondition = AirConditionConstraint.NORMAL;
                }
                else {
                    valueAirCondition = AirConditionConstraint.COOL;
                }
                imgViewAir.setImageResource(defineImageResource_AirCondition(valueAirCondition));
                editor.putString("valueAirCondition", valueAirCondition);
                editor.putInt("valueSeekBar", i);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void loadingStored() {
        SharedPreferences sharedPreferences= this.getSharedPreferences("stored", Context.MODE_PRIVATE);
        if(sharedPreferences != null) {

            valueFan = sharedPreferences.getInt("valueFan", FanSpeedConstraint.OFF);
            valueLight1 = sharedPreferences.getString("valueLight1", "");
            valueLight2 = sharedPreferences.getString("valueLight2", "");
            valueAirCondition = sharedPreferences.getString("valueAirCondition", "");
            valueSeekBar = sharedPreferences.getInt("valueSeekBar", 1);

            runFan(valueFan);
            System.out.println(">>> value light1: " + valueLight1);
            System.out.println(">>> value light2: " + valueLight2);

            seekBar.setProgress(valueSeekBar);
            imgViewAir.setImageResource(this.defineImageResource_AirCondition(valueAirCondition));
            imgViewLight1.setImageResource(this.defineImageResource(valueLight1));
            imgViewLight2.setImageResource(this.defineImageResource(valueLight2));
        } else {

        }
    }

    private int defineImageResource_AirCondition(String statusAirCondition) {
        if (statusAirCondition.equals(AirConditionConstraint.NORMAL)) {
            return R.drawable.bth;
        }
        else if (statusAirCondition.equals(AirConditionConstraint.HOT)) {
            return R.drawable.nong;
        }
        else {
            return R.drawable.lanh;
        }
    }

    private int defineImageResource(String statusLight) {
        if (statusLight.equals(LightConstraint.OFF)) {
            return R.drawable.tat_den;
        }
        else {
            return R.drawable.sang_den;
        }
    }

    private void runFan(int valueFan) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("stored", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("valueFan", valueFan);
        editor.apply();
        RotateAnimation rotate = new RotateAnimation(0, valueFan, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setRepeatMode(Animation.RESTART);
        rotate.setInterpolator(new LinearInterpolator());

        this.imgViewFan1.startAnimation(rotate);
    };

    private void binding() {
        this.imgViewFan1 = findViewById(R.id.imgViewFan1);
        this.imgViewLight1 = findViewById(R.id.imgViewLight1);
        this.imgViewLight2 = findViewById(R.id.imgViewLight2);
        this.seekBar = findViewById(R.id.seekBar2);
        this.imgViewAir = findViewById(R.id.imgViewAir);
    }
}