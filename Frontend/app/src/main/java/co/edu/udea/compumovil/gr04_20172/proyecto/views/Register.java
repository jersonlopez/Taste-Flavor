package co.edu.udea.compumovil.gr04_20172.proyecto.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import co.edu.udea.compumovil.gr04_20172.proyecto.MainActivity;
import co.edu.udea.compumovil.gr04_20172.proyecto.R;

public class Register extends AppCompatActivity implements View.OnClickListener {

    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = (Button) findViewById(R.id.buttonSignup);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSignup:
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
