package com.example.red.modelo;

public class ModelUser {
    private int userID;
    private String userName;
    private String email;
    private String password;
    private boolean administrador;
    private String verifyCode;

    public ModelUser(int userID, String userName, String email, String password, boolean administrador,
            String verifyCode) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.administrador = administrador;
        this.verifyCode = verifyCode;
    }

    public ModelUser(int userID, String userName, String email, String password) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public ModelUser() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

}