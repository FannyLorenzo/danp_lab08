package com.example.android.posiciong9;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor sensorAccelerometer;
    Sensor sensorGravity_;
    Sensor sensorMagneticFiel;
    SensorEventListener sensorEventListener;

    private TextView position;
    private String txtposition="";
    private TextView situacion;
    private String txtsituacion="";
    int whip=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // sensores
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorGravity_ = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        position = (TextView) findViewById(R.id.posicion);
        situacion = (TextView) findViewById(R.id.situacion);

    if(sensorAccelerometer ==null) {
            System.out.println(" Su dispositivo no cuenta con el sensor ACCELEROMETER");
            finish();
        }
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                double li = 5;
                double lj = 10;
                double g = 9.80665;
                txtposition = "";

                // b. manejando la aceleración total (lineal + gravedad = sensor acelerómetro)
                float x=sensorEvent.values[0];
                float y=sensorEvent.values[1];
                float z=sensorEvent.values[2];

                txtposition = "X: "+  (double)(Math.round(x * 100.0) / 100.0) + " Y: "+
                                (double)(Math.round(y * 100.0) / 100.0) +" Z: "+
                                (double)(Math.round(z * 100.0) / 100.0);
                System.out.println(x+ " , "+ y + " , "+z);
                position.setText(txtposition);

                // c. manejando la caida libre;
                if(z>g || z<-g|| y >g || y <-g || x >g || x <-g) {
                    txtsituacion = "caidaa¡¡¡";
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }else if(z>li && z<=lj) {
                    //whip++;
                    txtsituacion = ("echado - boca arriba");
                    getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                }else if(z<-li && z<=-lj) {
                    //whip++;
                    txtsituacion =("echado - boca abajo");
                    getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                }else if(x>=0 && x<=li && y >0 && y <=lj ){ //&& whip==0
                    //whip++;
                    txtsituacion =(" sostenido - parado");
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                }else if(x>=-li && x<=0 && y >-lj && y <=0 ){
                    //whip++;
                    txtsituacion =(" sostenido - de cabeza");
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                }else if(x>=-lj && x<=0 && y >0 && y <=li ){
                    //whip++;
                    txtsituacion =("sostenido - de costado - derecha");
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                }else if(x>=0 && x<=lj && y >-li && y <=0 ){
                    //whip++;
                    txtsituacion =("sostenido - de costado - izquierda");
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                }else if(x<-li ) {
                    //whip++;
                    txtsituacion =("inclinado - derecha");
                    getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                }else if(x>li ) {
                    //whip++;
                    txtsituacion =("inclinado - izquierda");
                    getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                }
                System.out.println(">> Situacion: "+txtsituacion);
                situacion.setText(txtsituacion);

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

    }

    private void start(){
        sensorManager.registerListener(sensorEventListener, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    private void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }
    protected void onPause(){
        stop();
        super.onPause();
    }
    protected void onResume(){
        start();
        super.onResume();
    }
}