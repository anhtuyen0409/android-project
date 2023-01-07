package com.nguyenanhtuyen.appthuexe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.nguyenanhtuyen.adapter.XeTuLaiAdapter;
import com.nguyenanhtuyen.model.User;
import com.nguyenanhtuyen.model.XeTuLai;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



public class DanhSachXeActivity extends AppCompatActivity {
    ListView lvDanhSachXe;
    XeTuLaiAdapter danhSachXeAdapter;
    ArrayList<XeTuLai> dsXe;
    Button btnDangXeNgay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_xe);
        addControls();
        addEvents();
    }

    class HienThiXeTheoMaThanhVienTask extends AsyncTask<Integer, Void, ArrayList<XeTuLai>>{
        @Override
        protected void onPostExecute(ArrayList<XeTuLai> xeTuLais) {
            super.onPostExecute(xeTuLais);
            danhSachXeAdapter.clear();
            danhSachXeAdapter.addAll(xeTuLais);
        }

        @Override
        protected ArrayList<XeTuLai> doInBackground(Integer... integers) {
            dsXe = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/xe/hienthixetheomathanhvien?maThanhVien="+integers[0]);
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
                    XeTuLai xe = new XeTuLai();
                    xe.setMaXe(jsonArray.optJSONObject(i).getInt("MaXe"));
                    xe.setTenXe(jsonArray.optJSONObject(i).getString("TenXe"));
                    xe.setGiaThue(jsonArray.optJSONObject(i).getInt("Gia"));
                    xe.setHinhAnh(jsonArray.optJSONObject(i).getString("HinhAnh"));
                    xe.setMoTa(jsonArray.optJSONObject(i).getString("MoTa"));
                    xe.setMaNhaSanXuat(jsonArray.optJSONObject(i).getInt("MaNhaSanXuat"));
                    xe.setMaLoaiXe(jsonArray.optJSONObject(i).getInt("MaLoaiXe"));
                    xe.setMaThanhVien(jsonArray.optJSONObject(i).getInt("MaThanhVien"));
                    xe.setStatus(jsonArray.optJSONObject(i).getInt("Status"));
                    xe.setDiaChi(jsonArray.optJSONObject(i).getString("DiaChi"));
                    xe.setKinhDo(jsonArray.optJSONObject(i).getDouble("KinhDo"));
                    xe.setViDo(jsonArray.optJSONObject(i).getDouble("ViDo"));
                    dsXe.add(xe);
                }
                br.close();
            }
            catch (Exception ex){
                Log.e("Lỗi",ex.toString());
            }
            return dsXe;
        }
    }

    class HienThiChiTietXeTuLaiTask extends AsyncTask<Integer, Void, XeTuLai>{

        @Override
        protected void onPostExecute(XeTuLai xeTuLai) {
            super.onPostExecute(xeTuLai);
            Intent intent = new Intent(DanhSachXeActivity.this, ChinhSuaThongTinXeActivity.class);
            intent.putExtra("object_chinhsuaxe",xeTuLai);
            startActivity(intent);
        }


        @Override
        protected XeTuLai doInBackground(Integer... integers) {
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/xe/chitietxetulai?id="+integers[0]);
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
                XeTuLai xeTuLai = new XeTuLai();
                xeTuLai.setMaXe(jsonObject.getInt("MaXe"));
                xeTuLai.setTenXe(jsonObject.getString("TenXe"));
                xeTuLai.setGiaThue(jsonObject.getInt("Gia"));
                xeTuLai.setHinhAnh(jsonObject.getString("HinhAnh"));
                xeTuLai.setMoTa(jsonObject.getString("MoTa"));
                xeTuLai.setMaNhaSanXuat(jsonObject.getInt("MaNhaSanXuat"));
                xeTuLai.setMaLoaiXe(jsonObject.getInt("MaLoaiXe"));
                xeTuLai.setMaThanhVien(jsonObject.getInt("MaThanhVien"));
                xeTuLai.setStatus(jsonObject.getInt("Status"));
                xeTuLai.setDiaChi(jsonObject.getString("DiaChi"));
                xeTuLai.setKinhDo(jsonObject.getDouble("KinhDo"));
                xeTuLai.setViDo(jsonObject.getDouble("ViDo"));
                return xeTuLai;
            }
            catch (Exception ex){
                Log.e("Lỗi",ex.toString());
            }
            return null;
        }
    }

    private void addControls() {
        lvDanhSachXe = findViewById(R.id.lvDanhSachXe);
        danhSachXeAdapter = new XeTuLaiAdapter(DanhSachXeActivity.this, R.layout.item_xetulai);
        btnDangXeNgay = findViewById(R.id.btnDangXeNgay);
        //hiển thị toàn bộ xe tự lái call api
        Intent intent = getIntent();
        if(intent!=null){
            User u = (User) intent.getSerializableExtra("user_danhsachxe");
            if(u!=null){
                HienThiXeTheoMaThanhVienTask task = new HienThiXeTheoMaThanhVienTask();
                task.execute(u.getMaThanhVien());
                lvDanhSachXe.setAdapter(danhSachXeAdapter);
            }
        }

    }
    private void addEvents(){
        lvDanhSachXe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HienThiChiTietXeTuLaiTask hienThiChiTietXeTuLaiTask = new HienThiChiTietXeTuLaiTask();
                hienThiChiTietXeTuLaiTask.execute(danhSachXeAdapter.getItem(i).getMaXe());
            }
        });
        btnDangXeNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                if(intent!=null){
                    User u = (User) intent.getSerializableExtra("user_danhsachxe");
                    if(u!=null){
                        Intent intent2 = new Intent(DanhSachXeActivity.this, DangKyXeChoThueActivity.class);
                        intent2.putExtra("user_themxetulai",u);
                        startActivity(intent2);
                    }
                }
            }
        });
    }
}