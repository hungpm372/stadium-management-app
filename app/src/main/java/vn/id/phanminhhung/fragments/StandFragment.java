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
import vn.id.phanminhhung.models.Stand;

public class StandFragment extends Fragment {
    private FacilityDetailActivity activity;

    public StandFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (FacilityDetailActivity) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stand, container, false);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvName = view.findViewById(R.id.tv_fr_name_stand);
        TextView tvStandA = view.findViewById(R.id.tv_fr_a_stand);
        TextView tvStandB = view.findViewById(R.id.tv_fr_b_stand);
        TextView tvStandC = view.findViewById(R.id.tv_fr_c_stand);
        TextView tvStandD = view.findViewById(R.id.tv_fr_d_stand);
        TextView tvBrokenChair = view.findViewById(R.id.tv_fr_broken_chair_stand);

        Stand stand = (Stand) activity.getFacility();

        tvName.setText(stand.getName());
        tvStandA.setText(String.format("Khán đài A: %s", stand.getStandA()));
        tvStandB.setText(String.format("Khán đài B: %s", stand.getStandB()));
        tvStandC.setText(String.format("Khán đài C: %s", stand.getStandC()));
        tvStandD.setText(String.format("Khán đài D: %s", stand.getStandD()));
        tvBrokenChair.setText(String.format("Ghế ngồi hư hỏng: %d", stand.getBrokenChair()));
    }
}