package co.edu.udea.compumovil.gr04_20172.proyecto.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import co.edu.udea.compumovil.gr04_20172.proyecto.DTOs.Customer;
import co.edu.udea.compumovil.gr04_20172.proyecto.R;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.user.Register;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Button btnUpdate;
    private String email, name;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private TextView nameProfile=null, lastnameProfile=null, emailProfile=null, cityProfile=null;
    private ImageView photo;
    Customer user;

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Customer");
        email = getActivity().getIntent().getStringExtra("email");
        name = getActivity().getIntent().getStringExtra("name");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        nameProfile = v.findViewById(R.id.textViewNameProfile);
        lastnameProfile = v.findViewById(R.id.textViewLastnameProfile);
        emailProfile = v.findViewById(R.id.textViewEmailProfile);
        cityProfile = v.findViewById(R.id.textViewCityProfile);
        photo = v.findViewById(R.id.imageViewProfile);
        btnUpdate = v.findViewById(R.id.updateProfile);
        btnUpdate.setOnClickListener(this);
        ref.orderByChild("id").equalTo(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("Aviso","¿Entró?");
                user = dataSnapshot.getValue(Customer.class);
                nameProfile.setText(user.getUsername());
                lastnameProfile.setText(user.getUserlastname());
                emailProfile.setText(user.getId());
                cityProfile.setText(user.getCity());
                Picasso.with(getContext()).load(user.getPhoto()).into(photo);
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
        return v;
    }


    @Override
    public void onClick(View view) {
        String lastname, uri;
        name = getActivity().getIntent().getStringExtra("name");
        lastname = getActivity().getIntent().getStringExtra("lastname");
        uri = getActivity().getIntent().getStringExtra("photo");
        Intent intentToEdit = new Intent(getActivity(), Register.class);
        intentToEdit.putExtra("email", email);
        intentToEdit.putExtra("name", name);
        intentToEdit.putExtra("lastname", lastname);
        intentToEdit.putExtra("photo", uri);
        getActivity().startActivity(intentToEdit);

    }
}
