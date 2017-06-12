package com.canoo.dolphin.async;

import java.io.Serializable;

public class RemoteCommand implements Serializable{

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
