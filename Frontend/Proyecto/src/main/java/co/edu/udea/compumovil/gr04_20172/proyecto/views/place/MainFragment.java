package co.edu.udea.compumovil.gr04_20172.proyecto.views.place;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr04_20172.proyecto.DTOs.Food;
import co.edu.udea.compumovil.gr04_20172.proyecto.DTOs.Place;
import co.edu.udea.compumovil.gr04_20172.proyecto.R;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.user.Login;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener {

    private FirebaseDatabase database;
    private DatabaseReference refPlace, refFood;
    private Place place;
    private Food food;
    private FirebaseUser user;

    private RecyclerView rv;
    private RecyclerView.LayoutManager llm;
    private RVAdapter adapter;
    private FloatingActionButton fab;
    ArrayList<Place> places;
    private String email, name, lastname, uri;
    private OnFragmentButtonListener mListener;
    private SearchView searchView;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        refPlace = database.getReference("Place");
        refFood = database.getReference("Food");
        user = FirebaseAuth.getInstance().getCurrentUser();
        name = getActivity().getIntent().getStringExtra("name");
        lastname = getActivity().getIntent().getStringExtra("lastname");
        uri = getActivity().getIntent().getStringExtra("photo");

        if (user != null) {
            email = user.getEmail();
        } else {
            getActivity().finish();
            Intent toLogin = new Intent(getContext(), Login.class);
            startActivity(toLogin);
        }

        places = new ArrayList<>();
        places.clear();

        refPlace.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                place = dataSnapshot.getValue(Place.class);
                //Toast.makeText(getActivity(), apartment.getUbication(), Toast.LENGTH_SHORT).show();

                places.add(place);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        Snackbar.make(v, "Cargando restaurantes", Snackbar.LENGTH_LONG).show();
        searchView = (SearchView) v.findViewById(R.id.search_view);
        searchView.setQueryHint("Buscar por comida...");
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Agregar apartamento", Snackbar.LENGTH_LONG).show();
                Intent intentToAdd = new Intent(getActivity(), Add_Place.class);
                intentToAdd.putExtra("email", email);
                intentToAdd.putExtra("name", name);
                intentToAdd.putExtra("lastname", lastname);
                intentToAdd.putExtra("photo", uri);
                getActivity().startActivity(intentToAdd);
            }
        });

        rv = (RecyclerView) v.findViewById(R.id.rv);
        paint();

        searchView.setOnQueryTextListener(this);
        return v;
    }

    private void paint() {
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        adapter = new RVAdapter(places, email);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = adapter.getItem(rv.getChildAdapterPosition(view)).getDirection();
                //Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
                if (mListener != null) {
                    mListener.onFragmentClickButton(id);
                }
            }
        });

        rv.setAdapter(adapter);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentButtonListener) {
            mListener = (OnFragmentButtonListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentButtonListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
        refFood.orderByChild("name").equalTo(query).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                food = dataSnapshot.getValue(Food.class);
                //Toast.makeText(getActivity(), food.getPlace(), Toast.LENGTH_SHORT).show();

                places.clear();
                refPlace.orderByChild("direction").equalTo(food.getPlace()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        place = dataSnapshot.getValue(Place.class);
                        //Toast.makeText(getActivity(), apartment.getUbication(), Toast.LENGTH_SHORT).show();

                        places.add(place);
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
                paint();

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
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        //Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
        refFood.orderByChild("name").equalTo(s).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                food = dataSnapshot.getValue(Food.class);
                //Toast.makeText(getActivity(), food.getPlace(), Toast.LENGTH_SHORT).show();

                places.clear();
                refPlace.orderByChild("direction").equalTo(food.getPlace()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        place = dataSnapshot.getValue(Place.class);
                        //Toast.makeText(getActivity(), apartment.getUbication(), Toast.LENGTH_SHORT).show();

                        places.add(place);
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
                paint();

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
        return false;

    }

    public interface OnFragmentButtonListener {
        void onFragmentClickButton(String id);
    }

}