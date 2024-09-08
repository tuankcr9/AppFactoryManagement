package com.example.plp_app.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DefectCodesResponse {
    @SerializedName("data")
    private Data data;
    @SerializedName("additionalData")
    private Object additionalData;
    @SerializedName("message")
    private String message;
    @SerializedName("statuscode")
    private int statusCode;
    @SerializedName("code")
    private String code;

    // Getters and Setters
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
        @SerializedName("items")
        private List<Item> items;
        @SerializedName("pageNumber")
        private int pageNumber;

        @SerializedName("totalPages")
        private int totalPages;

        @SerializedName("totalCount")
        private int totalCount;

        @SerializedName("pageSize")
        private int pageSize;

        @SerializedName("hasPreviousPage")
        private boolean hasPreviousPage;

        @SerializedName("hasNextPage")
        private boolean hasNextPage;

        // Getters and Setters
        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }
    }

    public static class Item {
        @SerializedName("id")
        private String id;
        @SerializedName("code")
        private String code;
        @SerializedName("name")
        private String name;
        @SerializedName("createdBy")
        private String createdBy;
        @SerializedName("description")
        private String description;
        @SerializedName("backGroundColor")
        private String backGroundColor;
        @SerializedName("textColor")
        private boolean textColor;

        // Getters and Setters
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

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBackGroundColor() {
            return backGroundColor;
        }

        public void setBackGroundColor(String backGroundColor) {
            this.backGroundColor = backGroundColor;
        }

        public boolean getTextColor() {
            return textColor;
        }

        public void setTextColor(boolean textColor) {
            this.textColor = textColor;
        }
    }
}

