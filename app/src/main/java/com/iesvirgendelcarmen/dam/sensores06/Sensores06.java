package com.iesvirgendelcarmen.dam.sensores06;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Sensores06 extends AppCompatActivity implements SensorEventListener {

    int contador;
    double distancia;
    String proximidad;
    TextView tvProximidad, tvDistancia, tvContador;
    Sensor sensorProximo;
    SensorManager sensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores06);

        distancia = 0;
        proximidad = "LEJOS";

        tvProximidad = findViewById(R.id.proximidad);
        tvDistancia = findViewById(R.id.orientacion);
        tvContador = findViewById(R.id.lecturas);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorProximo = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        runOnUiThread(new CambiaTexto());
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        distancia = event.values[0];
        contador++;
        if (distancia < 1){
            proximidad = "CERCA";
        } else {
            proximidad = "LEJOS";
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class CambiaTexto implements Runnable{

        @Override
        public void run() {
            tvDistancia.setText("" + distancia);
            tvProximidad.setText("" + proximidad);
            tvContador.setText("" + contador);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorProximo, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
