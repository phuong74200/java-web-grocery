/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.user;

/**
 *
 * @author phuon
 */
public class UserError {

    private String userIDError;
    private String fullNameError;
    private String passwordError;
    private String rePasswordError;
    private String roleIDError;
    private String addressError;
    private String birthdayError;
    private String phoneError;
    private String emailError;

    public UserError() {
        this.userIDError = "";
        this.fullNameError = "";
        this.passwordError = "";
        this.rePasswordError = "";
        this.roleIDError = "";
        this.addressError = "";
        this.birthdayError = "";
        this.phoneError = "";
        this.emailError = "";
    }

    public UserError(String userIDError, String fullNameError, String passwordError, String rePasswordError, String roleIDError,
            String addressError, String birthdayError, String phoneError, String emailError) {
        this.userIDError = userIDError;
        this.fullNameError = fullNameError;
        this.passwordError = passwordError;
        this.roleIDError = roleIDError;
        this.addressError = addressError;
        this.birthdayError = birthdayError;
        this.phoneError = phoneError;
        this.rePasswordError = rePasswordError;
        this.emailError = emailError;
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public String getRePasswordError() {
        return rePasswordError;
    }

    public void setRePasswordError(String rePasswordError) {
        this.rePasswordError = rePasswordError;
    }

    public String getUserIDError() {
        return userIDError;
    }

    public void setUserIDError(String userIDError) {
        this.userIDError = userIDError;
    }

    public String getFullNameError() {
        return fullNameError;
    }

    public void setFullNameError(String fullNameError) {
        this.fullNameError = fullNameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getRoleIDError() {
        return roleIDError;
    }

    public void setRoleIDError(String roleIDError) {
        this.roleIDError = roleIDError;
    }

    public String getAddressError() {
        return addressError;
    }

    public void setAddressError(String addressError) {
        this.addressError = addressError;
    }

    public String getBirthdayError() {
        return birthdayError;
    }

    public void setBirthdayError(String birthdayError) {
        this.birthdayError = birthdayError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }

}
