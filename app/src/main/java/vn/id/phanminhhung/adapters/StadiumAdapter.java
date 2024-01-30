package vn.id.phanminhhung.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.id.phanminhhung.R;
import vn.id.phanminhhung.interfaces.StadiumClickListener;
import vn.id.phanminhhung.models.Stadium;

public class StadiumAdapter extends RecyclerView.Adapter<StadiumAdapter.StadiumViewHolder> {

    private final Context context;
    private List<Stadium> stadiums;
    private final StadiumClickListener listener;

    public StadiumAdapter(Context context, List<Stadium> stadiums, StadiumClickListener listener) {
        this.context = context;
        this.stadiums = stadiums;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setStadiums(List<Stadium> stadiums) {
        this.stadiums = stadiums;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StadiumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stadium_item_main, parent, false);
        return new StadiumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StadiumViewHolder holder, int position) {
        Stadium stadium = stadiums.get(position);
        Glide.with(context)
                .load(stadium.getImages().get(0))
                .placeholder(R.drawable.background_placeholder)
                .into(holder.ivStadiumImage);
        holder.tvStadiumName.setText(stadium.getName());

        holder.cvItem.setOnClickListener(view -> listener.click(stadium));
    }

    @Override
    public int getItemCount() {
        return stadiums.size();
    }

    public static class StadiumViewHolder extends RecyclerView.ViewHolder {
        CardView cvItem;
        ImageView ivStadiumImage;
        TextView tvStadiumName;

        public StadiumViewHolder(@NonNull View itemView) {
            super(itemView);
            cvItem = itemView.findViewById(R.id.cv_item_main);
            ivStadiumImage = itemView.findViewById(R.id.iv_image_stadium_item_main);
            tvStadiumName = itemView.findViewById(R.id.iv_name_stadium_item_main);
        }
    }
}
