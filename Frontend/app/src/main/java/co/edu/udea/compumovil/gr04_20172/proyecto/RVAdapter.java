package co.edu.udea.compumovil.gr04_20172.proyecto;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.LocalViewHolder>{

    List<Local> locals;

    public RVAdapter(List<Local> locals){
        this.locals = locals;
    }

    @Override
    public LocalViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_local_food, viewGroup, false);
        LocalViewHolder pvh = new LocalViewHolder(v);
        return pvh;

    }

    @Override
    public void onBindViewHolder(LocalViewHolder holder, int position) {
        holder.photo.setImageResource(R.drawable.apa1);
        holder.name.setText(locals.get(position).getName());
        holder.phone.setText(locals.get(position).getPhone());
        holder.direction.setText(locals.get(position).getDirection());
    }

    @Override
    public int getItemCount() {
        return locals.size();
    }

    public static class LocalViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        TextView direction;
        ImageView photo;
        TextView phone;

        LocalViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            name = (TextView)itemView.findViewById(R.id.local_name);
            direction = (TextView)itemView.findViewById(R.id.local_direction);
            photo = (ImageView)itemView.findViewById(R.id.local_photo);
            phone = (TextView)itemView.findViewById(R.id.local_phone);
        }
    }

}