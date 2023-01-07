package com.nguyenanhtuyen.appthuexe;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.nguyenanhtuyen.adapter.HangXeAdapter;
import com.nguyenanhtuyen.adapter.LoaiXeAdapter;
import com.nguyenanhtuyen.adapter.XeTuLaiAdapter;
import com.nguyenanhtuyen.model.HangXe;
import com.nguyenanhtuyen.model.LoaiXe;
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
import java.util.ArrayList;
import java.util.Locale;


public class ThueXeTuLaiActivity extends AppCompatActivity {
    private ListView lvXeTuLai;
    private XeTuLaiAdapter xeTuLaiAdapter, timXeTuLaiTheoTenAdapter, locXeTuLaiTheoLoaiXeAdapter, locXeTuLaiTheoHangXeAdapter, locXeTuLaiTheoGiaAdapter;
    private LoaiXeAdapter loaiXeAdapter;
    private HangXeAdapter hangXeAdapter;
    Button  btnBoLocTuLai;
    TextView txtHienThiTatCaTuLai;
    ImageView imgSearchXeTuLai;
    ArrayList<XeTuLai> dsXe;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thue_xe_tu_lai);
        addControls();
        addEvents();
    }

    private void addEvents() {
        lvXeTuLai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /* call api lấy chi tiết xe tự lái theo id
                HienThiChiTietXeTuLaiTask task = new HienThiChiTietXeTuLaiTask();
                task.execute(xeTuLaiAdapter.getItem(i).getMaXe());
                */

                //call api lấy chi tiết xe tự lái theo tên
                if(flag == 1){
                    LayChiTietXeTuLaiTheoTenTask layChiTietXeTuLaiTheoTenTask = new LayChiTietXeTuLaiTheoTenTask();
                    layChiTietXeTuLaiTheoTenTask.execute(timXeTuLaiTheoTenAdapter.getItem(i).getTenXe());
                    return;
                }
                else if(flag == 2){
                    LayChiTietXeTuLaiTheoTenTask layChiTietXeTuLaiTheoTenTask = new LayChiTietXeTuLaiTheoTenTask();
                   layChiTietXeTuLaiTheoTenTask.execute(locXeTuLaiTheoLoaiXeAdapter.getItem(i).getTenXe());
                    return;
                }
                else if(flag == 3){
                   LayChiTietXeTuLaiTheoTenTask layChiTietXeTuLaiTheoTenTask = new LayChiTietXeTuLaiTheoTenTask();
                   layChiTietXeTuLaiTheoTenTask.execute(locXeTuLaiTheoHangXeAdapter.getItem(i).getTenXe());
                    return;
                }
                else if(flag == 4){
                    LayChiTietXeTuLaiTheoTenTask layChiTietXeTuLaiTheoTenTask = new LayChiTietXeTuLaiTheoTenTask();
                   layChiTietXeTuLaiTheoTenTask.execute(locXeTuLaiTheoGiaAdapter.getItem(i).getTenXe());
                    return;
                }
                else{
                    HienThiChiTietXeTuLaiTask hienThiChiTietXeTuLaiTask = new HienThiChiTietXeTuLaiTask();
                    hienThiChiTietXeTuLaiTask.execute(xeTuLaiAdapter.getItem(i).getMaXe());
                }

            }
        });

        txtHienThiTatCaTuLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyHienThiTatCaXeTuLai();
            }
        });
        btnBoLocTuLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hienThiBoLoc(Gravity.BOTTOM);
            }
        });
        imgSearchXeTuLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hienThiTimKiemXeTuLaiTheoTen(Gravity.TOP);
            }
        });

    }

    private void hienThiTimKiemXeTuLaiTheoTen(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_timkiemtulaitheoten);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.TOP == gravity){
            dialog.setCancelable(true);
        }
        else{
            dialog.setCancelable(false);
        }

        //khai báo các control
        EditText edtNhapTenXeTuLaiCanTim = dialog.findViewById(R.id.edtNhapTenXeTuLaiCanTim);
        Button btnTimKiemXeTuLai = dialog.findViewById(R.id.btnTimKiemXeTuLai);

        btnTimKiemXeTuLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimKiemXeTuLaiTheoTenTask task = new TimKiemXeTuLaiTheoTenTask();
                task.execute(edtNhapTenXeTuLaiCanTim.getText().toString());

                timXeTuLaiTheoTenAdapter = new XeTuLaiAdapter(ThueXeTuLaiActivity.this, R.layout.item_xetulai);
                lvXeTuLai.setAdapter(timXeTuLaiTheoTenAdapter);

                flag = 1;
            }
        });
        dialog.show();
    }

    private void hienThiBoLoc(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_filter);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }
        else{
            dialog.setCancelable(false);
        }

        //khai báo các control
        ListView lvFilter = dialog.findViewById(R.id.lvFilter);
        SeekBar skGia = dialog.findViewById(R.id.seekBarGia);
        TextView txtGia = dialog.findViewById(R.id.txtLocTheoGia);

        //add data lvFilter
        String[] arrData = {"Loại xe","Hãng xe","Sắp xếp"};
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrData);
        lvFilter.setAdapter(adapter);

        //xử lý sự kiện click lvFilter
        lvFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(arrData[i].contains("Loại xe")){
                    hienThiDanhSachLoaiXe(Gravity.BOTTOM);
                    dialog.dismiss();
                }
                else if(arrData[i].contains("Hãng xe")){
                    hienThiToanBoHangXe(Gravity.BOTTOM);
                    dialog.dismiss();
                }
            }
        });

        //định dạng tiền
        Locale locale = new Locale("vi","VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        format.setRoundingMode(RoundingMode.HALF_DOWN);

        //xử lý sự kiện change seekbar
        skGia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int a = i/1000;
                txtGia.setText("Bất kỳ - "+a+"K");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //xử lý lọc xe tự lái theo giá call api
                LocXeTuLaiTheoGiaTask locXeTuLaiTheoGiaTask = new LocXeTuLaiTheoGiaTask();
               locXeTuLaiTheoGiaTask.execute(skGia.getProgress());
                locXeTuLaiTheoGiaAdapter = new XeTuLaiAdapter(ThueXeTuLaiActivity.this, R.layout.item_xetulai);
                lvXeTuLai.setAdapter(locXeTuLaiTheoGiaAdapter);
                flag = 4;
                if(skGia.getProgress()==3000000){
                    txtGia.setText("Bất kỳ");
                }
            }
        });
        dialog.show();
    }

    private void hienThiToanBoHangXe(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_hangxe);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }
        else{
            dialog.setCancelable(false);
        }

        //khai báo các control
        ListView lvHangXe = dialog.findViewById(R.id.lvHangXe);

        //call api hiển thị toàn bộ hãng xe
        HienThiToanBoHangXeTask hienThiToanBoHangXeTask = new HienThiToanBoHangXeTask();
       hienThiToanBoHangXeTask.execute();
        hangXeAdapter = new HangXeAdapter(this, R.layout.item_hangxe);
        lvHangXe.setAdapter(hangXeAdapter);

        //xử lý sự kiện click item lvLoaiXe
        lvHangXe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //xử lý lọc xe tự lái theo hãng xe
               LocXeTuLaiTheoHangXeTask task = new LocXeTuLaiTheoHangXeTask();
               task.execute(hangXeAdapter.getItem(i).getMaHangXe());
                locXeTuLaiTheoHangXeAdapter = new XeTuLaiAdapter(ThueXeTuLaiActivity.this, R.layout.item_xetulai);
                lvXeTuLai.setAdapter(locXeTuLaiTheoHangXeAdapter);
                flag = 3;
            }
        });

        dialog.show();
    }

    private void hienThiDanhSachLoaiXe(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_loaixe);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }
        else{
            dialog.setCancelable(false);
        }

        //khai báo các control
        ListView lvLoaiXe = dialog.findViewById(R.id.lvLoaiXe);
        //call api hiển thị toàn bộ loại xe
        HienThiToanBoLoaiXeTask hienThiToanBoLoaiXeTask = new HienThiToanBoLoaiXeTask();
        hienThiToanBoLoaiXeTask.execute();
        loaiXeAdapter = new LoaiXeAdapter(this, R.layout.item_loaixe);
        lvLoaiXe.setAdapter(loaiXeAdapter);

        //xử lý sự kiện click item lvLoaiXe
        lvLoaiXe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //xử lý lọc xe tự lái theo loại xe
               LocXeTuLaiTheoLoaiXeTask task = new LocXeTuLaiTheoLoaiXeTask();
               task.execute(loaiXeAdapter.getItem(i).getMaLoaiXe());
                locXeTuLaiTheoLoaiXeAdapter = new XeTuLaiAdapter(ThueXeTuLaiActivity.this, R.layout.item_xetulai);
                lvXeTuLai.setAdapter(locXeTuLaiTheoLoaiXeAdapter);
                flag = 2;
            }
        });
        dialog.show();
    }

    private void xuLyHienThiTatCaXeTuLai() {
        HienThiToanBoXeTuLaiTask hienThiToanBoXeTuLaiTask = new HienThiToanBoXeTuLaiTask();
        hienThiToanBoXeTuLaiTask.execute();
        xeTuLaiAdapter = new XeTuLaiAdapter(ThueXeTuLaiActivity.this, R.layout.item_xetulai);
        lvXeTuLai.setAdapter(xeTuLaiAdapter);
        flag = 0;
    }

    class LocXeTuLaiTheoGiaTask extends AsyncTask<Integer, Void, ArrayList<XeTuLai>>{
        @Override
        protected void onPostExecute(ArrayList<XeTuLai> xeTuLais) {
            super.onPostExecute(xeTuLais);
            locXeTuLaiTheoGiaAdapter.clear();
            locXeTuLaiTheoGiaAdapter.addAll(xeTuLais);
        }
        @Override
        protected ArrayList<XeTuLai> doInBackground(Integer... integers) {
            ArrayList<XeTuLai> dsXe = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/xe/locxetulaitheogia?gia="+integers[0]);
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

    class LocXeTuLaiTheoHangXeTask extends AsyncTask<Integer, Void, ArrayList<XeTuLai>>{
        @Override
        protected void onPostExecute(ArrayList<XeTuLai> xeTuLais) {
            super.onPostExecute(xeTuLais);
            locXeTuLaiTheoHangXeAdapter.clear();
            locXeTuLaiTheoHangXeAdapter.addAll(xeTuLais);
        }
        @Override
        protected ArrayList<XeTuLai> doInBackground(Integer... integers) {
            ArrayList<XeTuLai> dsXe = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/xe/locxetulaitheohangxe?maHangXe="+integers[0]);
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

    class LocXeTuLaiTheoLoaiXeTask extends AsyncTask<Integer, Void, ArrayList<XeTuLai>>{
        @Override
        protected void onPostExecute(ArrayList<XeTuLai> xeTuLais) {
            super.onPostExecute(xeTuLais);
            locXeTuLaiTheoLoaiXeAdapter.clear();
            locXeTuLaiTheoLoaiXeAdapter.addAll(xeTuLais);
        }
        @Override
        protected ArrayList<XeTuLai> doInBackground(Integer... integers) {
            ArrayList<XeTuLai> dsXe = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/xe/locxetulaitheoloaixe?maLoai="+integers[0]);
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

    class TimKiemXeTuLaiTheoTenTask extends AsyncTask<String, Void, ArrayList<XeTuLai>>{
        @Override
        protected void onPostExecute(ArrayList<XeTuLai> xeTuLais) {
            super.onPostExecute(xeTuLais);
            timXeTuLaiTheoTenAdapter.clear();
            timXeTuLaiTheoTenAdapter.addAll(xeTuLais);
        }
        @Override
        protected ArrayList<XeTuLai> doInBackground(String... strings) {
            ArrayList<XeTuLai> dsXe = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/xe/timkiemxetulaitheoten?ten="+strings[0]);
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

    /* call api lấy chi tiết xe tự lái theo id*/
    class HienThiChiTietXeTuLaiTask extends AsyncTask<Integer, Void, XeTuLai>{
        @Override
        protected void onPostExecute(XeTuLai xeTuLai) {
            super.onPostExecute(xeTuLai);
            Intent intent = new Intent(ThueXeTuLaiActivity.this, ChiTietXeTuLaiActivity.class);
            intent.putExtra("object_xetulai",xeTuLai);

            //truyền user đăng nhập sang chi tiết xe tự lái
            Intent intent2 = getIntent();
            if(intent2!=null){
                User u = (User) intent2.getSerializableExtra("user_thuexetulai");
                if(u!=null){
                    intent.putExtra("user_chitietxetulai",u);
                }
            }
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

    class HienThiToanBoXeTuLaiTask extends AsyncTask<Void, Void, ArrayList<XeTuLai>>{
        @Override
        protected void onPostExecute(ArrayList<XeTuLai> xeTuLais) {
            super.onPostExecute(xeTuLais);
            xeTuLaiAdapter.clear();
            xeTuLaiAdapter.addAll(xeTuLais);
            //listxetulai = (ArrayList<XeTuLai>) xeTuLais.clone();
        }

        @Override
        protected ArrayList<XeTuLai> doInBackground(Void... voids) {
            dsXe = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/xe/hienthitoanboxetulai");
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

    class HienThiToanBoLoaiXeTask extends AsyncTask<Void, Void, ArrayList<LoaiXe>>{
        @Override
        protected void onPostExecute(ArrayList<LoaiXe> loaiXes) {
            super.onPostExecute(loaiXes);
            loaiXeAdapter.clear();
            loaiXeAdapter.addAll(loaiXes);
        }
        @Override
        protected ArrayList<LoaiXe> doInBackground(Void... voids) {
            ArrayList<LoaiXe> dsLoaiXe = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/loaixe/hienthitoanboloaixe");
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

                    LoaiXe loaiXe = new LoaiXe();
                    loaiXe.setTenLoaiXe(jsonArray.optJSONObject(i).getString("TenLoaiXe"));
                    loaiXe.setMaLoaiXe(jsonArray.optJSONObject(i).getInt("MaLoaiXe"));
                    dsLoaiXe.add(loaiXe);
                }
                br.close();
            }
            catch (Exception ex){
                Log.e("Lỗi",ex.toString());
            }
            return dsLoaiXe;
        }
    }

    class HienThiToanBoHangXeTask extends AsyncTask<Void, Void, ArrayList<HangXe>>{
        @Override
        protected void onPostExecute(ArrayList<HangXe> hangXes) {
            super.onPostExecute(hangXes);
            hangXeAdapter.clear();
            hangXeAdapter.addAll(hangXes);
        }
        @Override
        protected ArrayList<HangXe> doInBackground(Void... voids) {
            ArrayList<HangXe> dsHangXe = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/hangxe/hienthitoanbohangxe");
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

                    HangXe hangXe = new HangXe();
                    hangXe.setTenHangXe(jsonArray.optJSONObject(i).getString("TenNhaSanXuat"));
                    hangXe.setMaHangXe(jsonArray.optJSONObject(i).getInt("MaNhaSanXuat"));
                    dsHangXe.add(hangXe);
                }
                br.close();
            }
            catch (Exception ex){
                Log.e("Lỗi",ex.toString());
            }
            return dsHangXe;
        }
    }

    class LayChiTietXeTuLaiTheoTenTask extends AsyncTask<String, Void, XeTuLai>{
        @Override
        protected void onPostExecute(XeTuLai xeTuLai) {
            super.onPostExecute(xeTuLai);
            Intent intent = new Intent(ThueXeTuLaiActivity.this, ChiTietXeTuLaiActivity.class);
            intent.putExtra("object_xetulai",xeTuLai);
            startActivity(intent);
        }
        @Override
        protected XeTuLai doInBackground(String... strings) {
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/xe/laychitietxetulaitheoten?ten="+strings[0]);
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
                xeTuLai.setTenXe(jsonObject.getString("TenXe"));
                xeTuLai.setGiaThue(jsonObject.getInt("Gia"));
                xeTuLai.setHinhAnh(jsonObject.getString("HinhAnh"));
                xeTuLai.setMaXe(jsonObject.getInt("MaXe"));
                xeTuLai.setStatus(jsonObject.getInt("Status"));
                return xeTuLai;
            }
            catch (Exception ex){
                Log.e("Lỗi",ex.toString());
            }
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void onClickItemXeTuLai(XeTuLai xeTuLai){
        Intent intent = new Intent(ThueXeTuLaiActivity.this, ChiTietXeTuLaiActivity.class);
        intent.putExtra("item_xetulai",xeTuLai);
        startActivity(intent);
    }

    private void addControls() {
        btnBoLocTuLai = findViewById(R.id.btnBoLoc_TuLai);
        txtHienThiTatCaTuLai = findViewById(R.id.txtHienThiTatCa_TuLai);
        imgSearchXeTuLai = findViewById(R.id.imgSearchXeTuLai);

        //hiển thị toàn bộ xe tự lái call api
        HienThiToanBoXeTuLaiTask hienThiToanBoXeTuLaiTask = new HienThiToanBoXeTuLaiTask();
        hienThiToanBoXeTuLaiTask.execute();
        lvXeTuLai = findViewById(R.id.lvToanBoXeTuLai);
        xeTuLaiAdapter = new XeTuLaiAdapter(ThueXeTuLaiActivity.this, R.layout.item_xetulai);
        lvXeTuLai.setAdapter(xeTuLaiAdapter);
    }
}