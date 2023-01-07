package com.nguyenanhtuyen.appthuexe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.nguyenanhtuyen.model.User;
import com.nguyenanhtuyen.model.XeTuLai;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DanhGiaActivity extends AppCompatActivity {
    ImageView imgHinhAnhChuXe;
    TextView txtTenChuXe, txtSoDienThoaiChuXe, txtTenXeDanhGia, txtGiaDanhGia;
    RatingBar ratingBarDiemDanhGia;
    EditText edtNoiDung;
    Button btnGuiDanhGia;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnGuiDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDanhGia();
            }
        });
    }

    private void xuLyDanhGia() {
        Intent intent = getIntent();
        Intent intent1 = new Intent(DanhGiaActivity.this, MainActivity.class);
        if (intent != null) {
            User user = (User) intent.getSerializableExtra("user_danhgia");
            XeTuLai xeTuLai = (XeTuLai) intent.getSerializableExtra("xetulai_danhgia3");
            if (user != null && xeTuLai != null) {
                DanhGiaTask task = new DanhGiaTask();
                task.execute(user.getMaThanhVien()+"", xeTuLai.getMaXe()+"", edtNoiDung.getText().toString(), ratingBarDiemDanhGia.getRating()+"");
            }
        }

    }

    class HienThiChuXeTask extends AsyncTask<Integer,Void,User>{
        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            Glide.with(DanhGiaActivity.this).load("http://192.168.1.50/dacn/assets/images/user/"+user.getHinhAnh()).into(imgHinhAnhChuXe);
            txtTenChuXe.setText(user.getHoTen());
            txtSoDienThoaiChuXe.setText(user.getSdt());
        }

        @Override
        protected User doInBackground(Integer... integers) {
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/xe/hienthichuxe?maThanhVien="+integers[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json; charset-UTF-8");

                InputStreamReader isr = new InputStreamReader(connection.getInputStream(),"UTF-8");
                BufferedReader br = new BufferedReader(isr);
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line=br.readLine())!=null){
                    builder.append(line);
                }
                JSONObject jsonObject = new JSONObject(builder.toString());
                User user = new User();
                user.setMaThanhVien(jsonObject.getInt("MaThanhVien"));
                user.setTenHienThi(jsonObject.getString("TenHienThi"));
                user.setEmail(jsonObject.getString("Email"));
                user.setMatKhau(jsonObject.getString("MatKhau"));
                user.setXacNhanMatKhau(jsonObject.getString("XacNhanMatKhau"));
                user.setHoTen(jsonObject.getString("HoTen"));
                user.setSdt(jsonObject.getString("DienThoai"));
                user.setNgaySinh(jsonObject.getString("NgaySinh"));
                user.setHinhAnh(jsonObject.getString("HinhAnh"));
                user.setMaLoaiThanhVien(jsonObject.getInt("MaLoaiThanhVien"));
                return user;
            }
            catch (Exception ex){
                Log.e("Lỗi",ex.toString());
            }
            return null;
        }
    }

    class DanhGiaTask extends AsyncTask<String,Void,Boolean>{
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true){
                progressDialog.show();
                Intent intent = getIntent();
                Intent intent1 = new Intent(DanhGiaActivity.this, MainActivity.class);
                if (intent != null) {
                    User user = (User) intent.getSerializableExtra("user_danhgia");
                    XeTuLai xeTuLai = (XeTuLai) intent.getSerializableExtra("xetulai_danhgia3");
                    if (user != null && xeTuLai != null) {
                        intent1.putExtra("user_login",user);
                        Toast.makeText(DanhGiaActivity.this, "Cảm ơn bạn đã gửi đánh giá đến chủ xe! Chúc bạn một ngày tốt lành.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                startActivity(intent1);
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/danhgia/danhgia?maThanhVien="+strings[0]+"&maXe="+strings[1]+
                        "&noiDung="+strings[2]+"&diem="+strings[3]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json; charset-UTF-8");

                InputStreamReader isr = new InputStreamReader(connection.getInputStream(),"UTF-8");
                BufferedReader br = new BufferedReader(isr);
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line=br.readLine())!=null){
                    builder.append(line);
                }
                boolean kq = builder.toString().contains("true");
                return kq;
            }
            catch (Exception ex){
                Log.e("Lỗi",ex.toString());
            }
            return false;
        }
    }

    private void addControls() {
        imgHinhAnhChuXe = findViewById(R.id.imgHinhAnhChuXe);
        txtTenChuXe = findViewById(R.id.txtTenChuXe);
        txtGiaDanhGia = findViewById(R.id.txtGia_DanhGia);
        txtSoDienThoaiChuXe = findViewById(R.id.txtSoDienThoaiChuXe);
        txtTenXeDanhGia = findViewById(R.id.txtTenXe_DanhGia);
        ratingBarDiemDanhGia = findViewById(R.id.ratingDiem_DanhGia);
        edtNoiDung = findViewById(R.id.edtNoiDung);
        btnGuiDanhGia = findViewById(R.id.btnGuiDanhGia);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng chờ...");
        Intent intent = getIntent();
        if (intent != null) {
            User user = (User) intent.getSerializableExtra("user_danhgia");
            XeTuLai xeTuLai = (XeTuLai) intent.getSerializableExtra("xetulai_danhgia3");
            if (user != null && xeTuLai != null) {
                HienThiChuXeTask task = new HienThiChuXeTask();
                task.execute(xeTuLai.getMaThanhVien());
                txtTenXeDanhGia.setText(xeTuLai.getTenXe());
                txtGiaDanhGia.setText(xeTuLai.getGiaThue()+"");
            }
        }
    }
}