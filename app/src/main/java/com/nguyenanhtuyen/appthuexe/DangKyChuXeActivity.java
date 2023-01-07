package com.nguyenanhtuyen.appthuexe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.nguyenanhtuyen.model.TaiKhoan;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class DangKyChuXeActivity extends AppCompatActivity {
    EditText edtHoTenChuXe, edtNgaySinhChuXe, edtSDTChuXe, edtTenHienThiChuXe, edtEmailChuXe, edtMatKhauChuXe, edtXacNhanMatKhauChuXe;
    CheckBox chkDongYChuXe;
    RadioButton radChiChoThue, radTroThanhTaiXe;
    Button btnDangKyThanhVienChuXe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_chu_xe);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnDangKyThanhVienChuXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDangKyChuXe();
            }
        });
    }

    private void xuLyDangKyChuXe() {
        if(radChiChoThue.isChecked()){
            TaiKhoan tk = new TaiKhoan();
            tk.setHoTen(edtHoTenChuXe.getText().toString());
            tk.setNgaySinh(edtNgaySinhChuXe.getText().toString());
            tk.setSdt(edtSDTChuXe.getText().toString());
            tk.setTenHienThi(edtTenHienThiChuXe.getText().toString());
            tk.setEmail(edtEmailChuXe.getText().toString());
            tk.setMatKhau(edtMatKhauChuXe.getText().toString());
            tk.setXacNhanMatKhau(edtXacNhanMatKhauChuXe.getText().toString());
            DangKyChuXeTask task = new DangKyChuXeTask();
            task.execute(tk);
        }
        else if(radTroThanhTaiXe.isChecked()){
            TaiKhoan tk = new TaiKhoan();
            tk.setHoTen(edtHoTenChuXe.getText().toString());
            tk.setNgaySinh(edtNgaySinhChuXe.getText().toString());
            tk.setSdt(edtSDTChuXe.getText().toString());
            tk.setTenHienThi(edtTenHienThiChuXe.getText().toString());
            tk.setEmail(edtEmailChuXe.getText().toString());
            tk.setMatKhau(edtMatKhauChuXe.getText().toString());
            tk.setXacNhanMatKhau(edtXacNhanMatKhauChuXe.getText().toString());
            DangKyTaiXeTask task = new DangKyTaiXeTask();
            task.execute(tk);
        }
    }

    class DangKyTaiXeTask extends AsyncTask<TaiKhoan, Void, Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true){

                Toast.makeText(DangKyChuXeActivity.this,"Đăng ký thành công!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DangKyChuXeActivity.this, DangNhapActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(DangKyChuXeActivity.this,"Email đã tồn tại!",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(TaiKhoan... taiKhoans) {
            try {
                TaiKhoan tk = taiKhoans[0];
                String params = "?tenHienThi="+ URLEncoder.encode(tk.getTenHienThi())+"&email="+tk.getEmail()+"&matKhau="
                        +tk.getMatKhau()+"&xacNhanMatKhau="+tk.getXacNhanMatKhau()+"&sdt="+tk.getSdt()+"&hoTen="+tk.getHoTen()
                        +"&ngaySinh="+tk.getNgaySinh();
                URL url = new URL("http://192.168.1.50/dacn/api/user/dangkytaixe"+params);
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

    class DangKyChuXeTask extends AsyncTask<TaiKhoan, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true){
                Toast.makeText(DangKyChuXeActivity.this,"Đăng ký thành công!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DangKyChuXeActivity.this, DangNhapActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(DangKyChuXeActivity.this,"Email đã tồn tại!",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(TaiKhoan... taiKhoans) {
            try {
                TaiKhoan tk = taiKhoans[0];
                String params = "?tenHienThi="+ URLEncoder.encode(tk.getTenHienThi())+"&email="+tk.getEmail()+"&matKhau="
                        +tk.getMatKhau()+"&xacNhanMatKhau="+tk.getXacNhanMatKhau()+"&sdt="+tk.getSdt()+"&hoTen="+tk.getHoTen()
                        +"&ngaySinh="+tk.getNgaySinh();
                URL url = new URL("http://192.168.1.50/dacn/api/user/dangkychuxe"+params);
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
        //khai báo các control
        edtHoTenChuXe = findViewById(R.id.edtHoTen_ChuXe);
        edtNgaySinhChuXe = findViewById(R.id.edtNgaySinh_ChuXe);
        edtSDTChuXe = findViewById(R.id.edtSDT_ChuXe);
        edtTenHienThiChuXe = findViewById(R.id.edtTenHienThi_ChuXe);
        edtEmailChuXe = findViewById(R.id.edtEmail_ChuXe);
        edtMatKhauChuXe = findViewById(R.id.edtMatKhau_ChuXe);
        edtXacNhanMatKhauChuXe = findViewById(R.id.edtXacNhanMatKhau_ChuXe);
        chkDongYChuXe = findViewById(R.id.chkDongY_ChuXe);
        radChiChoThue = findViewById(R.id.radChiChoThue);
        radTroThanhTaiXe = findViewById(R.id.radTroThanhTaiXe);
        btnDangKyThanhVienChuXe = findViewById(R.id.btnDangKyThanhVien_ChuXe);
    }
}