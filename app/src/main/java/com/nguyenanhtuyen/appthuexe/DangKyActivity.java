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
import android.widget.TextView;
import android.widget.Toast;
import com.nguyenanhtuyen.model.TaiKhoan;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;



public class DangKyActivity extends AppCompatActivity {
    EditText edtTenHienThi, edtEmail, edtMatKhau, edtXacNhanMatKhau;
    CheckBox chkDongY;
    TextView txtChinhSach;
    Button btnDangKyThanhVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnDangKyThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               xuLyDangKyThanhVien();
            }
        });
    }

    private void xuLyDangKyThanhVien() {

        if(edtEmail.getText().toString().contentEquals("")){
            Toast.makeText(this, "Bạn chưa nhập email!", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(edtMatKhau.getText().toString().contentEquals("")){
            Toast.makeText(this,"Bạn chưa nhập mật khẩu!",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(edtXacNhanMatKhau.getText().toString().contentEquals("")){
            Toast.makeText(this, "Bạn chưa nhập xác nhận mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(edtTenHienThi.getText().toString().contentEquals("")){
            Toast.makeText(this, "Bạn chưa nhập tên hiển thị!", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!chkDongY.isChecked()){
            Toast.makeText(this, "Bạn chưa đồng ý với điều khoản!", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            TaiKhoan tk = new TaiKhoan();
            tk.setTenHienThi(edtTenHienThi.getText().toString());
            tk.setEmail(edtEmail.getText().toString());
            tk.setMatKhau(edtMatKhau.getText().toString());
            tk.setXacNhanMatKhau(edtXacNhanMatKhau.getText().toString());
            DangKyThanhVienTask task = new DangKyThanhVienTask();
            task.execute(tk);
        }
    }

    class DangKyThanhVienTask extends AsyncTask<TaiKhoan,Void,Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true){
                Toast.makeText(DangKyActivity.this,"Đăng ký thành công!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DangKyActivity.this,DangNhapActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(DangKyActivity.this,"Email đã tồn tại!",Toast.LENGTH_SHORT).show();
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
                String params = "?tenHienThi="+ URLEncoder.encode(tk.getTenHienThi())+"&email="+tk.getEmail()+"&matKhau="+tk.getMatKhau()+"&xacNhanMatKhau="+tk.getXacNhanMatKhau();
                URL url = new URL("http://192.168.1.50/dacn/api/user/dangky"+params);
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
        edtTenHienThi = findViewById(R.id.edtTenHienThi);

        edtEmail = findViewById(R.id.edtEmail);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtXacNhanMatKhau = findViewById(R.id.edtXacNhanMatKhau);
        btnDangKyThanhVien = findViewById(R.id.btnDangKyThanhVien);
        chkDongY = findViewById(R.id.chkDongY);
        txtChinhSach = findViewById(R.id.txtChinhSach);
    }
}