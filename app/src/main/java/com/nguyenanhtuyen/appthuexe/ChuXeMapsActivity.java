package com.nguyenanhtuyen.appthuexe;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nguyenanhtuyen.appthuexe.databinding.ActivityChuXeMapsBinding;
import com.nguyenanhtuyen.model.User;

public class ChuXeMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityChuXeMapsBinding binding;
    TextView txtTenHienThiChuXe;
    ImageView ic_KhamPhaChuXe, ic_ThongBaoChuXe, ic_ChuyenChuXe, ic_HoTroChuXe, ic_CaNhanChuXe;

    GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(@NonNull Location location) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChuXeMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_chuxe);
        mapFragment.getMapAsync(this);
        addControls();
        addEvents();
    }

    private void addEvents() {
        ic_CaNhanChuXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Intent intent2 = new Intent(ChuXeMapsActivity.this, ProfileLoggedActivity.class);
                if (intent != null) {
                    User u = (User) intent.getSerializableExtra("user_login");
                    if (u != null) {
                        intent2.putExtra("user_profile", u);
                    }
                }
                startActivity(intent2);
            }
        });
    }

    private void addControls() {
        txtTenHienThiChuXe = findViewById(R.id.txtTenHienThi_ChuXe);
        ic_KhamPhaChuXe = findViewById(R.id.ic_khampha_chuxe);
        ic_ThongBaoChuXe = findViewById(R.id.ic_thongbao_chuxe);
        ic_ChuyenChuXe = findViewById(R.id.ic_chuyen_chuxe);
        ic_HoTroChuXe = findViewById(R.id.ic_hotro_chuxe);
        ic_CaNhanChuXe = findViewById(R.id.ic_canhan_chuxe);
        Intent intent = getIntent();
        if (intent != null) {
            User u = (User) intent.getSerializableExtra("user_login");
            if (u != null) {
                txtTenHienThiChuXe.setText("Xin chào " + u.getTenHienThi() + "!");
                Intent intent1 = new Intent(ChuXeMapsActivity.this, DatXeActivity.class);
                intent1.putExtra("user_datxe",u);
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);

        // Add a marker in Sydney and move the camera
        //LatLng myLocation = new LatLng(10.852321, 106.784672);
        //Marker marker = mMap.addMarker(new MarkerOptions().position(myLocation).title("Vị trí của bạn"));
        //marker.showInfoWindow();
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,15));
    }
}