package com.app.base.log;

import android.annotation.SuppressLint;
import android.os.Build;

public class DeviceInfo{
    //Build.BOARD  //主板
    //Build.BOOTLOADER//系统启动程序版本
    //Build.BRAND//系统定制商
    //Build.CPU_ABI//cpu指令集
    //Build.CPU_ABI2//cpu指令集2
    //Build.DEVICE//设置参数
    //Build.DISPLAY//显示屏参数
    //Build.MANUFACTURER//硬件制造商
    //Build.getRadioVersion//无线电固件版本
    //Build.FINGERPRINT//硬件识别码
    //Build.HARDWARE//硬件名称
    //Build.HOST //HOS
    //Build.ID// 修订版本列表
    //Build.MODEL//手机型号(MI XXX)
    //Build.SERIAL//硬件序列号
    //Build.PRODUCT//手机制造商
    //Build.TAGS//描述Build的标签
    //Build.TIME//编译时间
    //Build.TYPE//builder类型
    //Build.USER//USER
    //Build.VERSION.CODENAME//当前开发代号
    //Build.VERSION.INCREMENTAL//源码控制版本号
    //Build.VERSION.SDK_INT//版本号
    //Build.VERSION.RELEASE//版本字符串

    private String BRAND;//系统定制商
    private String MANUFACTURER;//硬件制造商
    private String FINGERPRINT;//硬件识别码
    private String HARDWARE;//硬件名称
    private String ID;// 修订版本列表
    private String MODEL;//手机型号(MI XXX)
    private String SERIAL;//硬件序列号
    private String PRODUCT;//手机制造商
    private int VERSION_SDK_INT;//版本号


    @SuppressLint("MissingPermission")
    public DeviceInfo(){
        this.BRAND = Build.BOARD;
        this.MANUFACTURER = Build.MANUFACTURER;
        this.FINGERPRINT = Build.FINGERPRINT;
        this.HARDWARE = Build.HARDWARE;
        this.ID = Build.ID;
        this.MODEL = Build.MODEL;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            this.SERIAL = Build.getSerial();
        } else{
            this.SERIAL = Build.SERIAL;
        }
        this.PRODUCT = Build.PRODUCT;
        this.VERSION_SDK_INT = Build.VERSION.SDK_INT;
    }

    @Override
    public String toString(){
        return "设备的信息{" + "系统定制商='" + BRAND + '\'' + ", 硬件制造商='" + MANUFACTURER + '\'' + ", 硬件识别码='" + FINGERPRINT +
               '\'' + ", 硬件名称='" + HARDWARE + '\'' + ", 修订版本列表='" + ID + '\'' + ", 手机型号='" + MODEL + '\'' +
               ", 硬件序列号='" + SERIAL + '\'' + ", 手机制造商='" + PRODUCT + '\'' + ", 系统版本号=" + VERSION_SDK_INT + '}';
    }

    public String getBRAND(){
        return BRAND;
    }

    public void setBRAND(String BRAND){
        this.BRAND = BRAND;
    }

    public String getMANUFACTURER(){
        return MANUFACTURER;
    }

    public void setMANUFACTURER(String MANUFACTURER){
        this.MANUFACTURER = MANUFACTURER;
    }

    public String getFINGERPRINT(){
        return FINGERPRINT;
    }

    public void setFINGERPRINT(String FINGERPRINT){
        this.FINGERPRINT = FINGERPRINT;
    }

    public String getHARDWARE(){
        return HARDWARE;
    }

    public void setHARDWARE(String HARDWARE){
        this.HARDWARE = HARDWARE;
    }

    public String getID(){
        return ID;
    }

    public void setID(String ID){
        this.ID = ID;
    }

    public String getMODEL(){
        return MODEL;
    }

    public void setMODEL(String MODEL){
        this.MODEL = MODEL;
    }

    public String getSERIAL(){
        return SERIAL;
    }

    public void setSERIAL(String SERIAL){
        this.SERIAL = SERIAL;
    }

    public String getPRODUCT(){
        return PRODUCT;
    }

    public void setPRODUCT(String PRODUCT){
        this.PRODUCT = PRODUCT;
    }

    public int getVERSION_SDK_INT(){
        return VERSION_SDK_INT;
    }

    public void setVERSION_SDK_INT(int VERSION_SDK_INT){
        this.VERSION_SDK_INT = VERSION_SDK_INT;
    }
}
