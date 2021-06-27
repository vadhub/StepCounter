package com.vad.stepcounter.fragments.stepscounted;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.vad.stepcounter.R;

public class SensorBackgroundCounter extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private RemoteViews view;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor countStep = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        view = new RemoteViews(getPackageName(), R.layout.step_view_app_widget);

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
        updateWidget(steps);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void updateWidget(float steps){
        view.setTextViewText(R.id.appwidget_text, String.valueOf(steps));
        ComponentName widget = new ComponentName(this, StepViewAppWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(widget, view);
    }
}
