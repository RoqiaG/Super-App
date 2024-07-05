package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    private String message;
    private String token;
    private User user;

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public class User {
        @SerializedName("_id")
        private String id;
        private String organizationId;
        private String organizationName;
        private PermissionId permissionId;
        private boolean userStatus;
        private String username;
        private String userPassword;
        private String userNationalID;
        private String userEmail;
        private int __v;
        private int businessUserId;

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

        public PermissionId getPermissionId() {
            return permissionId;
        }

        public void setPermissionId(PermissionId permissionId) {
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

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
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

        private String userMobileNumber;

        // Getters for User fields
        // Add setters if needed
    }

    public class PermissionId {
        @SerializedName("_id")
        private String id;
        private String organizationId;
        private String organizationName;
        private boolean userStatus;
        private boolean superAdmin;
        private boolean organizationAdmin;
        private int __v;
        private boolean consumer;
        private boolean fieldAgent;
        private boolean inventoryWorker;
        private boolean merchant;
        private boolean serviceAgent;

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

        public boolean isUserStatus() {
            return userStatus;
        }

        public void setUserStatus(boolean userStatus) {
            this.userStatus = userStatus;
        }

        public boolean isSuperAdmin() {
            return superAdmin;
        }

        public void setSuperAdmin(boolean superAdmin) {
            this.superAdmin = superAdmin;
        }

        public boolean isOrganizationAdmin() {
            return organizationAdmin;
        }

        public void setOrganizationAdmin(boolean organizationAdmin) {
            this.organizationAdmin = organizationAdmin;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public boolean isConsumer() {
            return consumer;
        }

        public void setConsumer(boolean consumer) {
            this.consumer = consumer;
        }

        public boolean isFieldAgent() {
            return fieldAgent;
        }

        public void setFieldAgent(boolean fieldAgent) {
            this.fieldAgent = fieldAgent;
        }

        public boolean isInventoryWorker() {
            return inventoryWorker;
        }

        public void setInventoryWorker(boolean inventoryWorker) {
            this.inventoryWorker = inventoryWorker;
        }

        public boolean isMerchant() {
            return merchant;
        }

        public void setMerchant(boolean merchant) {
            this.merchant = merchant;
        }

        public boolean isServiceAgent() {
            return serviceAgent;
        }

        public void setServiceAgent(boolean serviceAgent) {
            this.serviceAgent = serviceAgent;
        }
// Getters for PermissionId fields
        // Add setters if needed
    }
}
