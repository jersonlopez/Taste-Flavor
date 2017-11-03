package co.edu.udea.compumovil.gr04_20172.proyecto.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import co.edu.udea.compumovil.gr04_20172.proyecto.R;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Button btnUpdate;
    private String email, name;

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        btnUpdate = v.findViewById(R.id.updateProfile);
        btnUpdate.setOnClickListener(this);
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
