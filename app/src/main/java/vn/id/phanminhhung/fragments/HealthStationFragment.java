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
import vn.id.phanminhhung.models.HealthStation;

public class HealthStationFragment extends Fragment {

    private FacilityDetailActivity activity;

    public HealthStationFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (FacilityDetailActivity) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_health_station, container, false);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvName = view.findViewById(R.id.tv_fr_name_health_station);
        TextView tvSize = view.findViewById(R.id.tv_fr_size_health_station);
        TextView tvCapacity = view.findViewById(R.id.tv_fr_capacity_health_station);
        TextView tvNumberOfBeds = view.findViewById(R.id.tv_fr_number_of_beds_health_station);
        TextView tvPrivateArea = view.findViewById(R.id.tv_fr_private_area_health_station);
        TextView tvSeatingAvailable = view.findViewById(R.id.tv_fr_seating_available_health_station);
        TextView tvMedicalEquipment = view.findViewById(R.id.tv_fr_medical_equipment_health_station);
        TextView tvEmergencyExit = view.findViewById(R.id.tv_fr_emergency_exit_health_station);
        TextView tvVentilationSystem = view.findViewById(R.id.tv_fr_ventilation_system_health_station);
        TextView tvSterilizationEquipment = view.findViewById(R.id.tv_fr_sterilization_equipment_health_station);
        TextView tvEmergencyCallButton = view.findViewById(R.id.tv_fr_emergency_callButton_health_station);
        TextView tvStorageCabinets = view.findViewById(R.id.tv_fr_storage_cabinets_health_station);

        HealthStation healthStation = (HealthStation) activity.getFacility();

        tvName.setText(healthStation.getName());
        tvSize.setText(String.format("Kích thước: %s", healthStation.getSize()));
        tvCapacity.setText(String.format("Sức chứa: %d", healthStation.getCapacity()));
        tvNumberOfBeds.setText(String.format("Số giường: %d", healthStation.getNumberOfBeds()));
        tvPrivateArea.setText(String.format("Khu vực riêng tư: %s", healthStation.getPrivateArea()));
        tvSeatingAvailable.setText(String.format("Số chỗ ngồi có sẵn: %s", healthStation.getSeatingAvailable()));
        tvMedicalEquipment.setText(String.format("Trang thiết bị y tế: %s", healthStation.getMedicalEquipment()));
        tvEmergencyExit.setText(String.format("Lối thoát hiểm: %s", healthStation.getEmergencyExit()));
        tvVentilationSystem.setText(String.format("Hệ thống thông gió: %s", healthStation.getVentilationSystem()));
        tvSterilizationEquipment.setText(String.format("Trang thiết bị tiệt trùng: %s", healthStation.getSterilizationEquipment()));
        tvEmergencyCallButton.setText(String.format("Nút gọi cấp cứu: %s", healthStation.getEmergencyCallButton()));
        tvStorageCabinets.setText(String.format("Tủ lưu trữ: %s", healthStation.getStorageCabinets()));
    }
}