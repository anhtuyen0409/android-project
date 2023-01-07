package com.nguyenanhtuyen.appthuexe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.nguyenanhtuyen.adapter.XeYeuCauAdapter;
import com.nguyenanhtuyen.model.User;
import com.nguyenanhtuyen.model.YeuCauDatXe;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DatXeActivity extends AppCompatActivity {
    ListView lvDanhSachYeuCau;
    XeYeuCauAdapter danhSachYeuCauAdapter;
    Button btnChapNhan, btnTuChoi;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_xe);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnTuChoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Intent intent1 = new Intent(DatXeActivity.this, ChuXeMapsActivity.class);
                if (intent != null) {
                    User user = (User) intent.getSerializableExtra("user_danhsachyeucau");
                    if (user != null) {
                        TuChoiYeuCauTask task = new TuChoiYeuCauTask();
                        task.execute(user.getMaThanhVien());
                        intent1.putExtra("user_login",user);
                    }
                }
                startActivity(intent1);
            }
        });
        btnChapNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Intent intent1 = new Intent(DatXeActivity.this, ChuXeMapsActivity.class);
                if (intent != null) {
                    User user = (User) intent.getSerializableExtra("user_danhsachyeucau");
                    if (user != null) {
                        ChapNhanYeuCauTask task = new ChapNhanYeuCauTask();
                        task.execute();
                        intent1.putExtra("user_login",user);
                    }
                }
                startActivity(intent1);
            }
        });
    }

    class ChapNhanYeuCauTask extends AsyncTask<Void,Void,Boolean>{
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true){
                Toast.makeText(DatXeActivity.this, "success", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/datxe/chapnhanyeucau");
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

    class TuChoiYeuCauTask extends AsyncTask<Integer, Void, Boolean>{
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true){
                Toast.makeText(DatXeActivity.this, "Huỷ yêu cầu thành công!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/datxe/tuchoiyeucau?maThanhVien="+integers[0]);
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

    class HienThiXeYeuCauTheoMaThanhVienTask extends AsyncTask<Integer, Void, ArrayList<YeuCauDatXe>>{
        @Override
        protected void onPostExecute(ArrayList<YeuCauDatXe> yeuCauDatXes) {
            super.onPostExecute(yeuCauDatXes);
            danhSachYeuCauAdapter.clear();
            danhSachYeuCauAdapter.addAll(yeuCauDatXes);
        }

        @Override
        protected ArrayList<YeuCauDatXe> doInBackground(Integer... integers) {
            ArrayList<YeuCauDatXe> dsYeuCau = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/xe/hienthixeyeucautheomathanhvien?maThanhVien="+integers[0]);
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
                JSONArray jsonArray = new JSONArray(builder.toString());
                for(int i=0; i<jsonArray.length();i++){
                    YeuCauDatXe yeuCauDatXe = new YeuCauDatXe();
                    yeuCauDatXe.setMaThanhVien(jsonArray.optJSONObject(i).getInt("MaThanhVien"));
                    yeuCauDatXe.setTenHienThi(jsonArray.optJSONObject(i).getString("TenHienThi"));
                    yeuCauDatXe.setTongThanhToan(jsonArray.optJSONObject(i).getInt("TongThanhToan"));
                    yeuCauDatXe.setHinhAnh(jsonArray.optJSONObject(i).getString("HinhAnh"));
                    yeuCauDatXe.setViTriBatDau(jsonArray.optJSONObject(i).getString("ViTriBatDau"));
                    yeuCauDatXe.setViTriKetThuc(jsonArray.optJSONObject(i).getString("ViTriKetThuc"));
                    yeuCauDatXe.setMaDatXe(jsonArray.optJSONObject(i).getInt("MaDatXe"));
                    dsYeuCau.add(yeuCauDatXe);
                }
                br.close();
            }
            catch (Exception ex){
                Log.e("Lỗi",ex.toString());
            }
            return dsYeuCau;
        }
    }

    private void addControls() {
        lvDanhSachYeuCau = findViewById(R.id.lvDanhSachYeuCau);
        danhSachYeuCauAdapter = new XeYeuCauAdapter(DatXeActivity.this, R.layout.item_yeucaudatxe);
        btnChapNhan = findViewById(R.id.btnChapNhan);
        btnTuChoi = findViewById(R.id.btnTuChoi);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng chờ...");

        Intent intent = getIntent();
        if (intent != null) {
            User user = (User) intent.getSerializableExtra("user_danhsachyeucau");
            if (user != null) {
                HienThiXeYeuCauTheoMaThanhVienTask task = new HienThiXeYeuCauTheoMaThanhVienTask();
                task.execute(user.getMaThanhVien());
                lvDanhSachYeuCau.setAdapter(danhSachYeuCauAdapter);
            }
        }

    }
}