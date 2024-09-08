package com.example.plp_app.api;

import java.util.List;

public class ModuleData {
    private String moduleName;
    private List<ChartCol> chartCols;
    private List<TaskProductDetail> taskProductsDetail;
    private int currentQuantityByHour;
    private int totalQuantityByHour;
    private int currentQuantityByDay;
    private int totalQuantityByDay;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<ChartCol> getChartCols() {
        return chartCols;
    }

    public void setChartCols(List<ChartCol> chartCols) {
        this.chartCols = chartCols;
    }

    public List<TaskProductDetail> getTaskProductsDetail() {
        return taskProductsDetail;
    }

    public void setTaskProductsDetail(List<TaskProductDetail> taskProductsDetail) {
        this.taskProductsDetail = taskProductsDetail;
    }

    public int getCurrentQuantityByHour() {
        return currentQuantityByHour;
    }

    public void setCurrentQuantityByHour(int currentQuantityByHour) {
        this.currentQuantityByHour = currentQuantityByHour;
    }

    public int getTotalQuantityByHour() {
        return totalQuantityByHour;
    }

    public void setTotalQuantityByHour(int totalQuantityByHour) {
        this.totalQuantityByHour = totalQuantityByHour;
    }

    public int getCurrentQuantityByDay() {
        return currentQuantityByDay;
    }

    public void setCurrentQuantityByDay(int currentQuantityByDay) {
        this.currentQuantityByDay = currentQuantityByDay;
    }

    public int getTotalQuantityByDay() {
        return totalQuantityByDay;
    }

    public void setTotalQuantityByDay(int totalQuantityByDay) {
        this.totalQuantityByDay = totalQuantityByDay;
    }

    // Add similar getters and setters for other fields
}