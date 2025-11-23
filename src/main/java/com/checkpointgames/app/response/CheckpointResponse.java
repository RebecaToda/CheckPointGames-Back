package com.checkpointgames.app.response;

import java.util.ArrayList;
import java.util.List;

public class CheckpointResponse<T> {
    
    private T data;
    private List<String> errors;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getErrosr() {
        if (this.errors == null) {
            this.errors = new ArrayList<String>();
        }
        return errors;
    }

    public void setErrosr(List<String> errosr) {
        this.errors = errosr;
    }
    
    
    
}
