/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.google;

/**
 *
 * @author phuon
 */
public class CaptchaDTO {

    boolean success;

    public CaptchaDTO() {
        this.success = false;
    }

    public CaptchaDTO(boolean sucess) {
        this.success = sucess;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
