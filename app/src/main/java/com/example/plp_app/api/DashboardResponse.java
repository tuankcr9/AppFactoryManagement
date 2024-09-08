package com.example.plp_app.api;

import java.util.List;

public class DashboardResponse {
    private List<ModuleData> data;

    public List<ModuleData> getData() {
        return data;
    }

    public void setData(List<ModuleData> data) {
        this.data = data;
    }
}