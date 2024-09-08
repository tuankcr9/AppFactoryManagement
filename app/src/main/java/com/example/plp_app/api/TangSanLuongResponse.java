package com.example.plp_app.api;

import com.google.gson.annotations.SerializedName;

public class TangSanLuongResponse{
    @SerializedName("data")
    private Data data;
    @SerializedName("additionalData")
    private Object additionalData;
    @SerializedName("message")
    private String message;
    @SerializedName("statusCode")
    private int statusCode;
    @SerializedName("code")
    private String code;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Object getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Object additionalData) {
        this.additionalData = additionalData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public static class Data {
        @SerializedName("id")
        private String id;
        @SerializedName("code")
        private String code;
        @SerializedName("name")
        private String name;
        @SerializedName("description")
        private String description;
        @SerializedName("quantity")
        private int quantity;
        @SerializedName("dateStart")
        private String dateStart;
        @SerializedName("dateEnd")
        private String dateEnd;
        @SerializedName("totalQuantityProduct")
        private int totalQuantityProduct;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getDateStart() {
            return dateStart;
        }

        public void setDateStart(String dateStart) {
            this.dateStart = dateStart;
        }

        public String getDateEnd() {
            return dateEnd;
        }

        public void setDateEnd(String dateEnd) {
            this.dateEnd = dateEnd;
        }

        public int getTotalQuantityProduct() {
            return totalQuantityProduct;
        }

        public void setTotalQuantityProduct(int totalQuantityProduct) {
            this.totalQuantityProduct = totalQuantityProduct;
        }
    }
}
