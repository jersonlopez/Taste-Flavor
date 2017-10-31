package co.edu.udea.compumovil.gr04_20172.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin;
    TextView tRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin=(Button) findViewById(R.id.buttonLogin);
        tRegister=(TextView) findViewById(R.id.logUp);
        btnLogin.setOnClickListener(this);
        tRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLogin:
                Intent intent=new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.logUp:
                Intent intent1=new Intent(Login.this, Register.class);
                startActivity(intent1);
                break;
        }
    }
}
