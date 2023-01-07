package com.nguyenanhtuyen.appthuexe;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nguyenanhtuyen.adapter.MyInforAdapter;
import com.nguyenanhtuyen.appthuexe.databinding.ActivityXeTuLaiMapsBinding;
import com.nguyenanhtuyen.model.XeTuLai;


public class XeTuLaiMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityXeTuLaiMapsBinding binding;
    LatLng xeLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityXeTuLaiMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent = getIntent();
        if(intent!=null){
            XeTuLai xeTuLai = (XeTuLai) intent.getSerializableExtra("xetulaimaps");
            if(xeTuLai!=null){
                xeLocation = new LatLng(xeTuLai.getKinhDo(), xeTuLai.getViDo());
                Marker marker = mMap.addMarker(
                        new MarkerOptions()
                                .position(xeLocation)
                                .title(xeTuLai.getTenXe())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(xeLocation, 15));
                mMap.setInfoWindowAdapter(new MyInforAdapter(this,xeTuLai));
                marker.showInfoWindow();
            }
        }
    }
}