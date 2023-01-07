package com.nguyenanhtuyen.appthuexe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.nguyenanhtuyen.model.DatXe;
import com.nguyenanhtuyen.model.User;
import com.nguyenanhtuyen.model.XeTuLai;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class XacNhanDatXeActivity extends AppCompatActivity {
    Button btnXacNhanDatXe;
    TextView txtTenXe, txtNgayDatXe, txtNgayKetThuc, txtViTriNhanXe, txtTongThanhToan;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_dat_xe);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnXacNhanDatXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                if(intent!=null) {
                    DatXe datXe = (DatXe) intent.getSerializableExtra("datxe");
                    XeTuLai xeTuLai = (XeTuLai) intent.getSerializableExtra("xetulai");
                    User user = (User) intent.getSerializableExtra("user_xacnhan");
                    if (datXe != null && xeTuLai != null && user != null) {

                        ThemChiTietDatXeTask task = new ThemChiTietDatXeTask();
                        task.execute(datXe.getMaDatXe()+"",xeTuLai.getMaXe()+"",xeTuLai.getGiaThue()+"");
                        //xử lý gửi thông báo
                        String title = "Thông báo hệ thống";
                        String message = "Bạn có yêu cầu đặt xe mới!";
                        FCMSend.pushNotification(XacNhanDatXeActivity.this,
                                "fSpEYsnOQ5Ojxxekq1-Xs_:APA91bEJFMqvBogYfybnqihm-NMAhMDfbfkTboTeQHzBQWgSXOwAMbsFvJgeL9OI97HgAiyAWOKwqqrql08suOoLrFn_IFFNuKWXZ6IBeN-pmgFk1p7tfDlxVuxEjUUxhWethwdcIBoj"
                                ,title,
                                message);
                        progressDialog.dismiss();
                        Intent intent1 = new Intent(XacNhanDatXeActivity.this, MainActivity.class);
                        intent1.putExtra("user_login",user);
                        intent1.putExtra("xetulai_danhgia",xeTuLai);
                        startActivity(intent1);
                    }
                }
            }
        });
    }

    class ThemChiTietDatXeTask extends AsyncTask<String, Void, Boolean>{
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true){
                Toast.makeText(XacNhanDatXeActivity.this, "Dat xe thanh cong!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(XacNhanDatXeActivity.this,"Bạn đã đặt xe này rồi! Vui lòng chờ...",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/datxe/themchitietdatxe?maDatXe="+strings[0]+"&maXe="+strings[1]+"&tongThanhToan="+strings[2]);
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
        btnXacNhanDatXe = findViewById(R.id.btnXacNhanDatXe);
        txtTenXe = findViewById(R.id.txtTenXe);
        txtNgayDatXe = findViewById(R.id.txtNgayDatXe);
        txtNgayKetThuc = findViewById(R.id.txtNgayKetThuc);
        txtViTriNhanXe = findViewById(R.id.txtViTriNhanXe);
        txtTongThanhToan = findViewById(R.id.txtTongThanhToan);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng chờ...");

        Intent intent = getIntent();
        if(intent!=null){
            DatXe datXe = (DatXe) intent.getSerializableExtra("datxe");
            XeTuLai xeTuLai = (XeTuLai) intent.getSerializableExtra("xetulai");
            if(datXe!=null && xeTuLai!=null){
                txtTenXe.setText(xeTuLai.getTenXe());
                txtNgayDatXe.setText(datXe.getNgayDat());
                txtNgayKetThuc.setText(datXe.getNgayKetThuc());
                txtViTriNhanXe.setText(datXe.getViTriNhanXe());
                txtTongThanhToan.setText(xeTuLai.getGiaThue()+"");

            }
        }
    }
}