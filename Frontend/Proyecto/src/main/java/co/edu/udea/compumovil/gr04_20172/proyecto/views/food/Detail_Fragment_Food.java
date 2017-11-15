package co.edu.udea.compumovil.gr04_20172.proyecto.views.food;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import co.edu.udea.compumovil.gr04_20172.proyecto.DTOs.Customer;
import co.edu.udea.compumovil.gr04_20172.proyecto.DTOs.Food;
import co.edu.udea.compumovil.gr04_20172.proyecto.DTOs.Place;
import co.edu.udea.compumovil.gr04_20172.proyecto.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Detail_Fragment_Food extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    Food value;


    private CollapsingToolbarLayout toolbar;
    private String id;
    private TextView name, description, price;
    private ImageView image;


    public Detail_Fragment_Food() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        //Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Food");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail__food, container, false);
        image = v.findViewById(R.id.foodImage);
        name = v.findViewById(R.id.name_food_detail);
        description = v.findViewById(R.id.description_food_detail);
        price = v.findViewById(R.id.price_food_detail);
        ref.orderByChild("description").equalTo(id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("Aviso","¿Entró?");
                value = dataSnapshot.getValue(Food.class);
                name.setText(value.getName());
                description.setText(value.getDescription());
                price.setText(value.getPrice());
                Picasso.with(getContext()).load(value.getPhoto()).into(image);
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

}
