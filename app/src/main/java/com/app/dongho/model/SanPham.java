package com.app.dongho.model;

public class SanPham {
    private int maSP;
    private int maLoai;
    private String tenSP;
    private double giaNhap;
    private double giaBan;
    private int soLuong;
    private int soLuongDaBan;
    private String moTa;
    private byte[] hinhAnh;
    private String tenLoai;

    public SanPham() {
    }

    public SanPham(int maSP, int maLoai, String tenSP, double giaNhap, double giaBan, String moTa, int soLuong, int soLuongDaBan, byte[] hinhAnh) {
        this.maSP = maSP;
        this.maLoai = maLoai;
        this.tenSP = tenSP;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.moTa = moTa;
        this.soLuong = soLuong;
        this.soLuongDaBan = soLuongDaBan;
        this.hinhAnh = hinhAnh;
    }

    public SanPham(int maSP, String tenSP, double giaBan, int soLuong, byte[] hinhAnh) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
    }

    public SanPham(String tenSP, double giaBan, int soLuong, byte[] hinhAnh) {
        this.tenSP = tenSP;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
    }

    public SanPham(int maLoai, String tenSP, double giaNhap, double giaBan, String moTa, int soLuong, byte[] hinhAnh) {
        this.maLoai = maLoai;
        this.tenSP = tenSP;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.moTa = moTa;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
    }

    public SanPham(int maSP, int maLoai, String tenSP, double giaNhap, double giaBan, String moTa, int soLuong, byte[] hinhAnh) {
        this.maSP = maSP;
        this.maLoai = maLoai;
        this.tenSP = tenSP;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.moTa = moTa;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
    }

    public SanPham(int maSP, int maLoai, String tenSP, double giaNhap, double giaBan, String moTa, int soLuong, byte[] hinhAnh, String tenLoai) {
        this.maSP = maSP;
        this.maLoai = maLoai;
        this.tenSP = tenSP;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.moTa = moTa;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
        this.tenLoai = tenLoai;
    }

    public int getSoLuongDaBan() {
        return soLuongDaBan;
    }

    public void setSoLuongDaBan(int soLuongDaBan) {
        this.soLuongDaBan = soLuongDaBan;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
