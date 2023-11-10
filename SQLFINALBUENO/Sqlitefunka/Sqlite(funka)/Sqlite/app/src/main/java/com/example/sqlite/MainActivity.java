package com.example.sqlite;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private boolean isPhoneUpwards = false;
    private Button btnStore, btnGetall;
    private EditText etrut, etname, etdescr;
    private Spinner etlab;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        btnStore = findViewById(R.id.btnstore);
        btnGetall = findViewById(R.id.btnget);
        etlab = findViewById(R.id.etlab);
        etrut = findViewById(R.id.etrut);
        etname = findViewById(R.id.etname);
        etdescr = findViewById(R.id.etdescr);

        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSaveButton();
            }
        });

        btnGetall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetAllUsersActivity.class);
                startActivity(intent);
            }
        });


        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        if (sensorManager != null) {
            Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (accelerometer != null) {
                sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float yValue = event.values[1];

        if (yValue < -8.5 && !isPhoneUpwards) {

            isPhoneUpwards = true;
        } else if (yValue > 8.7 && isPhoneUpwards) {

            isPhoneUpwards = false;

            onClickSaveButton();
        }
    }

    private void onClickSaveButton() {
        if (validateInputs()) {
            databaseHelper.addUserDetail(
                    etlab.getSelectedItem().toString(),
                    etrut.getText().toString(),
                    etname.getText().toString(),
                    etdescr.getText().toString()
            );
            etlab.setSelection(0);
            etrut.setText("");
            etname.setText("");
            etdescr.setText("");
            Toast.makeText(MainActivity.this, "Datos grabados!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Por favor, completa todos los campos correctamente.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInputs() {
        String rut = etrut.getText().toString();
        String name = etname.getText().toString();
        String descr = etdescr.getText().toString();


        if (!Validations.validarRut(rut)) {
            etrut.setError("RUT no válido");
            return false;
        }


        if (!Validations.validarNombre(name)) {
            etname.setError("Nombre no válido");
            return false;
        }


        if (!Validations.validarDescripcion(descr)) {
            etdescr.setError("Descripción no válida");
            return false;
        }

        return true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
