package com.conciliacion.parquet.dto;

import java.util.List;

public class ParametrosSQLDto {

    private String query;
    private List<String> key;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getKey() {
        return key;
    }

    public void setKey(List<String> key) {
        this.key = key;
    }
}
