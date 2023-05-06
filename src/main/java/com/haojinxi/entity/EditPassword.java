package com.haojinxi.entity;

import lombok.Data;

@Data
public class EditPassword {

    private String oldPass;

    private String newPass;

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getOldPass() {
        return oldPass;
    }

    public String getNewPass() {
        return newPass;
    }
}
