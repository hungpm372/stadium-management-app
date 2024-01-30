package vn.id.phanminhhung.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.id.phanminhhung.R;

public class StadiumImageAdapter extends RecyclerView.Adapter<StadiumImageAdapter.StadiumImageViewHolder> {

    private Context context;
    private List<String> images;

    public StadiumImageAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public StadiumImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stadium_image_item_detail, parent, false);
        return new StadiumImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StadiumImageViewHolder holder, int position) {
        Glide.with(context)
                .load(images.get(position))
                .placeholder(R.drawable.background_placeholder)
                .into(holder.ivStadiumImage);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class StadiumImageViewHolder extends RecyclerView.ViewHolder {

        ImageView ivStadiumImage;

        public StadiumImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStadiumImage = itemView.findViewById(R.id.iv_image_stadium_item_detail);
        }
    }
}
