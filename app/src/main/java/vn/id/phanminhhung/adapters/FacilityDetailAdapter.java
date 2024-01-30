package vn.id.phanminhhung.adapters;

import static vn.id.phanminhhung.activities.StadiumDetailActivity.DRESSING_ROOM_TYPE;
import static vn.id.phanminhhung.activities.StadiumDetailActivity.HEALTH_STATION_TYPE;
import static vn.id.phanminhhung.activities.StadiumDetailActivity.PLAYGROUND_TYPE;
import static vn.id.phanminhhung.activities.StadiumDetailActivity.STAND_TYPE;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.id.phanminhhung.activities.FacilityDetailActivity;
import vn.id.phanminhhung.fragments.DressingRoomFragment;
import vn.id.phanminhhung.fragments.HealthStationFragment;
import vn.id.phanminhhung.fragments.ImageFragment;
import vn.id.phanminhhung.fragments.MediaFragment;
import vn.id.phanminhhung.fragments.PlayGroundFragment;
import vn.id.phanminhhung.fragments.StandFragment;

public class FacilityDetailAdapter extends FragmentStateAdapter {

    private final FacilityDetailActivity activity;

    public FacilityDetailAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, FacilityDetailActivity activity) {
        super(fragmentManager, lifecycle);
        this.activity = activity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ImageFragment();
            case 1:
                int type = activity.getType();
                if (type == PLAYGROUND_TYPE)
                    return new PlayGroundFragment();
                else if (type == STAND_TYPE)
                    return new StandFragment();
                else if (type == DRESSING_ROOM_TYPE)
                    return new DressingRoomFragment();
                else if (type == HEALTH_STATION_TYPE)
                    return new HealthStationFragment();
            default:
                return new MediaFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
