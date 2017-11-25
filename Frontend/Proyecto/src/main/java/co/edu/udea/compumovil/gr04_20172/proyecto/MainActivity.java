package co.edu.udea.compumovil.gr04_20172.proyecto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import co.edu.udea.compumovil.gr04_20172.proyecto.DTOs.Customer;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.AboutFragment;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.place.Detail_Fragment_Place;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.place.Favorite_Fragment;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.user.Login;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.place.MainFragment;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.user.ProfileFragment;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.user.Settings;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AboutFragment.OnFragmentInteractionListener,
        MainFragment.OnFragmentButtonListener {

    Fragment fragment = null;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mFireBase = FirebaseDatabase.getInstance().getReference("Customer");
    private String email;
    private Customer user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        email = getIntent().getStringExtra("email");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);
        final CircularImageView photoND = (CircularImageView)headerLayout.findViewById(R.id.imageViewND);
        final TextView emailND = (TextView) headerLayout.findViewById(R.id.emailND);
        final TextView nameND = (TextView) headerLayout.findViewById(R.id.nameND);

        mFireBase.orderByChild("id").equalTo(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("Aviso","¿Entró?");
                user = dataSnapshot.getValue(Customer.class);
                nameND.setText(user.getUsername()+" "+user.getUserlastname());
                emailND.setText(user.getId());
                Picasso.with(getApplicationContext()).load(user.getPhoto()).into(photoND);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //For me
        fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.contentNavigation, fragment).commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }

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


        boolean fragmentSeleccionado = false;

        if (id == R.id.nav_Main) {
            // Handle the camera action
            fragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.contentNavigation, fragment)
                    .addToBackStack(null)
                    .commit();
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        if (id == R.id.nav_Profile) {
            // Handle the camera action
            fragment = new ProfileFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_Favorite) {
            fragment = new Favorite_Fragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_Setting) {
            fragment = new Settings();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_Logout) {
            this.cerrarSesion();
        } else if (id == R.id.nav_About) {
            fragment = new AboutFragment();
            fragmentSeleccionado = true;
        }

        if (fragmentSeleccionado) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contentNavigation, fragment)
                    .addToBackStack(null)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void cerrarSesion() {
        firebaseAuth.signOut();
        Toast.makeText(MainActivity.this, "Cerrando sesion", Toast.LENGTH_SHORT).show();
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
        fragment = new Detail_Fragment_Place();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentNavigation, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
