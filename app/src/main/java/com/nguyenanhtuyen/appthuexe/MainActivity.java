package com.nguyenanhtuyen.appthuexe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.nguyenanhtuyen.adapter.TinhNangAdapter;
import com.nguyenanhtuyen.adapter.XeCoTaiXeNoiBatAdapter;
import com.nguyenanhtuyen.adapter.XeTuLaiNoiBatAdapter;
import com.nguyenanhtuyen.model.TinhNang;
import com.nguyenanhtuyen.model.User;
import com.nguyenanhtuyen.model.XeCoTaiXe;
import com.nguyenanhtuyen.model.XeTuLai;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView.Adapter tinhNangadapter, xeTuLaiNoiBatadapter, xeCoTaiXeNoiBatadapter,
            xeTuLaiMoiNhatAdapter, xeCoTaiXeMoiNhatAdapter;
    private RecyclerView recyclerViewTinhNang, recyclerViewXeTuLaiNoiBat, recyclerViewXeCoTaiXeNoiBat,
            recyclerViewXeTuLaiMoiNhat, recyclerViewXeCoTaiXeMoiNhat;
    private SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<XeTuLai> dsXeTuLaiNoiBat = new ArrayList<>();
    ArrayList<XeCoTaiXe> dsXeCoTaiXeNoiBat = new ArrayList<>();
    ArrayList<XeTuLai> dsXeTuLaiMoiNhat = new ArrayList<>();
    ArrayList<XeCoTaiXe> dsXeCoTaiXeMoiNhat = new ArrayList<>();
    ImageView ic_CaNhan, ic_HoTro, ic_Chuyen, ic_KhamPha, ic_ThongBao, ic_XeTuLai, ic_XeCoTaiXe, ic_User;
    TextView txtTenHienThi;
    Button btnDangKyChuXe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        ic_CaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyHienThiManHinhProfile();
            }
        });

        ic_XeTuLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyHienThiManHinhThueXeTuLai();
            }
        });

        ic_XeCoTaiXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyHienThiManHinhThueXeCoTaiXe();
            }
        });
        ic_Chuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyHienThiChuyenXeDangDienRa();
            }
        });
        btnDangKyChuXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hienThiManHinhDangKyChuXe();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(MainActivity.this);
    }

    private void xuLyHienThiChuyenXeDangDienRa() {
        Intent intent = getIntent();
        if(intent!=null){
            User u = (User) intent.getSerializableExtra("user_login");
            XeTuLai xeTuLai = (XeTuLai) intent.getSerializableExtra("xetulai_danhgia");
            if(u!=null && xeTuLai != null){
                Intent intent1 = new Intent(MainActivity.this, ThanhToanActivity.class);
                intent1.putExtra("user_chuyenxedangdienra",u);
                intent1.putExtra("xetulai_danhgia2",xeTuLai);
                startActivity(intent1);
            }
        }
        else{
            Toast.makeText(this, "Bạn cần đăng nhập để xem thông tin chuyến xe đang diễn ra!", Toast.LENGTH_SHORT).show();
        }
    }

    private void hienThiManHinhDangKyChuXe() {
        Intent intent = new Intent(MainActivity.this, DangKyChuXeActivity.class);
        startActivity(intent);
    }


    private void xuLyHienThiManHinhThueXeCoTaiXe() {
        Intent intent = new Intent(MainActivity.this, ThueXeCoTaiXeActivity.class);
        startActivity(intent);
    }

    private void xuLyHienThiManHinhThueXeTuLai() {
        //truyền user đã đăng nhập từ main sang thuê xe tụ lái
        Intent intent = getIntent();
        Intent intent2 = new Intent(MainActivity.this, ThueXeTuLaiActivity.class);
        if(intent!=null){
            User u = (User) intent.getSerializableExtra("user_login");
            if(u!=null){
                intent2.putExtra("user_thuexetulai",u);
                Toast.makeText(this, "Bạn chọn xe tự lái", Toast.LENGTH_SHORT).show();
            }
        }
        startActivity(intent2);
    }

    private void xuLyHienThiManHinhProfile() {
        if(txtTenHienThi.getText()=="Xin chào!"){
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);

        }
        else{
            //truyền user đã đăng nhập từ main sang profile
            Intent intent = getIntent();
            Intent intent2 = new Intent(MainActivity.this, ProfileLoggedActivity.class);
            if(intent!=null){
                User u = (User) intent.getSerializableExtra("user_login");
                if(u!=null){
                    intent2.putExtra("user_profile",u);
                    startActivity(intent2);
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        HienThiXeTuLaiMoiNhatTask hienThiXeTuLaiMoiNhatTask = new HienThiXeTuLaiMoiNhatTask();
        hienThiXeTuLaiMoiNhatTask.execute();
        xeTuLaiMoiNhatAdapter = new XeTuLaiNoiBatAdapter(dsXeTuLaiMoiNhat);
        recyclerViewXeTuLaiMoiNhat.setAdapter(xeTuLaiMoiNhatAdapter);

        HienThiXeTuLaiNoiBatTask hienThiXeTuLaiNoiBatTask = new HienThiXeTuLaiNoiBatTask();
        hienThiXeTuLaiNoiBatTask.execute();
        xeTuLaiNoiBatadapter = new XeTuLaiNoiBatAdapter(dsXeTuLaiNoiBat);
        recyclerViewXeTuLaiNoiBat.setAdapter(xeTuLaiNoiBatadapter);

        HienThiXeCoTaiXeMoiNhatTask hienThiXeCoTaiXeMoiNhatTask = new HienThiXeCoTaiXeMoiNhatTask();
        hienThiXeCoTaiXeMoiNhatTask.execute();
        xeCoTaiXeMoiNhatAdapter = new XeCoTaiXeNoiBatAdapter(dsXeCoTaiXeMoiNhat);
        recyclerViewXeCoTaiXeMoiNhat.setAdapter(xeCoTaiXeMoiNhatAdapter);

        HienThiXeCoTaiXeNoiBatTask hienThiXeCoTaiXeNoiBatTask = new HienThiXeCoTaiXeNoiBatTask();
        hienThiXeCoTaiXeNoiBatTask.execute();
        xeCoTaiXeNoiBatadapter = new XeCoTaiXeNoiBatAdapter(dsXeCoTaiXeNoiBat);
        recyclerViewXeCoTaiXeNoiBat.setAdapter(xeCoTaiXeNoiBatadapter);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        },3000);
    }


    class HienThiXeTuLaiNoiBatTask extends AsyncTask<Void, Void, ArrayList<XeTuLai>>{
        @Override
        protected void onPostExecute(ArrayList<XeTuLai> xeTuLais) {
            super.onPostExecute(xeTuLais);
            dsXeTuLaiNoiBat.clear();
            dsXeTuLaiNoiBat.addAll(xeTuLais);
        }

        @Override
        protected ArrayList<XeTuLai> doInBackground(Void... voids) {
            ArrayList<XeTuLai> dsXe = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/xe/hienthixetulainoibat");
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


    class HienThiXeCoTaiXeNoiBatTask extends AsyncTask<Void, Void, ArrayList<XeCoTaiXe>>{
        @Override
        protected void onPostExecute(ArrayList<XeCoTaiXe> xeCoTaiXes) {
            super.onPostExecute(xeCoTaiXes);
            dsXeCoTaiXeNoiBat.clear();
            dsXeCoTaiXeNoiBat.addAll(xeCoTaiXes);
        }

        @Override
        protected ArrayList<XeCoTaiXe> doInBackground(Void... voids) {
            ArrayList<XeCoTaiXe> dsXe = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/xe/hienthixecotaixenoibat");
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

                    XeCoTaiXe xe = new XeCoTaiXe();
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

    class HienThiXeTuLaiMoiNhatTask extends AsyncTask<Void,Void,ArrayList<XeTuLai>>{
        @Override
        protected void onPostExecute(ArrayList<XeTuLai> xeTuLais) {
            super.onPostExecute(xeTuLais);
            dsXeTuLaiMoiNhat.clear();
            dsXeTuLaiMoiNhat.addAll(xeTuLais);
        }

        @Override
        protected ArrayList<XeTuLai> doInBackground(Void... voids) {
            ArrayList<XeTuLai> dsXe = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/xe/hienthixetulaimoinhat");
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

    class HienThiXeCoTaiXeMoiNhatTask extends AsyncTask<Void,Void,ArrayList<XeCoTaiXe>>{
        @Override
        protected void onPostExecute(ArrayList<XeCoTaiXe> xeCoTaiXes) {
            super.onPostExecute(xeCoTaiXes);
            dsXeCoTaiXeMoiNhat.clear();
            dsXeCoTaiXeMoiNhat.addAll(xeCoTaiXes);
        }

        @Override
        protected ArrayList<XeCoTaiXe> doInBackground(Void... voids) {
            ArrayList<XeCoTaiXe> dsXe = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/xe/hienthixecotaixemoinhat");
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

                    XeCoTaiXe xe = new XeCoTaiXe();
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

    private void addControls() {
        //khai bao cac controls
        //imageview
        ic_CaNhan = findViewById(R.id.ic_canhan_tulai);
        ic_Chuyen = findViewById(R.id.ic_chuyen_tulai);
        ic_ThongBao = findViewById(R.id.ic_thongbao_tulai);
        ic_HoTro = findViewById(R.id.ic_hotro_tulai);
        ic_KhamPha = findViewById(R.id.ic_khampha_tulai);
        ic_XeTuLai = findViewById(R.id.ic_xetulai);
        ic_XeCoTaiXe = findViewById(R.id.ic_xecotaixe);
        ic_User = findViewById(R.id.ic_user);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        //button
        btnDangKyChuXe = findViewById(R.id.btnDangKyChuXe);

        recyclerViewTinhNang = findViewById(R.id.viewTinhNang);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerViewTinhNang.setLayoutManager(linearLayoutManager);
        ArrayList<TinhNang> tinhnangs = new ArrayList<>();
        tinhnangs.add(new TinhNang("tn1"));
        tinhnangs.add(new TinhNang("tn2"));
        tinhnangs.add(new TinhNang("tn3"));
        tinhnangs.add(new TinhNang("tn4"));
        tinhnangs.add(new TinhNang("tn5"));
        tinhnangs.add(new TinhNang("tn6"));
        tinhNangadapter = new TinhNangAdapter(tinhnangs);
        recyclerViewTinhNang.setAdapter(tinhNangadapter);

        //hiển thị xe tự lái moi nhat call api
        recyclerViewXeTuLaiMoiNhat = findViewById(R.id.viewXeTuLaiMoiNhat);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewXeTuLaiMoiNhat.setLayoutManager(linearLayoutManager3);
        HienThiXeTuLaiMoiNhatTask hienThiXeTuLaiMoiNhatTask = new HienThiXeTuLaiMoiNhatTask();
        hienThiXeTuLaiMoiNhatTask.execute();
        xeTuLaiMoiNhatAdapter = new XeTuLaiNoiBatAdapter(dsXeTuLaiMoiNhat);
        recyclerViewXeTuLaiMoiNhat.setAdapter(xeTuLaiMoiNhatAdapter);

        //hiển thị xe tự lái gia re call api
        recyclerViewXeTuLaiNoiBat = findViewById(R.id.viewXeTuLaiNoiBat);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewXeTuLaiNoiBat.setLayoutManager(linearLayoutManager1);
        HienThiXeTuLaiNoiBatTask hienThiXeTuLaiNoiBatTask = new HienThiXeTuLaiNoiBatTask();
        hienThiXeTuLaiNoiBatTask.execute();
        xeTuLaiNoiBatadapter = new XeTuLaiNoiBatAdapter(dsXeTuLaiNoiBat);
        recyclerViewXeTuLaiNoiBat.setAdapter(xeTuLaiNoiBatadapter);

        //hiển thị xe tự lái moi nhat call api
        recyclerViewXeCoTaiXeMoiNhat = findViewById(R.id.viewXeCoTaiXeMoiNhat);
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewXeCoTaiXeMoiNhat.setLayoutManager(linearLayoutManager4);
        HienThiXeCoTaiXeMoiNhatTask hienThiXeCoTaiXeMoiNhatTask = new HienThiXeCoTaiXeMoiNhatTask();
        hienThiXeCoTaiXeMoiNhatTask.execute();
        xeCoTaiXeMoiNhatAdapter = new XeCoTaiXeNoiBatAdapter(dsXeCoTaiXeMoiNhat);
        recyclerViewXeCoTaiXeMoiNhat.setAdapter(xeCoTaiXeMoiNhatAdapter);


        //hiển thị xe có tài xế gia re call api
        recyclerViewXeCoTaiXeNoiBat = findViewById(R.id.viewXeCoTaiXeNoiBat);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewXeCoTaiXeNoiBat.setLayoutManager(linearLayoutManager2);
        HienThiXeCoTaiXeNoiBatTask hienThiXeCoTaiXeNoiBatTask = new HienThiXeCoTaiXeNoiBatTask();
        hienThiXeCoTaiXeNoiBatTask.execute();
        xeCoTaiXeNoiBatadapter = new XeCoTaiXeNoiBatAdapter(dsXeCoTaiXeNoiBat);
        recyclerViewXeCoTaiXeNoiBat.setAdapter(xeCoTaiXeNoiBatadapter);

        txtTenHienThi = findViewById(R.id.txtTenHienThi);
        txtTenHienThi.setText("Xin chào!");
        Intent intent = getIntent();
        if(intent!=null){
            User u = (User) intent.getSerializableExtra("user_login");
            if(u!=null){
                txtTenHienThi.setText("Xin chào "+u.getTenHienThi()+"!");
                Glide.with(this).load("http://192.168.1.50/dacn/assets/images/user/"+u.getHinhAnh()).into(ic_User);
            }

        }

    }
}