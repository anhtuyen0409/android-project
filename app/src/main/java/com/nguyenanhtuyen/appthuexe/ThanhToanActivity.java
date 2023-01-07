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
import com.nguyenanhtuyen.adapter.ChuyenXeDangDienRaAdapter;
import com.nguyenanhtuyen.model.ChuyenXeDangDienRa;
import com.nguyenanhtuyen.model.User;
import com.nguyenanhtuyen.model.XeTuLai;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ThanhToanActivity extends AppCompatActivity {
    ListView lvDanhSachChuyenXeDangDienRa;
    ChuyenXeDangDienRaAdapter danhSachChuyenXeDangDienRaAdapter;
    Button btnKetThucChuyen;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnKetThucChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                if (intent != null) {
                    User user = (User) intent.getSerializableExtra("user_chuyenxedangdienra");
                    XeTuLai xeTuLai = (XeTuLai) intent.getSerializableExtra("xetulai_danhgia2");
                    if (user != null && xeTuLai != null) {
                        KetThucChuyenTask task = new KetThucChuyenTask();
                        task.execute(xeTuLai.getMaXe());
                    }
                }

            }
        });
    }

    class KetThucChuyenTask extends AsyncTask<Integer,Void,Boolean>{
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true){
                Intent intent = getIntent();
                Intent intent1 = new Intent(ThanhToanActivity.this, DanhGiaActivity.class);
                if (intent != null) {
                    User user = (User) intent.getSerializableExtra("user_chuyenxedangdienra");
                    XeTuLai xeTuLai = (XeTuLai) intent.getSerializableExtra("xetulai_danhgia2");
                    if (user != null && xeTuLai != null) {
                        intent1.putExtra("user_danhgia",user);
                        intent1.putExtra("xetulai_danhgia3",xeTuLai);
                    }
                }
                startActivity(intent1);
            }
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/datxe/ketthucchuyen?maXe="+integers[0]);
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

    class HienThiChuyenXeDangDienRaCuaKhachHangTask extends AsyncTask<Integer,Void, ArrayList<ChuyenXeDangDienRa>>{
        @Override
        protected void onPostExecute(ArrayList<ChuyenXeDangDienRa> chuyenXeDangDienRas) {
            super.onPostExecute(chuyenXeDangDienRas);
            danhSachChuyenXeDangDienRaAdapter.clear();
            danhSachChuyenXeDangDienRaAdapter.addAll(chuyenXeDangDienRas);
        }

        @Override
        protected ArrayList<ChuyenXeDangDienRa> doInBackground(Integer... integers) {
            ArrayList<ChuyenXeDangDienRa> dsChuyen = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/datxe/hienthichuyenxedangdienracuakhachhang?maThanhVien="+integers[0]);
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
                    ChuyenXeDangDienRa chuyenXeDangDienRa = new ChuyenXeDangDienRa();
                    chuyenXeDangDienRa.setMaDatXe(jsonArray.optJSONObject(i).getInt("MaDatXe"));
                    chuyenXeDangDienRa.setNgayDat(jsonArray.optJSONObject(i).getString("NgayDat"));
                    chuyenXeDangDienRa.setNgayKetThuc(jsonArray.optJSONObject(i).getString("NgayKetThuc"));
                    chuyenXeDangDienRa.setTenHinhThuc(jsonArray.optJSONObject(i).getString("TenHinhThuc"));
                    chuyenXeDangDienRa.setTongThanhToan(jsonArray.optJSONObject(i).getInt("TongThanhToan"));

                    dsChuyen.add(chuyenXeDangDienRa);
                }
                br.close();
            }
            catch (Exception ex){
                Log.e("Lỗi",ex.toString());
            }
            return dsChuyen;
        }
    }

    private void addControls() {
        btnKetThucChuyen = findViewById(R.id.btnKetThucChuyen);
        lvDanhSachChuyenXeDangDienRa = findViewById(R.id.lvDanhSachChuyenXeDangDienRa);
        danhSachChuyenXeDangDienRaAdapter = new ChuyenXeDangDienRaAdapter(ThanhToanActivity.this, R.layout.item_chuyenxedangdienra);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng chờ...");

        Intent intent = getIntent();
        if (intent != null) {
            User user = (User) intent.getSerializableExtra("user_chuyenxedangdienra");
            if (user != null) {
                HienThiChuyenXeDangDienRaCuaKhachHangTask task = new HienThiChuyenXeDangDienRaCuaKhachHangTask();
                task.execute(user.getMaThanhVien());
                lvDanhSachChuyenXeDangDienRa.setAdapter(danhSachChuyenXeDangDienRaAdapter);
            }
        }
    }
}