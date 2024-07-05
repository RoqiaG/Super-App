package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class checkNILoginResponse {

    private boolean exists;

    @SerializedName("user")
    private User user;

    // Getters and setters
    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // User class
    public static class User {
        @SerializedName("_id")
        private String id;

        @SerializedName("organizationId")
        private String organizationId;

        @SerializedName("organizationName")
        private String organizationName;

        @SerializedName("permissionId")
        private String permissionId;

        @SerializedName("userStatus")
        private boolean userStatus;

        @SerializedName("username")
        private String username;

        @SerializedName("userPassword")
        private String userPassword;

        @SerializedName("userNationalID")
        private String userNationalID;

        @SerializedName("userEmail")
        private String userEmail;

        @SerializedName("__v")
        private int v;

        @SerializedName("businessUserId")
        private int businessUserId;

        @SerializedName("userMobileNumber")
        private String userMobileNumber;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrganizationId() {
            return organizationId;
        }

        public void setOrganizationId(String organizationId) {
            this.organizationId = organizationId;
        }

        public String getOrganizationName() {
            return organizationName;
        }

        public void setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
        }

        public String getPermissionId() {
            return permissionId;
        }

        public void setPermissionId(String permissionId) {
            this.permissionId = permissionId;
        }

        public boolean isUserStatus() {
            return userStatus;
        }

        public void setUserStatus(boolean userStatus) {
            this.userStatus = userStatus;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public String getUserNationalID() {
            return userNationalID;
        }

        public void setUserNationalID(String userNationalID) {
            this.userNationalID = userNationalID;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }

        public int getBusinessUserId() {
            return businessUserId;
        }

        public void setBusinessUserId(int businessUserId) {
            this.businessUserId = businessUserId;
        }

        public String getUserMobileNumber() {
            return userMobileNumber;
        }

        public void setUserMobileNumber(String userMobileNumber) {
            this.userMobileNumber = userMobileNumber;
        }

        public String getLastLoginAt() {
            return lastLoginAt;
        }

        public void setLastLoginAt(String lastLoginAt) {
            this.lastLoginAt = lastLoginAt;
        }

        @SerializedName("lastLoginAt")
        private String lastLoginAt;

        // Getters and setters
        // Implement getters and setters for all fields
    }
}
