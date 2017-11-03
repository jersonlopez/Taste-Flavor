package co.edu.udea.compumovil.gr04_20172.proyecto.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import co.edu.udea.compumovil.gr04_20172.proyecto.MainActivity;
import co.edu.udea.compumovil.gr04_20172.proyecto.R;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private String email;
    private int type;

    private FirebaseAuth mAuth;
    private DatabaseReference mFireBase = FirebaseDatabase.getInstance().getReference();
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private FirebaseUser customer;
    //private Customer user;

    static final int REQUEST_IMAGE_GET = 101;
    private Button btn_choose_image;
    private Button btnsave;
    private static String routeDowload;
    private ImageView myImageView;
    private EditText eName, eLastname, eBorn, eDirection, eEmail, ePassword, ecPassword, ePhone, eCity;
    private String textName, textLastname, textBorn, textDirection, textEmail, textPassword, textPhone, textCity;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String textGender = "";
    private String textImage = "";
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myImageView = (ImageView) findViewById(R.id.img_show);
        btn_choose_image = (Button) findViewById(R.id.buttonSelectImage);
        btn_choose_image.setOnClickListener(this);
        btnsave = (Button) findViewById(R.id.buttonSignup);
        eName = (EditText) findViewById(R.id.editTextNameRegister);
        eLastname = (EditText) findViewById(R.id.editTextLastnameRegister);
        eDirection = (EditText) findViewById(R.id.editTextUbication);
        btnsave.setOnClickListener(this);
    }

    public void downloadUserGoogle() {
        //descargar datos si es logueado con Google

        //si es nuevo
        String name, lastname, uri;
        name = getIntent().getStringExtra("name");
        lastname = getIntent().getStringExtra("lastname");
        uri = getIntent().getStringExtra("photo");
        //Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
        eName.setText(name);
        eLastname.setText(lastname);
        Picasso.with(getApplicationContext()).load(uri).into(myImageView);
        textImage = "ya";

        //si ya se encuentra registrado
        ref.orderByChild("id").equalTo(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("Aviso", "¿Entró?");
                /*user = dataSnapshot.getValue(Customer.class);
                eName.setText(user.getUsername());
                eLastname.setText(user.getUserlastname());
                ePhone.setText(String.valueOf(user.getNumberphone()));
                eBorn.setText(user.birthdate);
                eDirection.setText(user.getAddress());
                eCity.setText(user.getCity());
                Picasso.with(getApplicationContext()).load(user.getPhoto()).into(myImageView);*/
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


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSignup:
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Datos actulizados", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
