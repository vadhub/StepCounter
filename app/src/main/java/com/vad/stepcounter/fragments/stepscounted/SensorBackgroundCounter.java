package com.vad.stepcounter.fragments.stepscounted;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SensorBackgroundCounter extends Service implements SensorEventListener {

    private SensorManager mSensorManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor countStep = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if(countStep!=null){
            mSensorManager.registerListener(this, countStep, SensorManager.SENSOR_DELAY_UI);
        }else{
            Toast.makeText(this, "This device haven`t step sensor! Sorry...", Toast.LENGTH_SHORT).show();
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float steps = sensorEvent.values[0];
        Toast.makeText(this, ""+steps, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }
}
