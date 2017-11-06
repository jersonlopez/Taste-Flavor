package co.edu.udea.compumovil.gr04_20172.proyecto.views;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.edu.udea.compumovil.gr04_20172.proyecto.R;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.user.Login;

public class SplashScreen extends AppCompatActivity {

    static public int TIME_TO_WAIT_SPLASH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
                finish();

            }
        },TIME_TO_WAIT_SPLASH );
    }
}
