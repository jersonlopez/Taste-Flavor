package co.edu.udea.compumovil.gr04_20172.proyecto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import co.edu.udea.compumovil.gr04_20172.proyecto.views.AboutFragment;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.SettingFragment;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.place.Detail_Fragment_Place;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.place.Favorite_Fragment;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.user.Login;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.place.MainFragment;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.user.ProfileFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AboutFragment.OnFragmentInteractionListener, SettingFragment.OnFragmentInteractionListener, MainFragment.OnFragmentButtonListener{

    Fragment fragment=null;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        //For me
        fragment=new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentNavigation, fragment).commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        boolean fragmentSeleccionado=false;

        if (id == R.id.nav_Main) {
            // Handle the camera action
            fragment=new MainFragment();
            fragmentSeleccionado=true;
        }if (id == R.id.nav_Profile) {
            // Handle the camera action
            fragment=new ProfileFragment();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_Favorite) {
            fragment=new Favorite_Fragment();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_Setting) {
            fragment=new SettingFragment();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_Logout) {
            this.cerrarSesion();
        } else if (id == R.id.nav_About) {
            fragment=new AboutFragment();
            fragmentSeleccionado=true;
        }

        if (fragmentSeleccionado){
            getSupportFragmentManager().beginTransaction().replace(R.id.contentNavigation, fragment).addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cerrarSesion(){
        firebaseAuth.signOut();
        Toast.makeText(MainActivity.this,"Cerrando sesion", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onFragmentClickButton(String id) {
        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        fragment=new Detail_Fragment_Place();
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager=getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentNavigation,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}