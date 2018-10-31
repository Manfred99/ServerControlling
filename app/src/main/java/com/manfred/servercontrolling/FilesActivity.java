package com.manfred.servercontrolling;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

public class FilesActivity extends AppCompatActivity {
    ArrayList<String> listaNombreFiles;
    SshConection sshConectionFiles;
    Session session;
    private String output="aaa\n";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaNombreFiles = new ArrayList<>();
        sshConectionFiles = new SshConection();
        try {
            sshConectionFiles.execute("ls").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_files);
        fillExpandibleList();

        /*
        ConnectWithServer conServerDev = new ConnectWithServer();
        List<String> files = null;
        try {
            files = conServerDev.listFiles("scdv4001","SCDV4001DEV",
                    "scdv4001@192.168.43.239","");
        } catch (IOException e) {
        }

            Toast.makeText(FilesActivity.this, files.size(),
                    Toast.LENGTH_SHORT).show();
         */
        ConnectWithServer connection1 = new ConnectWithServer("ls");
        String files = connection1.listFiles();
        Toast.makeText(FilesActivity.this, files,
                Toast.LENGTH_SHORT).show();

    }
    public void fillExpandibleList() {

        List<Map<String, String>> interestsList = new ArrayList<Map<String,String>>();
        /**
        ConnectWithServer conServerDev = new ConnectWithServer("ls");
        String files = conServerDev.listFiles();

        StringTokenizer st = new StringTokenizer(files,"\n");
        while (st.hasMoreTokens()){
         interestsList.add(createInterest("interest", st.nextToken()));
        }
         **/


        interestsList.add(createInterest("interest", "Sports"));
        interestsList.add(createInterest("interest", "Video Games"));
        interestsList.add(createInterest("interest", "Studying"));
        interestsList.add(createInterest("interest", "Volunteering"));

        SimpleAdapter simpleAdpterForListView =
                new SimpleAdapter(this, interestsList, android.R.layout.simple_list_item_checked,
                        new String[] {"interest"}, new int[] {android.R.id.text1});

        ListView lv = (ListView) findViewById(R.id.listViewFiles);

        lv.setAdapter(simpleAdpterForListView);

    }
    private HashMap<String, String> createInterest(String key, String name) {
        HashMap<String, String> interest = new HashMap<String, String>();
        interest.put(key, name);
        return interest;

    }
    private class SshConection extends AsyncTask<String, Void, ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            try {
                JSch jsch = new JSch();

                String host = "scdv4001@192.168.1.8";// enter username and ipaddress for machine you need to connect

                String user = host.substring(0, host.indexOf('@'));
                host = host.substring(host.indexOf('@') + 1);

                session = jsch.getSession(user, host, 22);

                // username and password will be given via UserInfo interface.
                UserInfo ui = new MyUserInfo();
                session.setUserInfo(ui);
                session.connect();

                String command =  params[0]; // enter any command you need to execute

                Channel canalServidor=session.openChannel("exec");
                ((ChannelExec)canalServidor).setCommand(command);


                canalServidor.setInputStream(null);

                ((ChannelExec)canalServidor).setErrStream(System.err);

                InputStream informacionDelServidor =canalServidor.getInputStream();

                canalServidor.connect();

                byte[] arregloBytes=new byte[1024];
                while (true) {
                    while (informacionDelServidor.available() > 0) {
                        int i = informacionDelServidor.read(arregloBytes, 0, 1024);
                        if (i < 0) {
                            break;
                        }
                        System.out.println("----------------");
                        String text = new String(arregloBytes, 0, i);
                        StringTokenizer st = new StringTokenizer(text,"\n");
                        while(st.hasMoreTokens()){
                            listaNombreFiles.add(st.nextToken());
                        }





                    }
                    if (canalServidor.isClosed()) {
                        System.out.println("exit-status: " + canalServidor.getExitStatus());
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ee) {
                    }


                }
                canalServidor.disconnect();
                session.disconnect();


            } catch (Exception e) {
                System.out.println(e);
            }
            return listaNombreFiles;
        }
    }

}
