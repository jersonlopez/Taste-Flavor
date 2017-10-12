package co.edu.udea.compumovil.gr04_20172.proyecto;


import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Favorite_Fragment extends Fragment {
    RecyclerView rv;
    RecyclerView.LayoutManager llm;

    public Favorite_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);

        ArrayList<Local> LocalFoods;
        LocalFoods = new ArrayList<>();
        rv = (RecyclerView) v.findViewById(R.id.rv);
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        LocalFoods.add(new Local("Bunger King", "310 427 13 94", "barrio antioquia",12,1));
        LocalFoods.add(new Local("Mc Donals", "320 633 97 38", "manrique",12,2));
        LocalFoods.add(new Local("Subway", "321 857 35 24", "san javier",12,3));
        //LocalFoods.add(new Local("KFC", "311 538 24 05",));
        //LocalFoods.add(new Person("Mac Donalds", "312 649 35 16", R.drawable.mc));

        //Local_Foods.add(new LocalFood("Bunger King", "310 427 13 94", "Carrera 25 a # 65 E 47", R.drawable.burgerking));
        //Local_Foods.add(new LocalFood("KFC", "311 538 24 05", "Carrera 65 d # 40 D 76", R.drawable.kfc));
        //Local_Foods.add(new LocalFood("Mac Donalds", "312 649 35 16", "Carrera 75 b # 34 A 54", R.drawable.mc));
        //Local_Foods.add(new LocalFood("Subway", "312 649 35 16", "Carrera 75 b # 34 A 54", R.drawable.subway));


        RVAdapter adapter = new RVAdapter(LocalFoods);
        rv.setAdapter(adapter);
        return v;
    }
}
