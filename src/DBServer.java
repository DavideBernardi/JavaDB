import Interpreter.Interpreter;
import Interpreter.ParseException;
import bespokeUtilities.StringJoiner;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

//TODO Once Engine refactoring is done, edit
class DBServer
{
    final static char EOT = 4;

    public static void main(String[] args) {
        new DBServer(8888);
    }

    public DBServer(int portNumber)  {
        try {
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            while(true) acceptNextConnection(ss);
        } catch(IOException ioe) {
            System.err.println(ioe.toString());
        }
    }

    private void acceptNextConnection(ServerSocket ss) {
        try {
            // Next line will block until a connection is received
            Socket socket = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Interpreter interp = new Interpreter();
            while(true) processNextCommand(in, out, interp);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void processNextCommand(BufferedReader in, BufferedWriter out, Interpreter interp) throws IOException {
        String line = in.readLine();
        long startTime = System.nanoTime();
        String msg;
        try {
            StringJoiner strjoin = new StringJoiner();
            msg = strjoin.join(interp.execute(line));
        } catch(ParseException pe) {
            pe.printStackTrace();
            msg = pe.ToString();
        }
        out.write(msg +"\n"+EOT+"\n");
        out.flush();
        long executionTime = System.nanoTime() - startTime;
        System.out.println("This command was executed in " + executionTime/1000000 + "ms");
    }

}


