package vn.id.phanminhhung.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.id.phanminhhung.R;

public class FacilityImageAdapter extends RecyclerView.Adapter<FacilityImageAdapter.FacilityImageViewHolder> {

    private final Context context;
    private List<String> images;

    public FacilityImageAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<String> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FacilityImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facility_image_item, parent, false);
        return new FacilityImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacilityImageViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //int i = position;
        Glide.with(context).load(images.get(position)).placeholder(R.drawable.background_placeholder).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class FacilityImageViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;

        public FacilityImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image_facility_item_detail);
        }
    }

}
