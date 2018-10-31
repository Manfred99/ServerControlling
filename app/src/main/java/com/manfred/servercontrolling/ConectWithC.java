package com.manfred.servercontrolling;

import android.support.v7.app.AppCompatActivity;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import java.io.InputStream;
import java.util.ArrayList;

public class ConectWithC{
    private String output;

    public ConectWithC(String output) {
        this.output = output;
    }

    public ConectWithC() {
        this.output="";
    }

    public String getOutput() {

        byte [] tmp = null;
        String  palabra = null;
        try{
            JSch jsch=new JSch();

            String host=null;
            //host="scdv4001@192.168.43.239"; // enter username and ipaddress for machine you need to connect
            host="scdv4001@192.168.1.8";
            String user=host.substring(0, host.indexOf('@'));
            host=host.substring(host.indexOf('@')+1);

            Session session=jsch.getSession(user, host, 22);

            // username and password will be given via UserInfo interface.
            UserInfo ui=new MyUserInfo();
            session.setUserInfo(ui);
            palabra="a\n";

            session.connect();
            palabra+="b\n";
            String command=  "ls"; // enter any command you need to execute

            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);


            channel.setInputStream(null);

            ((ChannelExec)channel).setErrStream(System.err);

            InputStream in=channel.getInputStream();
            palabra+="c\n";
            channel.connect();
            palabra+="d\n";
            tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    String test = new String(tmp, 0, i);
                    palabra = test;

                }
                if(channel.isClosed()){
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }
            channel.disconnect();
            session.disconnect();
            output = palabra;
            return output;
        }
        catch(Exception e){
            System.out.println(e);
        }
        output = palabra;
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}



