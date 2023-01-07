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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.nguyenanhtuyen.api.ApiClient;
import com.nguyenanhtuyen.api.UploadService;
import com.nguyenanhtuyen.model.XeTuLai;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChinhSuaThongTinXeActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    private static final String TAG = ChinhSuaThongTinXeTask.class.getName();
    ImageView imgHinhAnhChiTietXe;
    Button btnChinhSuaThongTinXe, btnThayDoiHinhAnh;
    EditText edtTenXe, edtGiaXe, edtDiaDiem;
    CheckBox chkXacNhan;
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
                            imgHinhAnhChiTietXe.setImageBitmap(bitmap);
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
        setContentView(R.layout.activity_chinh_sua_thong_tin_xe);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnChinhSuaThongTinXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyChinhSuaThongTinXe();
            }
        });

        btnThayDoiHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyLayHinh();
            }
        });

        chkXacNhan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    load();
                }
            }
        });
    }

    private void xuLyChinhSuaThongTinXe() {
        progressDialog.show();
        String strRealPath = RealPathUtil.getRealPath(this, mUri);
        File file = new File(strRealPath);
        Intent intent = getIntent();
        if(intent!=null){
            XeTuLai xeTuLai = (XeTuLai) intent.getSerializableExtra("object_chinhsuaxe");
            if(xeTuLai!=null){
                ChinhSuaThongTinXeTask task = new ChinhSuaThongTinXeTask();
                task.execute(xeTuLai.getMaXe()+"", edtTenXe.getText().toString(), edtGiaXe.getText().toString(),
                        edtDiaDiem.getText().toString(),file.getName());
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
        uploadService.UploadSua(photo).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(ChinhSuaThongTinXeActivity.this, "Upload thành công!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ChinhSuaThongTinXeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    class ChinhSuaThongTinXeTask extends AsyncTask<String, Void, Boolean>{
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true){
                Toast.makeText(ChinhSuaThongTinXeActivity.this, "Cập nhật thông tin xe thành công!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(ChinhSuaThongTinXeActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/xe/chinhsuathongtinxe?maXe="+strings[0]+"&tenXe="+strings[1]+"&gia="+strings[2]+"&diaChi="+strings[3]+"&hinhAnh="+strings[4]);
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
        imgHinhAnhChiTietXe = findViewById(R.id.imgHinhAnhChiTietXe);
        btnChinhSuaThongTinXe = findViewById(R.id.btnChinhSuaThongTinXe);
        edtTenXe = findViewById(R.id.edtTenXe);
        edtGiaXe = findViewById(R.id.edtGiaXe);
        edtDiaDiem = findViewById(R.id.edtDiaDiem);
        btnThayDoiHinhAnh = findViewById(R.id.btnThayDoiHinhAnh);
        chkXacNhan = findViewById(R.id.chkxacnhan);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng chờ...");

        Intent intent = getIntent();
        if(intent!=null){
            XeTuLai xeTuLai = (XeTuLai) intent.getSerializableExtra("object_chinhsuaxe");
            if(xeTuLai!=null){
                Glide.with(this).load("http://192.168.1.50/dacn/assets/images/xe/"+xeTuLai.getHinhAnh()).into(imgHinhAnhChiTietXe);
                edtTenXe.setText(xeTuLai.getTenXe());
                edtGiaXe.setText(xeTuLai.getGiaThue()+"");
                edtDiaDiem.setText(xeTuLai.getDiaChi());
            }
        }
    }
}