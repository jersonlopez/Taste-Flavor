package co.edu.udea.compumovil.gr04_20172.proyecto.views.place;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.edu.udea.compumovil.gr04_20172.proyecto.DTOs.Place;
import co.edu.udea.compumovil.gr04_20172.proyecto.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Detail_Fragment_Place extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    Place value;


    private CollapsingToolbarLayout toolbar;
    private String id;
    private TextView type, room, cost,area,ubication,description;
    private ImageView image;
    private Button map;


    public Detail_Fragment_Place() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail__place, container, false);
    }

}
