package com.manfred.servercontrolling;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
public class ConnectWithServer {
    String hostname = "";
    String username = "";
    String password = "";
    String command = "";
    String files = "";

    public ConnectWithServer(String command) {

        this.command = command;
    }

    public String listFiles(){
        try
        {
            /* Create a connection instance */
            String hostname = "192.168.43.239";
            String username = "scdv4001";
            String password = "SCDV4001DEV";

            Connection conn = new Connection(hostname);

            /* Now connect */

            conn.connect();

            /* Authenticate.
             * If you get an IOException saying something like
             * "Authentication method password not supported by the server at this stage."
             * then please check the FAQ.
             */

            boolean isAuthenticated = conn.authenticateWithPassword(username, password);

            if (isAuthenticated == false)
                throw new IOException("Authentication failed.");

            /* Create a session */

            Session sess = conn.openSession();

            sess.execCommand(this.command);

            /*
             * This basic example does not handle stderr, which is sometimes dangerous
             * (please read the FAQ).
             */

            InputStream stdout = new StreamGobbler(sess.getStdout());

            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

            while (true)
            {
                String line = br.readLine();
                if (line == null)
                    break;
                files+=line+"\n";
            }

            /* Show exit status, if available (otherwise "null") */

            /* Close this session */

            sess.close();

            /* Close the connection */

            conn.close();

        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
            System.exit(2);
        }
        return files;
    }

}
