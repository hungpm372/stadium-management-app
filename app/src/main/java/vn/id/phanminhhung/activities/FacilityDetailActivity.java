package vn.id.phanminhhung.activities;

import static vn.id.phanminhhung.activities.StadiumDetailActivity.DRESSING_ROOM_TYPE;
import static vn.id.phanminhhung.activities.StadiumDetailActivity.HEALTH_STATION_TYPE;
import static vn.id.phanminhhung.activities.StadiumDetailActivity.PLAYGROUND_TYPE;
import static vn.id.phanminhhung.activities.StadiumDetailActivity.STAND_TYPE;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import vn.id.phanminhhung.R;
import vn.id.phanminhhung.adapters.FacilityDetailAdapter;
import vn.id.phanminhhung.models.DressingRoom;
import vn.id.phanminhhung.models.HealthStation;
import vn.id.phanminhhung.models.Media;
import vn.id.phanminhhung.models.PlayGround;
import vn.id.phanminhhung.models.Stand;

public class FacilityDetailActivity extends AppCompatActivity {

    private ViewPager2 vpFacilityDetail;
    private TabLayout tlFacilityDetail;
    private Serializable facility;
    private List<String> images;

    private int type;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_detail);

        initViews();

        Bundle bundle = getIntent().getExtras();
        facility = bundle.getSerializable("facility");
        type = bundle.getInt("type");
        id = bundle.getInt("stadiumId");

        String title;
        if (type == PLAYGROUND_TYPE) {
            title = ((PlayGround) facility).getName();
            images = ((PlayGround) facility).getImages();
        } else if (type == STAND_TYPE) {
            title = ((Stand) facility).getName();
            images = ((Stand) facility).getImages();
        } else if (type == DRESSING_ROOM_TYPE) {
            title = ((DressingRoom) facility).getName();
            images = ((DressingRoom) facility).getImages();
        } else if (type == HEALTH_STATION_TYPE) {
            title = ((HealthStation) facility).getName();
            images = ((HealthStation) facility).getImages();
        } else {
            title = ((Media) facility).getName();
            images = ((Media) facility).getImages();
        }

        FacilityDetailAdapter adapter = new FacilityDetailAdapter(getSupportFragmentManager(), getLifecycle(), this);
        vpFacilityDetail.setAdapter(adapter);
        vpFacilityDetail.setOffscreenPageLimit(2);

        new TabLayoutMediator(tlFacilityDetail, vpFacilityDetail, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Hình ảnh");
                    break;
                case 1:
                    tab.setText("Chi tiết");
                    break;
            }
        }).attach();

        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return true;
    }

    private void initViews() {
        vpFacilityDetail = findViewById(R.id.vp_facility_detail);
        tlFacilityDetail = findViewById(R.id.tl_facility_detail);
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Serializable getFacility() {
        return facility;
    }

    public int getType() {
        return type;
    }

    public int getStadiumId() {
        return id;
    }
}