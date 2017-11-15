package co.edu.udea.compumovil.gr04_20172.proyecto.views.place;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import co.edu.udea.compumovil.gr04_20172.proyecto.DTOs.Place;
import co.edu.udea.compumovil.gr04_20172.proyecto.MainActivity;
import co.edu.udea.compumovil.gr04_20172.proyecto.R;

public class Add_Place extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mFireBase = FirebaseDatabase.getInstance().getReference();
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private Uri uri;

    private ImageView imageView;
    private EditText eName, ePhone, eDescription , eType, eDirection;
    private String textName, textPhone, textDescription , textType, textDirection, nombre;
    private String email, name, lastname, uri1;
    private Button btnAdd;
    private Bitmap bitmap;
    private static final int REQUEST_IMAGE_GET = 101;
    private static String routeDowload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        lastname = getIntent().getStringExtra("lastname");
        uri1 = getIntent().getStringExtra("photo");

        setContentView(R.layout.activity_add__place);
        imageView = (ImageView) findViewById(R.id.imageAdd);
        eName = (EditText) findViewById(R.id.editTextNameAdd);
        ePhone = (EditText) findViewById(R.id.editTextPhoneAdd);
        eDescription = (EditText) findViewById(R.id.editTextDescriptionAdd);
        eType = (EditText) findViewById(R.id.editTextTypeAdd);
        eDirection = (EditText) findViewById(R.id.editTextDirectionAdd);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        imageView.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageAdd:
                selectPicture();
                break;
            case R.id.buttonAdd:
                addPlace();
                break;
        }



    }

    public void addPlace() {
        textName = eName.getText().toString();
        textPhone = ePhone.getText().toString();
        textDescription = eDescription.getText().toString();
        textType = eType.getText().toString();
        textDirection = eDirection.getText().toString();

        if (bitmap==null || textName.equals("") || textPhone.equals("") || textDescription.equals("") || textType.equals("") || textDirection.equals("")) {
            Toast.makeText(getApplicationContext(), "Datos Incompletos", Toast.LENGTH_SHORT).show();
        }else{
            nombre = textDirection; //Nommbre de la imagen
            nombre = nombre.replace(" ", "");
            nombre = nombre.replace("#", "");
            nombre = nombre.replace("-", "");

            //final ProgressDialog progressDialog = new ProgressDialog(g);
            //progressDialog.setTitle("Agregando Apartamento");
            //progressDialog.show();

            String route = "Places/".concat(nombre.concat("img.png"));
            StorageReference riversRef = mStorageRef.child(route);
            riversRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Get a URL to the uploaded content
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            routeDowload = downloadUrl.toString();

                            Place place = new Place(textName, routeDowload, textPhone, textDescription, textType, textDirection, "nofavorite");

                            mFireBase.child("Place").child(nombre).setValue(place);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //progressDialog.dismiss();
                            //Toast.makeText(getApplicationContext(), "AQUI: "+exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                            /*.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                         //displaying the upload progress
                                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                        progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                                        }
                            });*/




            Toast.makeText(getApplicationContext(), "Sitio agregado", Toast.LENGTH_SHORT).show();
            Intent intentNavigation = new Intent(Add_Place.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentNavigation.putExtra("email",email);
            intentNavigation.putExtra("name", name);
            intentNavigation.putExtra("lastname", lastname);
            intentNavigation.putExtra("photo", uri1);
            //Toast.makeText(getApplicationContext(),"cogi el correo "+ email, Toast.LENGTH_SHORT).show();
            startActivity(intentNavigation);
            finish();
        }
    }

    public void selectPicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_GET);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            // Log.d(TAG, String.valueOf(bitmap));
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
