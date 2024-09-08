package com.example.plp_app.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChuyenSanXuatResponse {
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

        @SerializedName("taskProducts")
        private List<TaskProduct> taskProducts;

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

        public List<TaskProduct> getTaskProducts() {
            return taskProducts;
        }

        public void setTaskProducts(List<TaskProduct> taskProducts) {
            this.taskProducts = taskProducts;
        }
    }

    public static class TaskProduct {
        @SerializedName("id")
        private String id;

        @SerializedName("code")
        private String code;

        @SerializedName("name")
        private String name;

        @SerializedName("target")
        private int target;

        @SerializedName("dateStart")
        private String dateStart;

        @SerializedName("dateEnd")
        private String dateEnd;

        @SerializedName("orderProduct")
        private OrderProduct orderProduct;

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

        public int getTarget() {
            return target;
        }

        public void setTarget(int target) {
            this.target = target;
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

        public OrderProduct getOrderProduct() {
            return orderProduct;
        }

        public void setOrderProduct(OrderProduct orderProduct) {
            this.orderProduct = orderProduct;
        }
    }

    public static class OrderProduct {
        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        @SerializedName("quantityProduct")
        private int quantityProduct;

        @SerializedName("quantityQA")
        private int quantityQA;

        @SerializedName("quantityGood")
        private int quantityGood;

        @SerializedName("quantityBad")
        private int quantityBad;

        @SerializedName("totalQuantity")
        private int totalQuantity;

        @SerializedName("productId")
        private String productId;

        @SerializedName("productName")
        private String productName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getQuantityProduct() {
            return quantityProduct;
        }

        public void setQuantityProduct(int quantityProduct) {
            this.quantityProduct = quantityProduct;
        }

        public int getQuantityQA() {
            return quantityQA;
        }

        public void setQuantityQA(int quantityQA) {
            this.quantityQA = quantityQA;
        }

        public int getQuantityGood() {
            return quantityGood;
        }

        public void setQuantityGood(int quantityGood) {
            this.quantityGood = quantityGood;
        }

        public int getQuantityBad() {
            return quantityBad;
        }

        public void setQuantityBad(int quantityBad) {
            this.quantityBad = quantityBad;
        }

        public int getTotalQuantity() {
            return totalQuantity;
        }

        public void setTotalQuantity(int totalQuantity) {
            this.totalQuantity = totalQuantity;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }
    }

}


