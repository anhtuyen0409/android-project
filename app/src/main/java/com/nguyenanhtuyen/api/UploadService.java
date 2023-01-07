package com.nguyenanhtuyen.api;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadService {
    @Multipart
    @POST("user/upload")
    Call<ResponseBody> Upload(@Part MultipartBody.Part photo);

    @Multipart
    @POST("xe/UploadSua")
    Call<ResponseBody> UploadSua(@Part MultipartBody.Part photo);

    @Multipart
    @POST("xe/UploadThem")
    Call<ResponseBody> UploadThem(@Part MultipartBody.Part photo);
}
