package com.example.sqlite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateDeleteActivity extends AppCompatActivity {

    private UserModel userModel;
    private EditText etlab, etrut, etname, etdescr;
    private Button btnupdate, btndelete;
    private DatabaseHelper databaseHelper;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        Intent intent = getIntent();
        userModel = (UserModel) intent.getSerializableExtra("user");

        databaseHelper = new DatabaseHelper(this);

        etlab = findViewById(R.id.etlab);
        etrut = findViewById(R.id.etrut);
        etname = findViewById(R.id.etname);
        etdescr = findViewById(R.id.etdescr);

        etlab.setEnabled(false);
        etrut.setEnabled(false);
        etname.setEnabled(false);

        btndelete = findViewById(R.id.btndelete);
        btnupdate = findViewById(R.id.btnupdate);

        etlab.setText(userModel.getLab());
        etrut.setText(userModel.getRut());
        etname.setText(userModel.getName());
        etdescr.setText(userModel.getDescr());

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.updateDescription(userModel.getId(), etdescr.getText().toString());
                Toast.makeText(UpdateDeleteActivity.this, "ACTUALIZACIÓN EXITOSA!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateDeleteActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteUSer(userModel.getId());
                Toast.makeText(UpdateDeleteActivity.this, "ELIMINADO CORRECTAMENTE!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateDeleteActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
