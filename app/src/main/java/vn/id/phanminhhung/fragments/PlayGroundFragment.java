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
import vn.id.phanminhhung.models.PlayGround;

public class PlayGroundFragment extends Fragment {

    private FacilityDetailActivity activity;

    public PlayGroundFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (FacilityDetailActivity) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playground, container, false);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvName = view.findViewById(R.id.tv_fr_name_play_ground);
        TextView tvSize = view.findViewById(R.id.tv_fr_size_play_ground);
        TextView tvAthleticsTrack = view.findViewById(R.id.tv_fr_athletics_track_play_ground);
        TextView tvMotorbikeRacingTrack = view.findViewById(R.id.tv_fr_motorbike_racing_track_play_ground);
        TextView tvYardSurface = view.findViewById(R.id.tv_fr_yard_surface_play_ground);

        PlayGround playGround = (PlayGround) activity.getFacility();

        tvName.setText(playGround.getName());
        tvSize.setText(String.format("Kích thước: %s", playGround.getSize()));
        tvAthleticsTrack.setText(String.format("Đường chạy điền kinh: %d", playGround.getAthleticsTrack()));
        tvMotorbikeRacingTrack.setText(String.format("Đường đua mô tô: %d", playGround.getMotorbikeRacingTrack()));
        tvYardSurface.setText(String.format("Mặt sân: %s", playGround.getYardSurface()));
    }
}