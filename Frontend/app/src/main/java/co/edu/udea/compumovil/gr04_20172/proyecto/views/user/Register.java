package co.edu.udea.compumovil.gr04_20172.proyecto.views.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import co.edu.udea.compumovil.gr04_20172.proyecto.DTOs.Customer;
import co.edu.udea.compumovil.gr04_20172.proyecto.MainActivity;
import co.edu.udea.compumovil.gr04_20172.proyecto.R;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private String email;

    private DatabaseReference mFireBase = FirebaseDatabase.getInstance().getReference();
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private Customer user;

    static final int REQUEST_IMAGE_GET = 101;
    private Button btn_choose_image;
    private Button btnsave;
    private static String routeDowload;
    private ImageView myImageView;
    private EditText eName, eLastname, eCity;
    private String textName, textLastname, textCity;
    private String textImage = "";
    private Uri uri;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = getIntent().getStringExtra("email");
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Customer");
        myImageView = (ImageView) findViewById(R.id.img_show);
        btn_choose_image = (Button) findViewById(R.id.buttonSelectImage);
        btn_choose_image.setOnClickListener(this);
        btnsave = (Button) findViewById(R.id.buttonSignup);
        eName = (EditText) findViewById(R.id.editTextNameRegister);
        eLastname = (EditText) findViewById(R.id.editTextLastnameRegister);
        eCity = (EditText) findViewById(R.id.editTextCity);
        btnsave.setOnClickListener(this);

        downloadUserGoogle();
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
                user = dataSnapshot.getValue(Customer.class);
                eName.setText(user.getUsername());
                eLastname.setText(user.getUserlastname());
                eCity.setText(user.getCity());
                Picasso.with(getApplicationContext()).load(user.getPhoto()).into(myImageView);
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
                withGoogle();
                break;
        }
    }

    public void withGoogle() {
        textName = eName.getText().toString();
        textLastname = eLastname.getText().toString();
        textCity = eCity.getText().toString();

        if (textName.equals("") || textLastname.equals("") ||  textCity.equals("")) {
            Toast.makeText(getApplicationContext(), "Datos Incompletos", Toast.LENGTH_SHORT).show();

        } else {
            RegisterWithGoogle();
        }
    }

    public void RegisterWithGoogle() {
        String route = "users/".concat(email.concat("img.png"));
        StorageReference riversRef = mStorageRef.child(route);

        //si no modifico la foto
        if (textImage.equals("ya")) {
            routeDowload = getIntent().getStringExtra("photo");
            Customer user = new Customer(email, textName, textLastname, textCity, routeDowload);
            mFireBase.child("Customer").child(email.replace(".", ",")).setValue(user);
        } else {
            //si modifico la foto
            riversRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            routeDowload = downloadUrl.toString();

                            Customer user = new Customer(email, textName, textLastname, textCity, routeDowload);
                            mFireBase.child("Customer").child(email.replace(".", ",")).setValue(user);
                            //Toast.makeText(getApplicationContext(), "subi la imagen", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        }
        Toast.makeText(getApplicationContext(), "Datos actulizados", Toast.LENGTH_SHORT).show();
        Intent intentNavigation = new Intent(Register.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentNavigation.putExtra("email", email);
        startActivity(intentNavigation);
        finish();
    }

    public void selectPicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_GET);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            // Log.d(TAG, String.valueOf(bitmap));
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                myImageView.setImageBitmap(bitmap);
                textImage = "foto";

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
