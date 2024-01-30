package vn.id.phanminhhung.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.id.phanminhhung.R;
import vn.id.phanminhhung.activities.FacilityDetailActivity;
import vn.id.phanminhhung.models.Media;

public class MediaFragment extends Fragment {

    private FacilityDetailActivity activity;

    public MediaFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (FacilityDetailActivity) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_media, container, false);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvName = view.findViewById(R.id.tv_fr_name_media);
        TextView tvSize = view.findViewById(R.id.tv_fr_size_media);
        TextView tvSeatingCapacity = view.findViewById(R.id.tv_fr_seating_capacity_media);
        TextView tvLightingSystem = view.findViewById(R.id.tv_fr_lighting_system_media);
        TextView tvHasProjector = view.findViewById(R.id.tv_fr_has_projector_media);
        TextView tvHasSoundSystem = view.findViewById(R.id.tv_fr_has_sound_system_media);
        TextView tvHasMicrophones = view.findViewById(R.id.tv_fr_has_microphones_media);
        TextView tvHasStage = view.findViewById(R.id.tv_fr_has_stage_media);

        Media media = (Media) activity.getFacility();

        tvName.setText(media.getName());
        tvSize.setText(String.format("Kích thước: %s", media.getSize()));
        tvSeatingCapacity.setText(String.format("Sức chứa: %d", media.getSeatingCapacity()));
        tvLightingSystem.setText(String.format("Hệ thống chiếu sáng: %s", media.getLightingSystem()));
        tvHasProjector.setText(String.format("Máy chiếu: %s", media.getHasProjector()));
        tvHasSoundSystem.setText(String.format("Hệ thống âm thanh: %s", media.getHasSoundSystem()));
        tvHasMicrophones.setText(String.format("Micro: %s", media.getHasMicrophones()));
        tvHasStage.setText(String.format("Sân khấu: %s", media.getHasStage()));
    }
}