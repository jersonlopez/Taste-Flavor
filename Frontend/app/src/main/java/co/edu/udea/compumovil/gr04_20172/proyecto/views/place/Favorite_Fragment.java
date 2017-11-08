package co.edu.udea.compumovil.gr04_20172.proyecto.views.place;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr04_20172.proyecto.DTOs.Place;
import co.edu.udea.compumovil.gr04_20172.proyecto.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Favorite_Fragment extends Fragment {
    RecyclerView rv;
    RecyclerView.LayoutManager llm;
    ArrayList<Place> LocalFoods;

    public Favorite_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);


        LocalFoods = new ArrayList<>();
        rv = (RecyclerView) v.findViewById(R.id.rv);
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        LocalFoods.add(new Place("Burger King", "photo", "3104271394", "comidas rapidas", "las mejores hamburguesas", "barrio antioquia" ));
        LocalFoods.add(new Place("Mc Donals", "photo", "320 633 97 38", "postres", "los mas ricos postres", "manrique"));
        LocalFoods.add(new Place("Subway", "photo", "321 857 35 24", "sanduches", "los mejores sanduches", "san javier"));


        RVAdapter adapter = new RVAdapter(LocalFoods);
        rv.setAdapter(adapter);
        return v;
    }
}
