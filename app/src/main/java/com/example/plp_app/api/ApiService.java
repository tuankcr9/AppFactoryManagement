package com.example.plp_app.api;
import com.example.plp_app.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/api/auth/login")
    Call<LoginResponse> login(@Body User user);

    @GET("/api/modules/mobile")
    Call<ChuyenSanXuatResponse> getListChuyenSanXuat();

    @PUT("/api/taskproduct/{id}/tangsanluong")
    Call<TangSanLuongResponse> tangSanLuong(@Path("id") String taskProductId, @Query("quantity") int quantity);

    @GET("/api/defectcodes")
    Call<DefectCodesResponse> getDefectcode(@Query("pageSize") int pageSize);

    @POST("/api/qaproducts/{orderProduct}/createqaproduct")
    Call<QAProductResponse> createQAProduct(
        @Path("orderProduct") String orderProduct,
        @Query("DefectCode") List<String> defectcodes,
        @Query("quantity") int quantity
    );

    @GET("/api/dashboards/quantity-module-chart")
    Call<DashboardResponse> getDashBoard(@Query("date") String date);
    Call<DashboardResponse> getDashboardData(
            @Query("moduleIds") List<String> moduleIds,
            @Query("categoryId") String categoryId,
            @Query("date") String date
    );
}
