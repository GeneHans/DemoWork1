package com.example.demowork1.sensor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demowork1.DemoApplication;
import com.example.demowork1.R;
import com.example.demowork1.anim.TestAnimActivity;
import com.example.demowork1.util.LogUtil;

public class SensorTestActivity extends AppCompatActivity implements ISensorCallBack {
    private SensorController sensorController;
    private EditText etTimeInterval;
    private EditText etSpeed;
    private Button btnUpdate;
    long time = 3000;
    double speed = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_test);
        etTimeInterval = findViewById(R.id.et_time);
        etSpeed = findViewById(R.id.et_speed);
        btnUpdate = findViewById(R.id.btn_update);
        try {
            time = Long.parseLong(etTimeInterval.getText().toString());
            speed = Double.parseDouble(etSpeed.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        sensorController = new SensorController(this,
                time, speed, this);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    time = Long.parseLong(etTimeInterval.getText().toString());
                    speed = Double.parseDouble(etSpeed.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    sensorController.updateTimeAndAcceleration(time, speed);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorController.register();
    }

    @Override
    protected void onPause() {
        sensorController.unregister();
        super.onPause();
    }

    @Override
    public void action() {
        LogUtil.Companion.getInstance().toast("摇一摇", DemoApplication.Companion.getMContext());
        Intent intent = new Intent();
        intent.setClass(this, TestAnimActivity.class);
        startActivity(intent);
        finish();
    }
}