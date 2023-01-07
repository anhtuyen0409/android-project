package com.nguyenanhtuyen.appthuexe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity {
    EditText edtDangNhapBangEmailHoacSDT;
    Button btnDangNhapBangFacebook, btnDangKy, btnDangNhapBangGoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        addControls();
        addEvents();
    }

    private void addEvents() {
        edtDangNhapBangEmailHoacSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyMoManHinhDangNhap();
            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyMoManHinhDangKy();
            }
        });
    }

    private void xuLyMoManHinhDangKy() {
        Intent intent = new Intent(ProfileActivity.this, DangKyActivity.class);
        startActivity(intent);
    }

    private void xuLyMoManHinhDangNhap() {
        Intent intent = new Intent(ProfileActivity.this, DangNhapActivity.class);
        startActivity(intent);
    }

    private void addControls() {
        edtDangNhapBangEmailHoacSDT = findViewById(R.id.edtDangNhapBangEmailHoacSDT);
        btnDangNhapBangGoogle = findViewById(R.id.btnDangNhapBangGoogle);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnDangNhapBangFacebook = findViewById(R.id.btnDangNhapBangFacebook);
    }
}