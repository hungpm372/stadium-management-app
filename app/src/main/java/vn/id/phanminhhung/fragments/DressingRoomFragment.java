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
import vn.id.phanminhhung.models.DressingRoom;

public class DressingRoomFragment extends Fragment {

    private FacilityDetailActivity activity;

    public DressingRoomFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (FacilityDetailActivity) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dressing_room, container, false);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvName = view.findViewById(R.id.tv_fr_name_dressing_room);
        TextView tvSize = view.findViewById(R.id.tv_fr_size_dressing_room);
        TextView tvPrivateArea = view.findViewById(R.id.tv_fr_private_area_dressing_room);
        TextView tvSeatingAvailable = view.findViewById(R.id.tv_fr_seating_available_dressing_room);
        TextView tvPersonalLocker = view.findViewById(R.id.tv_fr_personalLocker_dressing_room);
        TextView tvLightingSystem = view.findViewById(R.id.tv_fr_lightingSystem_dressing_room);
        TextView tvFireAlarmSystem = view.findViewById(R.id.tv_fr_fireAlarmSystem_dressing_room);
        TextView tvFireFightingEquipment = view.findViewById(R.id.tv_fr_fire_fighting_equipment_dressing_room);
        TextView tvEmergencyExit = view.findViewById(R.id.tv_fr_emergency_exit_dressing_room);

        DressingRoom dressingRoom = (DressingRoom) activity.getFacility();

        tvName.setText(dressingRoom.getName());
        tvSize.setText(String.format("Kích thước: %s", dressingRoom.getSize()));
        tvPrivateArea.setText(String.format("Khu vực riêng tư: %s", dressingRoom.getPrivateArea()));
        tvSeatingAvailable.setText(String.format("Chỗ ngồi: %s", dressingRoom.getSeatingAvailable()));
        tvPersonalLocker.setText(String.format("Tủ đồ cá nhân: %s", dressingRoom.getPersonalLocker()));
        tvLightingSystem.setText(String.format("Hệ thống chiếu sáng: %s", dressingRoom.getLightingSystem()));
        tvFireAlarmSystem.setText(String.format("Hệ thống báo cháy: %s", dressingRoom.getFireAlarmSystem()));
        tvFireFightingEquipment.setText(String.format("Thiết bị cứu hỏa: %s", dressingRoom.getFireFightingEquipment()));
        tvEmergencyExit.setText(String.format("Lối thoát khẩn cấp: %s", dressingRoom.getEmergencyExit()));
    }
}