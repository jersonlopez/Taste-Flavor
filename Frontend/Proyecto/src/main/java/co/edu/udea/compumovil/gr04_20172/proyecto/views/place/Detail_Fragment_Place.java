package co.edu.udea.compumovil.gr04_20172.proyecto.views.place;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr04_20172.proyecto.DTOs.Food;
import co.edu.udea.compumovil.gr04_20172.proyecto.DTOs.Place;
import co.edu.udea.compumovil.gr04_20172.proyecto.R;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.food.Adapter;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.food.Detail_Fragment_Food;

/**
 * A simple {@link Fragment} subclass.
 */
public class Detail_Fragment_Place extends Fragment implements  View.OnClickListener{

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference refPlace, refFood;
    Place value;
    Food food;
    private RecyclerView rv;
    private RecyclerView.LayoutManager llm;
    private Adapter adapter;
    ArrayList<Food> foods;


    private CollapsingToolbarLayout toolbar;
    private String id;
    private ImageView image;
    private Button map;


    public Detail_Fragment_Place() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        firebaseDatabase = FirebaseDatabase.getInstance();
        refPlace = firebaseDatabase.getReference("Place");
        refFood = firebaseDatabase.getReference("Food");
        foods = new ArrayList<>();
        refFood.orderByChild("place").equalTo(id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                food = dataSnapshot.getValue(Food.class);

                foods.add(food);
                adapter.notifyDataSetChanged();

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
        //Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_detail__place, container, false);
        toolbar = (CollapsingToolbarLayout)v.findViewById(R.id.collapsing_toolbar);
        image=v.findViewById(R.id.placeImage);
        map = v.findViewById(R.id.map_button);
        map.setOnClickListener(this);

        refPlace.orderByChild("direction").equalTo(id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("Aviso","¿Entró?");
                value = dataSnapshot.getValue(Place.class);
                toolbar.setTitle(value.getName());
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
        rv = (RecyclerView) v.findViewById(R.id.rvFood);
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        adapter = new Adapter(foods);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = adapter.getItem(rv.getChildAdapterPosition(view)).getDescription();
                //Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
                Fragment fragment;
                fragment=new Detail_Fragment_Food();
                Bundle bundle=new Bundle();
                bundle.putString("id",id);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentNavigation,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        rv.setAdapter(adapter);
        setHasOptionsMenu(true);
        return v;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.map_button:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:6.266953,-75.569111?z=30"));
                if (intent!=null)
                {
                    startActivity(intent);
                }
                break;
        }
    }

}