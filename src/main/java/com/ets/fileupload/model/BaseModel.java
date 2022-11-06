package com.ets.fileupload.model;

import org.springframework.beans.BeanUtils;
public abstract class BaseModel<S, U> {

    public U convertToBase(S source, U destination) {
        try {
            BeanUtils.copyProperties(source, destination);
        } catch (Exception e) {
            throw new RuntimeException(source.getClass().getTypeName()+" can't cast to"+ destination.getClass().getTypeName());
        }
        return destination;
    }

    public S convertTo(U source, S destination) {
        try {
            BeanUtils.copyProperties(source, destination);
        } catch (Exception e) {
            throw new RuntimeException(destination.getClass().getTypeName()+" can't cast to"+ source.getClass().getTypeName());
        }
        return destination;
    }
}
