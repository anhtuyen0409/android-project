package com.nguyenanhtuyen.appthuexe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.nguyenanhtuyen.adapter.DanhMucProfileAdapter;
import com.nguyenanhtuyen.model.DanhMucProfile;
import com.nguyenanhtuyen.model.User;


public class ProfileLoggedActivity extends AppCompatActivity {
    ListView lvDanhMuc;
    DanhMucProfileAdapter danhMucProfileAdapter;
    TextView txtTenHienThiUser, txtDiemUser;
    ImageView imgHinhAnhUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_logged);
        addControls();
        addData();
        addEvents();
    }

    private void addEvents() {
        lvDanhMuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(danhMucProfileAdapter.getItem(i).getTieuDe()=="Đăng xuất"){
                    Intent intent = getIntent();
                    if(intent!=null){
                        User u = (User) intent.getSerializableExtra("user_profile");
                        if(u!=null){
                            if(u.getMaLoaiThanhVien()==2){
                                xuLyDangXuat();
                            }
                            else if(u.getMaLoaiThanhVien()==3){
                                xuLyDangXuat();
                            }
                        }
                    }
                }
                else if(danhMucProfileAdapter.getItem(i).getTieuDe()=="Tài khoản"){
                    Intent intent = getIntent();
                    Intent intent2 = new Intent(ProfileLoggedActivity.this, ChinhSuaThongTinCaNhanActivity.class);
                    if(intent!=null){
                        User u = (User) intent.getSerializableExtra("user_profile");
                        if(u!=null){
                            intent2.putExtra("user_update",u);
                        }
                    }
                    startActivity(intent2);
                }
                else if(danhMucProfileAdapter.getItem(i).getTieuDe()=="Xe của tôi"){
                    Intent intent = getIntent();
                    if(intent!=null){
                        User u = (User) intent.getSerializableExtra("user_profile");
                        if(u!=null){
                            if(u.getMaLoaiThanhVien()==2){
                                Toast.makeText(ProfileLoggedActivity.this, "Hiện tại bạn chưa có xe cho thuê. Bạn cần đăng ký tài khoản chủ xe để có thể đăng xe cho thuê!", Toast.LENGTH_LONG).show();
                            }
                            else if(u.getMaLoaiThanhVien()==3){
                                Intent intent2 = new Intent(ProfileLoggedActivity.this, DanhSachXeActivity.class);
                                intent2.putExtra("user_danhsachxe",u);
                                startActivity(intent2);
                            }
                        }

                    }
                }
                else if(danhMucProfileAdapter.getItem(i).getTieuDe()=="Thông báo yêu cầu đặt xe mới"){
                    Intent intent = getIntent();
                    if(intent!=null){
                        User u = (User) intent.getSerializableExtra("user_profile");
                        if(u!=null){
                            if(u.getMaLoaiThanhVien()==2){
                                Toast.makeText(ProfileLoggedActivity.this, "Bạn chưa đăng ký chủ xe!", Toast.LENGTH_LONG).show();
                            }
                            else if(u.getMaLoaiThanhVien()==3){
                                Intent intent2 = new Intent(ProfileLoggedActivity.this, DatXeActivity.class);
                                intent2.putExtra("user_danhsachyeucau",u);
                                startActivity(intent2);
                            }
                        }
                    }
                }
            }
        });
    }

    private void xuLyDangXuat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileLoggedActivity.this);
        builder.setTitle("Xác nhận");
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage("Bạn có chắc chắn đăng xuất không?");
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ProfileLoggedActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void addData() {
        danhMucProfileAdapter.add(new DanhMucProfile(R.drawable.edit_user,"Tài khoản","Quản lý thông tin cá nhân"));
        danhMucProfileAdapter.add(new DanhMucProfile(R.drawable.mycar,"Xe của tôi","Quản lý xe cho thuê"));
        danhMucProfileAdapter.add(new DanhMucProfile(R.drawable.list,"Thông báo yêu cầu đặt xe mới","Quản lý thông tin danh sách các yêu cầu đặt xe"));
        danhMucProfileAdapter.add(new DanhMucProfile(R.drawable.favorite_car,"Xe yêu thích","Danh sách các xe bạn đã bấm yêu thích"));
        danhMucProfileAdapter.add(new DanhMucProfile(R.drawable.my_location,"Địa chỉ của tôi","Quản lý các địa chỉ giao xe tận nơi"));
        danhMucProfileAdapter.add(new DanhMucProfile(R.drawable.gift,"Quà tặng","Các chương trình ưu đãi hấp dẫn dành cho bạn"));
        danhMucProfileAdapter.add(new DanhMucProfile(R.drawable.logout,"Đăng xuất"));
    }

    private void addControls() {
        //controls
        txtTenHienThiUser = findViewById(R.id.txtTenHienThi_User);
        txtDiemUser = findViewById(R.id.txtDiem_User);
        imgHinhAnhUser = findViewById(R.id.imgHinhAnh_User);

        lvDanhMuc = findViewById(R.id.lvDanhMuc);
        danhMucProfileAdapter = new DanhMucProfileAdapter(ProfileLoggedActivity.this, R.layout.item_danhmuc);
        lvDanhMuc.setAdapter(danhMucProfileAdapter);

        //set up profile user
        Intent intent = getIntent();
        if(intent!=null){
            User u = (User) intent.getSerializableExtra("user_profile");
            if(u!=null){
                txtTenHienThiUser.setText(u.getTenHienThi());
                Glide.with(this).load("http://192.168.1.50/dacn/assets/images/user/"+u.getHinhAnh()).into(imgHinhAnhUser);
            }
        }
    }
}