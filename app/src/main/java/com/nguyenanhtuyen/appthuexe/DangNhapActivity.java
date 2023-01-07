package com.nguyenanhtuyen.appthuexe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.nguyenanhtuyen.model.User;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class DangNhapActivity extends AppCompatActivity {
    EditText edtNhapEmail, edtNhapMatKhau;
    Button btnDangNhap;
    TextView txtQuenMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDangNhap();
            }
        });
    }

    private void xuLyDangNhap() {

        DangNhapTask task = new DangNhapTask();
       task.execute(edtNhapEmail.getText().toString(),edtNhapMatKhau.getText().toString());
       LayUserKhiDangNhapTask layUserKhiDangNhapTask = new LayUserKhiDangNhapTask();
        layUserKhiDangNhapTask.execute(edtNhapEmail.getText().toString(), edtNhapMatKhau.getText().toString());

    }

   class DangNhapTask extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.contains("admin")){
                Toast.makeText(DangNhapActivity.this,"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
            }
            else if(s.contains("khachhang")){
                Toast.makeText(DangNhapActivity.this,"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
            }

            else if(s.contains("taixe")){
                Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            }
            else if(s.contains("chuxe")){
                Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(DangNhapActivity.this,"Đăng nhập thất bại!",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/user/dangnhap?email="+strings[0]+"&matKhau="+strings[1]);
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

                if(builder.toString().contains("admin")){
                    return "admin";
                }
                else if(builder.toString().contains("khachhang")){
                    return "khachhang";
                }

                else if(builder.toString().contains("taixe")){
                    return "taixe";
                }
                else if(builder.toString().contains("chuxe")){
                    return "chuxe";
                }
                else{
                    return "";
                }
            }
            catch (Exception ex){
                Log.e("Lỗi",ex.toString());
            }
            return "";
        }
    }

    class LayUserKhiDangNhapTask extends AsyncTask<String, Void, User>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            //truyền user đăng nhập sang main
            if(user.getMaLoaiThanhVien()==2){
                Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                intent.putExtra("user_login",user);
                startActivity(intent);
            }
            else if(user.getMaLoaiThanhVien()==3 || user.getMaLoaiThanhVien()==7){
                Intent intent = new Intent(DangNhapActivity.this, ChuXeMapsActivity.class);
                intent.putExtra("user_login",user);
                startActivity(intent);
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected User doInBackground(String... strings) {
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/user/layuserkhidangnhap?email="+strings[0]+"&matKhau="+strings[1]);
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

    private void addControls() {
        edtNhapEmail = findViewById(R.id.edtNhapEmail);
        edtNhapMatKhau = findViewById(R.id.edtNhapMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        txtQuenMatKhau = findViewById(R.id.txtQuenMatKhau);
    }
}