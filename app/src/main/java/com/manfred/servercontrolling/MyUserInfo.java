package com.manfred.servercontrolling;

import com.jcraft.jsch.UserInfo;

public class MyUserInfo implements UserInfo {
    public String getPassword(){ return passwd; }
    public boolean promptYesNo(String str){
        str = "Yes";
        return true;}

    String passwd;

    public String getPassphrase(){ return null; }
    public boolean promptPassphrase(String message){ return true; }
    public boolean promptPassword(String message){
        passwd="SCDV4001DEV"; // enter the password for the machine you want to connect.
        return true;
    }
    public void showMessage(String message){

    }
}
