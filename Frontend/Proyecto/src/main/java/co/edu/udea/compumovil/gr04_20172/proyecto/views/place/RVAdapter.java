package co.edu.udea.compumovil.gr04_20172.proyecto.views.place;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr04_20172.proyecto.DTOs.Place;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.place.MainFragment;
import co.edu.udea.compumovil.gr04_20172.proyecto.R;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.LocalViewHolder> implements View.OnClickListener {

    private ArrayList<Place> locals;
    private View.OnClickListener listener;
    private DatabaseReference mFireBase = FirebaseDatabase.getInstance().getReference();
    private String email;

    public RVAdapter(ArrayList<Place> locals, String email) {
        this.locals = locals;
        this.email = email;
    }


    @Override
    public LocalViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_place, viewGroup, false);
        v.setOnClickListener(this);
        LocalViewHolder pvh = new LocalViewHolder(v, viewGroup.getContext());
        return pvh;

    }

    @Override
    public void onBindViewHolder(final LocalViewHolder holder, final int position) {
        paint(holder, position);
        Picasso.with(holder.contexto).load(locals.get(position).getPhoto()).into(holder.photo);
        holder.name.setText(locals.get(position).getName());
        holder.phone.setText(locals.get(position).getPhone());
        holder.direction.setText(locals.get(position).getDirection());

        if (locals.get(position).getState().equals("nofavorite")) {
            holder.favorite.setImageResource(R.mipmap.ic_launcher);
        } else {
            holder.favorite.setImageResource(R.mipmap.ic_launcher_2);
        }

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName, routeDowload, textPhone, textDescription, textType, textDirection, state, nombre;
                textName = locals.get(position).getName();
                routeDowload = locals.get(position).getPhoto();
                textPhone = locals.get(position).getPhone();
                textDescription = locals.get(position).getDescription();
                textType = locals.get(position).getType();
                textDirection = locals.get(position).getDirection();
                state = locals.get(position).getState();

                nombre = textDirection; //Nommbre de la imagen
                nombre = nombre.replace(" ", "");
                nombre = nombre.replace("#", "");
                nombre = nombre.replace("-", "");

                Log.d("Hola", textName);

                if (state.equals("nofavorite")) {
                    holder.favorite.setImageResource(R.mipmap.ic_launcher_2);
                    Place place = new Place(textName, routeDowload, textPhone, textDescription, textType, textDirection, "favorite");
                    mFireBase.child("Place").child(nombre).child("state").setValue("favorite");
                    mFireBase.child("Favorite").child(email.replace(".",",")).child(nombre).setValue(place);
                    Toast.makeText(holder.contexto, "Agregado a favoritos", Toast.LENGTH_SHORT).show();
                } else {
                    holder.favorite.setImageResource(R.mipmap.ic_launcher);
                    mFireBase.child("Favorite").child(email.replace(".",",")).child(nombre).removeValue();
                    mFireBase.child("Place").child(nombre).child("state").setValue("nofavorite");
                    Toast.makeText(holder.contexto, "Eliminado de favoritos", Toast.LENGTH_SHORT).show();
                }
                //holder.itemView.getDisplay();
                //holder.favorite.clearFocus();
            }
        });
    }

    private void paint(LocalViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return locals.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }

    }

    public Place getItem(int position) {
        return locals.get(position);

    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public static class LocalViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView name;
        private TextView direction;
        private ImageView photo, favorite;
        private TextView phone;
        private Context contexto;

        LocalViewHolder(View itemView, final Context context) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.local_name);
            direction = (TextView) itemView.findViewById(R.id.local_direction);
            photo = (ImageView) itemView.findViewById(R.id.local_photo);
            favorite = (ImageView) itemView.findViewById(R.id.favorite);
            phone = (TextView) itemView.findViewById(R.id.local_phone);
            this.contexto = context;
        }
    }

}