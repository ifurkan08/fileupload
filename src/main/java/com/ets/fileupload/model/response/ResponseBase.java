package com.ets.fileupload.model.response;

import java.util.ArrayList;
import java.util.List;

public abstract class ResponseBase {
    private List<String> errorList = new ArrayList<>();
    public void addError(String error){
        errorList.add(error);
    }
    public List<String> getErrorList(){
        return errorList;
    }
}
