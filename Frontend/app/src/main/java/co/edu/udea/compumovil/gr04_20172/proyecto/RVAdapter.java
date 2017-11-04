package co.edu.udea.compumovil.gr04_20172.proyecto;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr04_20172.proyecto.DTOs.Place;
import co.edu.udea.compumovil.gr04_20172.proyecto.views.place.MainFragment;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.LocalViewHolder> implements  View.OnClickListener{

    private ArrayList<Place> locals;
    private View.OnClickListener listener;

    public RVAdapter(ArrayList<Place> locals){
        this.locals = locals;
    }

    @Override
    public LocalViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_place, viewGroup, false);
        v.setOnClickListener(this);
        LocalViewHolder pvh = new LocalViewHolder(v, viewGroup.getContext());
        return pvh;

    }

    @Override
    public void onBindViewHolder(LocalViewHolder holder, int position) {
        Picasso.with(holder.contexto).load(locals.get(position).getPhoto()).into(holder.photo);
        holder.name.setText(locals.get(position).getName());
        holder.phone.setText(locals.get(position).getPhone());
        holder.direction.setText(locals.get(position).getDirection());
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
        if (listener != null){
            listener.onClick(view);
        }

    }

    public Place getItem(int position)
    {
        return locals.get(position);

    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public static class LocalViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView name;
        private TextView direction;
        private ImageView photo;
        private TextView phone;
        private Context contexto;

        LocalViewHolder(View itemView, Context context) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            name = (TextView)itemView.findViewById(R.id.local_name);
            direction = (TextView)itemView.findViewById(R.id.local_direction);
            photo = (ImageView)itemView.findViewById(R.id.local_photo);
            phone = (TextView)itemView.findViewById(R.id.local_phone);
            this.contexto = context;
        }
    }

}