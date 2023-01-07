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
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.nguyenanhtuyen.api.ApiClient;
import com.nguyenanhtuyen.api.UploadService;
import com.nguyenanhtuyen.model.User;
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

public class ChinhSuaThongTinCaNhanActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    private static final String TAG = ChinhSuaThongTinCaNhanActivity.class.getName();
    TextView txtCapNhatAnhDaiDien;
    EditText edtHoTenUpdate, edtNgaySinhUpdate, edtSDTUpdate, edtTenHienThiUpdate;
    Button btnCapNhatThongTin;
    ImageView imgHinhAnhUpdate;
    CheckBox chkChonAnh;
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
                            imgHinhAnhUpdate.setImageBitmap(bitmap);
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
        setContentView(R.layout.activity_chinh_sua_thong_tin_ca_nhan);
        addControls();
        addEvents();
    }

    private void addEvents() {
        txtCapNhatAnhDaiDien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyLayHinh();
            }
        });
        btnCapNhatThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyCapNhatThongTin();
            }
        });
        chkChonAnh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    load();
                }
            }
        });
    }

    private void load() {
        progressDialog.show();
        String strRealPath = RealPathUtil.getRealPath(this, mUri);
        File file = new File(strRealPath);
        RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo",file.getName(),photoContent);
        UploadService uploadService = ApiClient.getClient().create(UploadService.class);
        uploadService.Upload(photo).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(ChinhSuaThongTinCaNhanActivity.this, "Upload thành công!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ChinhSuaThongTinCaNhanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void xuLyCapNhatThongTin() {
        progressDialog.show();
        String strRealPath = RealPathUtil.getRealPath(this, mUri);
        File file = new File(strRealPath);
        //set up profile user
        Intent intent = getIntent();
        if(intent!=null){
            User u = (User) intent.getSerializableExtra("user_update");
            if(u!=null){
                CapNhatThongTinCaNhanTask task = new CapNhatThongTinCaNhanTask();
                    task.execute(u.getMaThanhVien()+"",edtHoTenUpdate.getText().toString(),
                            edtSDTUpdate.getText().toString(), edtNgaySinhUpdate.getText().toString(),
                            edtTenHienThiUpdate.getText().toString(), file.getName());
            }
        }
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

    class CapNhatThongTinCaNhanTask extends AsyncTask<String, Void, Boolean>{
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true){
                Intent intent = new Intent(ChinhSuaThongTinCaNhanActivity.this,MainActivity.class);
                startActivity(intent);
                progressDialog.dismiss();
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                URL url = new URL("http://192.168.1.50/dacn/api/user/suathongtincanhan?maThanhVien="+strings[0]+"&hoTen="+strings[1]+"&sdt="+strings[2]+"&ngaySinh="+strings[3]+"&tenHienThi="+strings[4]+"&hinhAnh="+strings[5]);
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
        //khai báo các controls
        txtCapNhatAnhDaiDien = findViewById(R.id.txtCapNhatAnhDaiDien);
        edtHoTenUpdate = findViewById(R.id.edtHoTen_Update);
        edtNgaySinhUpdate = findViewById(R.id.edtNgaySinh_Update);
        edtSDTUpdate = findViewById(R.id.edtSDT_Update);
        edtTenHienThiUpdate = findViewById(R.id.edtTenHienThi_Update);
        btnCapNhatThongTin = findViewById(R.id.btnCapNhatThongTin);
        imgHinhAnhUpdate = findViewById(R.id.imgHinhAnh_Update);
        chkChonAnh = findViewById(R.id.chkchonanh);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng chờ...");

        //set up profile user
        Intent intent = getIntent();
        if(intent!=null){
            User u = (User) intent.getSerializableExtra("user_update");
            if(u!=null){
                Glide.with(this).load("http://192.168.1.50/dacn/assets/images/user/"+u.getHinhAnh()).into(imgHinhAnhUpdate);
                edtHoTenUpdate.setText(u.getHoTen());
                edtNgaySinhUpdate.setText(u.getNgaySinh());
                edtSDTUpdate.setText(u.getSdt());
                edtTenHienThiUpdate.setText(u.getTenHienThi());
            }
        }
    }
}