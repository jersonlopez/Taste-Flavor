package co.edu.udea.compumovil.gr04_20172.proyecto.views.food;

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

import co.edu.udea.compumovil.gr04_20172.proyecto.DTOs.Food;
import co.edu.udea.compumovil.gr04_20172.proyecto.DTOs.Place;
import co.edu.udea.compumovil.gr04_20172.proyecto.R;
import co.edu.udea.compumovil.gr04_20172.proyecto.RVAdapter;

/**
 * Created by jersonlopez on 5/11/17.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.FoodViewHolder> implements  View.OnClickListener {

    private ArrayList<Food> foods;
    private View.OnClickListener listener;

    public Adapter(ArrayList<Food> foods) {
        this.foods = foods;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_food, parent, false);
        v.setOnClickListener(this);
        Adapter.FoodViewHolder pvh = new Adapter.FoodViewHolder(v, parent.getContext());
        return pvh;
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        Picasso.with(holder.contexto).load(foods.get(position).getPhoto()).into(holder.photo);
        holder.name.setText(foods.get(position).getName());
        holder.price.setText(foods.get(position).getPrice());
        holder.description.setText(foods.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return foods.size();
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

    public Food getItem(int position)
    {
        return foods.get(position);

    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView name;
        private TextView description;
        private ImageView photo;
        private TextView price;
        private Context contexto;

        FoodViewHolder(View itemView, Context context) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            name = (TextView)itemView.findViewById(R.id.food_name);
            description = (TextView)itemView.findViewById(R.id.food_description);
            photo = (ImageView)itemView.findViewById(R.id.food_photo);
            price = (TextView)itemView.findViewById(R.id.food_price);
            this.contexto = context;
        }
    }
}
