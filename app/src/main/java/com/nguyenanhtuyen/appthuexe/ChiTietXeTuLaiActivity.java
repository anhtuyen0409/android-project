package com.nguyenanhtuyen.appthuexe;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.nguyenanhtuyen.adapter.DanhGiaAdapter;
import com.nguyenanhtuyen.model.DanhGia;
import com.nguyenanhtuyen.model.DatXe;
import com.nguyenanhtuyen.model.HinhThucThanhToan;
import com.nguyenanhtuyen.model.User;
import com.nguyenanhtuyen.model.XeTuLai;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ChiTietXeTuLaiActivity extends AppCompatActivity {
    ImageView imgHinhAnhChiTietXeTuLai, imgHinhAnhChuXe;
    TextView txtTenChiTietXeTuLai, txtGiaChiTietXeTuLai, txtDonGiaThueXeTuLai, txtThoiGianBatDau,
            txtThoiGianKetThuc, txtTenXeTuLaiTitle, txtMoTaXeTuLai, txtTongCong, txtHoTenChuXe, txtSoDienThoaiChuXe;
    Button btnThayDoiThoiGian, btnDatXeTuLai, btnBanDoXeTuLai;
    EditText edtViTriXe, edtViTriGiaoNhanXe;
    private ProgressDialog progressDialog;
    Spinner spinnerHinhThucThanhToan;
    ArrayAdapter<HinhThucThanhToan> hinhThucAdapter;
    ListView lvDanhGia;
    DanhGiaAdapter danhSachDanhGiaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_xe_tu_lai);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnDatXeTuLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThueXeTuLai();
            }
        });
        btnBanDoXeTuLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                if(intent!=null){
                    XeTuLai xeTuLai = (XeTuLai) intent.getSerializableExtra("object_xetulai");
                    if(xeTuLai!=null){
                        Intent intent1 = new Intent(ChiTietXeTuLaiActivity.this, XeTuLaiMapsActivity.class);
                        intent1.putExtra("xetulaimaps",xeTuLai);
                        startActivity(intent1);
                    }
                }
            }
        });
    }

    private void xuLyThueXeTuLai() {
        progressDialog.show();
        Intent intent = getIntent();
        if(intent!=null){
            User u = (User) intent.getSerializableExtra("user_chitietxetulai");
            XeTuLai xeTuLai = (XeTuLai) intent.getSerializableExtra("object_xetulai");
            if(u!=null && xeTuLai!=null){
                if(u.getMaLoaiThanhVien()==2){
                    HinhThucThanhToan hinhThucThanhToan = (HinhThucThanhToan) spinnerHinhThucThanhToan.getSelectedItem();
                    DatXeTuLaiTask task = new DatXeTuLaiTask();
                    task.execute(u.getMaThanhVien()+"",txtThoiGianBatDau.getText().toString(), txtThoiGianKetThuc.getText().toString(),
                            edtViTriXe.getText().toString(), edtViTriGiaoNhanXe.getText().toString(),hinhThucThanhToan.getMaHinhThuc()+"");
                    LayThongTinKhiDatXeTask layThongTinKhiDatXeTask = new LayThongTinKhiDatXeTask();
                    layThongTinKhiDatXeTask.execute(u.getMaThanhVien()+"",txtThoiGianBatDau.getText().toString(), txtThoiGianKetThuc.getText().toString(),
                            edtViTriXe.getText().toString(), edtViTriGiaoNhanXe.getText().toString());
                }
            }
            else{
                Toast.makeText(this, "Chưa đăng nhập", Toast.LENGTH_SHORT).show();
            }
        }
    }


    class DatXeTuLaiTask extends AsyncTask<String, Void, Boolean>{
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true){
                Toast.makeText(ChiTietXeTuLaiActivity.this, "success", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(ChiTietXeTuLaiActivity.this,"Đat xe thất bại!",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/datxe/datxetulai?maThanhVien="+strings[0]+"&ngayDat="+strings[1]+
                        "&ngayKetThuc="+strings[2]+"&viTriXe="+strings[3]+"&viTriNhanXe="+strings[4]+"&maHinhThuc="+strings[5]);
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

    class LayThongTinKhiDatXeTask extends AsyncTask<String, Void, DatXe>{
        @Override
        protected void onPostExecute(DatXe datXe) {
            super.onPostExecute(datXe);
            Intent intent = getIntent();
            if(intent!=null) {
                User user = (User) intent.getSerializableExtra("user_chitietxetulai");
                XeTuLai xeTuLai = (XeTuLai) intent.getSerializableExtra("object_xetulai");
                if (xeTuLai != null) {
                    Intent intent2 = new Intent(ChiTietXeTuLaiActivity.this, XacNhanDatXeActivity.class);
                    intent2.putExtra("datxe",datXe);
                    intent2.putExtra("xetulai",xeTuLai);
                    intent2.putExtra("user_xacnhan",user);
                    startActivity(intent2);
                }
            }
        }
        @Override
        protected DatXe doInBackground(String... strings) {
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/datxe/laythongtinkhidatxe?maThanhVien="+strings[0]
                        +"&ngayDat="+strings[1]+"&ngayKetThuc="+strings[2]+"&viTriXe="+strings[3]+"&viTriNhanXe="+strings[4]);
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
                DatXe dx = new DatXe();
                dx.setMaDatXe(jsonObject.getInt("MaDatXe"));
                dx.setMaThanhVien(jsonObject.getInt("MaThanhVien"));
                dx.setNgayDat(jsonObject.getString("NgayDat"));
                dx.setNgayKetThuc(jsonObject.getString("NgayKetThuc"));
                dx.setViTriXe(jsonObject.getString("ViTriBatDau"));
                dx.setViTriNhanXe(jsonObject.getString("ViTriKetThuc"));

                return dx;
            }
            catch (Exception ex){
                Log.e("Lỗi",ex.toString());
            }
            return null;
        }
    }

    class HienThiToanBoHinhThucThanhToanTask extends AsyncTask<Void,Void, ArrayList<HinhThucThanhToan>>{
        @Override
        protected void onPostExecute(ArrayList<HinhThucThanhToan> hinhThucThanhToans) {
            super.onPostExecute(hinhThucThanhToans);
            hinhThucAdapter.clear();
            hinhThucAdapter.addAll(hinhThucThanhToans);
        }

        @Override
        protected ArrayList<HinhThucThanhToan> doInBackground(Void... voids) {
            ArrayList<HinhThucThanhToan> dsHinhThuc = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/hinhthucthanhtoan/hienthitoanbohinhthucthanhtoan");
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

                    HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
                    hinhThucThanhToan.setTenHinhThuc(jsonArray.optJSONObject(i).getString("TenHinhThuc"));
                    hinhThucThanhToan.setMaHinhThuc(jsonArray.optJSONObject(i).getInt("MaHinhThuc"));
                    dsHinhThuc.add(hinhThucThanhToan);
                }
                br.close();
            }
            catch (Exception ex){
                Log.e("Lỗi",ex.toString());
            }
            return dsHinhThuc;
        }
    }

    class HienThiChuXeTask extends AsyncTask<Integer,Void,User>{
        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            Glide.with(ChiTietXeTuLaiActivity.this).load("http://192.168.1.50/dacn/assets/images/user/"+user.getHinhAnh()).into(imgHinhAnhChuXe);
            txtHoTenChuXe.setText(user.getHoTen());
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

    class HienThiDanhGiaTheoMaXeTask extends AsyncTask<Integer,Void,ArrayList<DanhGia>>{
        @Override
        protected void onPostExecute(ArrayList<DanhGia> danhGias) {
            super.onPostExecute(danhGias);
            danhSachDanhGiaAdapter.clear();
            danhSachDanhGiaAdapter.addAll(danhGias);
        }

        @Override
        protected ArrayList<DanhGia> doInBackground(Integer... integers) {
            ArrayList<DanhGia> dsDanhGia = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/danhgia/hienthidanhgiatheomaxe?maXe="+integers[0]);
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
                    DanhGia danhGia = new DanhGia();
                    danhGia.setHinhAnh(jsonArray.optJSONObject(i).getString("HinhAnh"));
                    danhGia.setTenHienThi(jsonArray.optJSONObject(i).getString("TenHienThi"));
                    danhGia.setDiem(jsonArray.optJSONObject(i).getDouble("Diem"));
                    danhGia.setNoiDung(jsonArray.optJSONObject(i).getString("NoiDung"));
                    dsDanhGia.add(danhGia);
                }
                br.close();
            }
            catch (Exception ex){
                Log.e("Lỗi",ex.toString());
            }
            return dsDanhGia;
        }
    }

    private void addControls() {
        spinnerHinhThucThanhToan = findViewById(R.id.spinnerHinhThucThanhToan);
        imgHinhAnhChiTietXeTuLai = findViewById(R.id.imgHinhAnhChiTietXeTuLai);
        txtTenChiTietXeTuLai = findViewById(R.id.txtTenChiTietXeTuLai);
        txtGiaChiTietXeTuLai = findViewById(R.id.txtGiaChiTietXeTuLai);
        txtDonGiaThueXeTuLai = findViewById(R.id.txtDonGiaThueXeTuLai);
        txtThoiGianBatDau = findViewById(R.id.txtThoiGianBatDau);
        txtThoiGianKetThuc = findViewById(R.id.txtThoiGianKetThuc);
        txtTongCong = findViewById(R.id.txtTongCong);
        txtMoTaXeTuLai = findViewById(R.id.txtMoTaXeTuLai);
        btnThayDoiThoiGian = findViewById(R.id.btnThayDoiThoiGian);
        btnDatXeTuLai = findViewById(R.id.btnDatXeTuLai);
        txtTenXeTuLaiTitle = findViewById(R.id.txtTenXeTuLai_title);
        edtViTriXe = findViewById(R.id.edtViTriXe);
        edtViTriGiaoNhanXe = findViewById(R.id.edtViTriGiaoNhanXe);
        btnBanDoXeTuLai = findViewById(R.id.btnBanDo_XeTuLai);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng chờ...");
        imgHinhAnhChuXe = findViewById(R.id.imgHinhAnh_ChuXe);
        txtHoTenChuXe = findViewById(R.id.txtHoTen_ChuXe);
        txtSoDienThoaiChuXe = findViewById(R.id.txtSoDienThoai_ChuXe);
        lvDanhGia = findViewById(R.id.lvDanhGia);
        danhSachDanhGiaAdapter = new DanhGiaAdapter(ChiTietXeTuLaiActivity.this, R.layout.item_danhgia);

        //call api hien thi toan bo hang xe len spinner
        hinhThucAdapter = new ArrayAdapter<HinhThucThanhToan>(ChiTietXeTuLaiActivity.this, android.R.layout.simple_spinner_item);
        hinhThucAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHinhThucThanhToan.setAdapter(hinhThucAdapter);
        HienThiToanBoHinhThucThanhToanTask task = new HienThiToanBoHinhThucThanhToanTask();
        task.execute();
        Intent intent = getIntent();
        if(intent!=null){
            XeTuLai xeTuLai = (XeTuLai) intent.getSerializableExtra("object_xetulai");
            if(xeTuLai!=null){
                txtTenXeTuLaiTitle.setText(xeTuLai.getTenXe());
                Glide.with(this).load("http://192.168.1.50/dacn/assets/images/xe/"+xeTuLai.getHinhAnh()).into(imgHinhAnhChiTietXeTuLai);
                txtTenChiTietXeTuLai.setText(xeTuLai.getTenXe());
                Locale locale = new Locale("vi","VN");
                NumberFormat format = NumberFormat.getCurrencyInstance(locale);
                format.setRoundingMode(RoundingMode.HALF_UP);
                txtGiaChiTietXeTuLai.setText(format.format(xeTuLai.getGiaThue())+"/ngày");
                txtDonGiaThueXeTuLai.setText(format.format(xeTuLai.getGiaThue())+"/ngày");
                txtMoTaXeTuLai.setText(xeTuLai.getMoTa());
                //sử lý định dạng ngày tháng
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar cal = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                int d1 = cal2.getTime().getDate();
                int d2 = d1+1;
                cal2.set(2022,11, d2);
                txtThoiGianBatDau.setText(sdf.format(cal.getTime()));
                txtThoiGianKetThuc.setText(sdf.format(cal2.getTime()));
                edtViTriXe.setText(xeTuLai.getDiaChi());
                int tong = xeTuLai.getGiaThue()*(d2-d1);
                txtTongCong.setText(format.format(tong));
                HienThiChuXeTask hienThiChuXeTask = new HienThiChuXeTask();
                hienThiChuXeTask.execute(xeTuLai.getMaThanhVien());
                HienThiDanhGiaTheoMaXeTask hienThiDanhGiaTheoMaXeTask = new HienThiDanhGiaTheoMaXeTask();
                hienThiDanhGiaTheoMaXeTask.execute(xeTuLai.getMaXe());
                lvDanhGia.setAdapter(danhSachDanhGiaAdapter);
            }
        }
    }
}