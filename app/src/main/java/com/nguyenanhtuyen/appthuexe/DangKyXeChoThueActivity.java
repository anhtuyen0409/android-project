package com.nguyenanhtuyen.appthuexe;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.nguyenanhtuyen.api.ApiClient;
import com.nguyenanhtuyen.api.UploadService;
import com.nguyenanhtuyen.model.HangXe;
import com.nguyenanhtuyen.model.LoaiXe;
import com.nguyenanhtuyen.model.User;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKyXeChoThueActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    private static final String TAG = DangKyXeChoThueActivity.class.getName();
    Spinner spinnerHangXe, spinnerLoaiXe;
    ArrayAdapter<HangXe> hangXeAdapter;
    ArrayAdapter<LoaiXe> loaiXeAdapter;
    ImageView imgThem, imgHinhAnhXeThem;
    Button btnUploadThem, btnDangKyXeThem;
    EditText edtTenXeThem, edtGiaThem, edtDiaChiThem, edtMoTaThem;
    CheckBox chkDongYThem;
    private ProgressDialog progressDialog;
    private Uri mUri;

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data == null){
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;

                        try{
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgHinhAnhXeThem.setImageBitmap(bitmap);
                        }
                        catch (IOException ex){
                            ex.printStackTrace();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_xe_cho_thue);
        addControls();
        addEvents();
    }

    private void addEvents() {
        imgThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyLayHinh();
            }
        });
        btnUploadThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
            }
        });
        btnDangKyXeThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThemXeTuLai();
            }
        });
    }

    private void xuLyThemXeTuLai() {
        progressDialog.show();
        String strRealPath = RealPathUtil.getRealPath(this, mUri);
        File file = new File(strRealPath);
        Intent intent = getIntent();
        if(intent!=null){
            User u = (User) intent.getSerializableExtra("user_themxetulai");
            if(u!=null){
                ThemXeTuLaiTask task = new ThemXeTuLaiTask();
                HangXe hx = (HangXe) spinnerHangXe.getSelectedItem();
                LoaiXe loaiXe = (LoaiXe) spinnerLoaiXe.getSelectedItem();
                task.execute(u.getMaThanhVien()+"", edtTenXeThem.getText().toString(), hx.getMaHangXe()+"", loaiXe.getMaLoaiXe()+"",
                        edtGiaThem.getText().toString(), edtDiaChiThem.getText().toString(), edtMoTaThem.getText().toString(),
                        file.getName());
                progressDialog.dismiss();
            }
        }
    }

    private void load() {
        progressDialog.show();
        String strRealPath = RealPathUtil.getRealPath(this, mUri);
        File file = new File(strRealPath);
        RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo",file.getName(),photoContent);
        UploadService uploadService = ApiClient.getClient().create(UploadService.class);
        uploadService.UploadThem(photo).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(DangKyXeChoThueActivity.this, "Upload thành công!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DangKyXeChoThueActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void xuLyLayHinh() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            showHinhAnh();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            showHinhAnh();
        }
        else{
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showHinhAnh();
            }
        }
    }

    private void showHinhAnh() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
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

    class ThemXeTuLaiTask extends AsyncTask<String,Void,Boolean>{
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true){
                Toast.makeText(DangKyXeChoThueActivity.this,"Đăng xe thành công!",Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(DangKyXeChoThueActivity.this,"Đăng xe thất bại!",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/xe/themxetulai?maThanhVien="+strings[0]+"&tenXe="+strings[1]+
                        "&maHangXe="+strings[2]+"&maLoaiXe="+strings[3]+"&gia="+strings[4]+"&diaChi="+strings[5]+"&moTa="+strings[6]
                        +"&hinhAnh="+strings[7]);
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
        spinnerHangXe = findViewById(R.id.spinnerHangXe);
        spinnerLoaiXe = findViewById(R.id.spinnerLoaiXe);
        imgThem = findViewById(R.id.imgThem);
        imgHinhAnhXeThem = findViewById(R.id.imgHinhAnhXe_Them);
        btnUploadThem = findViewById(R.id.btnupload_them);
        btnDangKyXeThem = findViewById(R.id.btnDangKyXe_Them);
        edtTenXeThem = findViewById(R.id.edtTenXe_Them);
        edtDiaChiThem = findViewById(R.id.edtDiaChi_Them);
        edtGiaThem = findViewById(R.id.edtGia_Them);
        edtMoTaThem = findViewById(R.id.edtMoTa_Them);
        chkDongYThem = findViewById(R.id.chkDongY_ThemXe);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng chờ...");

        //call api hien thi toan bo hang xe len spinner
        hangXeAdapter = new ArrayAdapter<HangXe>(DangKyXeChoThueActivity.this, android.R.layout.simple_spinner_item);
        hangXeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHangXe.setAdapter(hangXeAdapter);
        HienThiToanBoHangXeTask task = new HienThiToanBoHangXeTask();
        task.execute();

        //call api hien thi toan bo loai xe len spinner
        loaiXeAdapter = new ArrayAdapter<LoaiXe>(DangKyXeChoThueActivity.this, android.R.layout.simple_spinner_item);
        loaiXeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoaiXe.setAdapter(loaiXeAdapter);
        HienThiToanBoLoaiXeTask hienThiToanBoLoaiXeTask = new HienThiToanBoLoaiXeTask();
        hienThiToanBoLoaiXeTask.execute();
    }
}