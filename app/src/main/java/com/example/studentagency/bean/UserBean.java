package com.example.studentagency.bean;

import androidx.annotation.NonNull;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/06
 * desc:
 */
public class UserBean {
    private int userId;
    private String username;
    private String password;
    private int studentId;
    private String phoneNum;
    private String avatar;
    private int gender;
    private String verifyPic;
    private int verifyState;
    private String school;
    private Float balance;
    private int creditScore;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getVerifyPic() {
        return verifyPic;
    }

    public void setVerifyPic(String verifyPic) {
        this.verifyPic = verifyPic;
    }

    public int getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(int verifyState) {
        this.verifyState = verifyState;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    @NonNull
    @Override
    public String toString() {
        return "\n[ userId:" + userId +
                "\n username:" + username +
                "\n password:" + password +
                "\n studentId:" + studentId +
                "\n phoneNum:" + phoneNum +
                "\n avatar:" + avatar +
                "\n gender:" + gender +
                "\n verifyPic:" + verifyPic +
                "\n verifyState:" + verifyState +
                "\n school:" + school +
                "\n balance:" + balance +
                "\n creditScore:" + creditScore + "]";
    }
}
