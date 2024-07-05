package com.example.myapplication;

public class ResponseModel {

    private String _id;
    private String ocrUrl;
    private int __v;

    public ResponseModel() {
        // Default constructor
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getOcrUrl() {
        return ocrUrl;
    }

    public void setOcrUrl(String ocrUrl) {
        this.ocrUrl = ocrUrl;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    @Override
    public String toString() {
        return "OcrResponse{" +
                "_id='" + _id + '\'' +
                ", ocrUrl='" + ocrUrl + '\'' +
                ", __v=" + __v +
                '}';
    }
}
