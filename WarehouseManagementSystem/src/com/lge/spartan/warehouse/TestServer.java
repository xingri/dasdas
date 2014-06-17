import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

public class TestServer {

	public static void main(String[] args) {
        ServerSocket serverSocket = null;
        boolean done = false;
        byte cmd = 0;
        boolean arrival = false;
        boolean watchdog = false;
        int test_count = 0;
 
        try {
            serverSocket = new ServerSocket(501);
            serverSocket.setSoTimeout(100);
            System.out.println(getTime() + " ������ �غ�Ǿ����ϴ�.");
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        while (!done) {
            try {
                //System.out.println(getTime() + " �����û�� ��ٸ��ϴ�.");
                Socket socket = serverSocket.accept();
                System.out.println(getTime() + socket.getInetAddress() + " �κ��� �����û�� ���Խ��ϴ�.");
                
                InputStream in = socket.getInputStream();
                DataInputStream dis = new DataInputStream(in);
                cmd = dis.readByte(); 
                System.out.println("Ŭ���̾�Ʈ�κ��� ���� �޼��� : " + cmd);
               
                switch (cmd) {
                case 32: // go next station
                	System.out.println("go next station");
                	test_count = 20; // robot arrives station in 2 sec. after moving start.
                	arrival = false;
                	break;
                case 56: // ping
                	System.out.println("ping");
                	break;
                case 64: // arrival
                	System.out.println("arrival");
                	if (!arrival) {
                		cmd = (byte)0x00;
                	} else {
                		System.out.println("robot arrival");
                		arrival = false;
                	}
                	break;
                case 72: // watchdog
                	System.out.println("watchdog");
                	if (!watchdog)
                		cmd = (byte)0x00;
                	break;
                default:
                }

                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    			out.write(cmd);
    			out.flush();
    			
                dis.close();           
                socket.close();
                
//                if (cmd == (byte)0x30) {
//                	System.out.println("Terminate Test Server");
//                	done = true;
//                }
            } catch (IOException e) {
            	//System.err.println( "Server Socket error: ack: " + e);
                //e.printStackTrace();
            }
            
            try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            if (test_count != 0) {
            	test_count--;
            	System.out.println("test count : " + test_count);
            	if (test_count == 0)
            		arrival = true;
            } 
            
       }
    }

    static String getTime() {
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date());
    } 
}
