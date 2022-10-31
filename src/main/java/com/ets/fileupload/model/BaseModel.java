package com.ets.fileupload.model;

import org.springframework.beans.BeanUtils;
public abstract class BaseModel<S, U> {
    private String[] ignoreProperties;

    protected void setIgnoreProperties(String[] ignoreProperties) {
        this.ignoreProperties = ignoreProperties;
    }

    public U convertToBase(S source, U destination) {
        try {
            BeanUtils.copyProperties(source, destination, ignoreProperties);
        } catch (Exception e) {
        }
        return destination;
    }

    public S convertTo(U source, S destination) {
        try {
            BeanUtils.copyProperties(source, destination, ignoreProperties);
        } catch (Exception e) {
        }
        return destination;
    }
}
