import java.io.*;
import java.net.*;
import java.util.function.Function;

public class WHRobotInf {
	private enum RobotCmd {
		GO_FWD((byte)(0<<3)),
		GO_BWD((byte)(1<<3)),
		TURN_L((byte)(2<<3)),
		TURN_R((byte)(3<<3)),
		GO_NEXT_STATION((byte)(4<<3)),
		NEAR_STATION((byte)(5<<3)),
		STOP((byte)(6<<3)),
		PING((byte)(7<<3)),
		ARRIVAL((byte)(8<<3)),
		WATCHDOG((byte)(9<<3));
		
		public final static int MAX_GO_STEP = 8;
		public final static int MAX_TURN_STEP = 6;
		private byte cmd = 0;
		
		RobotCmd(byte cmd) {
			this.cmd = (byte)cmd;
		}

		public byte getCmd() {
			return cmd;
		}
	}

	private Socket clientSocket = null;		// The socket.
	private String robotIP = "128.237.124.48";
	private int	portNum = 501;				// Port number for server socket

	public WHRobotInf(int portNum) {
		this.portNum = 501;
	}

	public WHRobotInf(String robotIP) {
		this.robotIP = robotIP;
	}

	public WHRobotInf(String robotIP, int portNum) {
		this.robotIP = robotIP;
		this.portNum = portNum;
	}

	public boolean goNextStation() {
		byte cmd = RobotCmd.GO_NEXT_STATION.getCmd();
		return sendCmd(cmd);
	}

	public boolean nearStation() {
		byte cmd = RobotCmd.NEAR_STATION.getCmd();
		return sendCmd(cmd);
	}

	public boolean goFoward(int step) {
		byte cmd = 0;
		if (step > RobotCmd.MAX_GO_STEP) {
			System.out.println("Going Step number must be under 6..");
			return false;
		}
		
		cmd |= RobotCmd.GO_FWD.getCmd();
		cmd |= (byte)step;
	
		return sendCmd(cmd);
	}
	
	public boolean goBackward(int step) {
		byte cmd = 0;
		if (step > RobotCmd.MAX_GO_STEP) {
			System.out.println("Going Step number must be under 6..");
			return false;
		}
		
		cmd |= RobotCmd.GO_BWD.getCmd();
		cmd |= (byte)step;

		return sendCmd(cmd);
	}
	
	public boolean turnLeft(int step) {
		byte cmd = 0;
		if (step > RobotCmd.MAX_TURN_STEP) {
			System.out.println("Turning Step number must be under 6..");
			return false;
		}
		
		cmd |= RobotCmd.TURN_L.getCmd();
		cmd |= (byte)step;

		return sendCmd(cmd);
	}
	
	public boolean turnRight(int step) {
		byte cmd = 0;
		if (step > RobotCmd.MAX_TURN_STEP) {
			System.out.println("Turning Step number must be under 6..");
			return false;
		}

		cmd |= RobotCmd.TURN_R.getCmd();
		cmd |= (byte)step;

		return sendCmd(cmd);
	}
	
	public boolean stop() {
		byte cmd = 0;
		cmd |= RobotCmd.STOP.getCmd();
		return sendCmd(cmd);
	}
	
	public boolean getHealth() {
		byte cmd = 0;
		cmd |= RobotCmd.PING.getCmd();
		return sendCmd(cmd);
	}

	public boolean getArrival() {
		byte cmd = 0;
		cmd |= RobotCmd.ARRIVAL.getCmd();
		return sendCmd(cmd);
	}

	public boolean getWatchdog() {
		byte cmd = 0;
		cmd |= RobotCmd.WATCHDOG.getCmd();
		return sendCmd(cmd);
	}

	private boolean sendCmd(byte cmd) {
		boolean ret = true;
		byte ack = 0;
		InputStream in = null;
		BufferedWriter out = null;

		//System.out.println("SendCmd :" + cmd);
		try {
			clientSocket = new Socket(robotIP, portNum);

			out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			out.write(cmd);
			out.flush();
		} catch (Exception e) {
			System.out.println( "Client Socket error: send: " + e);
			e.printStackTrace();
			ret = false;
		}
		
		try {
			Thread.sleep(100);
			in = clientSocket.getInputStream();
			DataInputStream dis = new DataInputStream(in);
			ack = dis.readByte();
			//System.out.println("Ack from robot: " + ack);
			if (ack != cmd) {
				//System.out.println("Ack should be same with cmd sent");
				ret = false;
			}
		} catch (Exception e) {
			System.out.println( "Client Socket error: ack: " + e);
			e.printStackTrace();
			ret = false;
		}
		
		try {
			out.close();
			clientSocket.close();
		} catch (IOException e) {
			System.out.println( "Socket Close error:" + e);
			e.printStackTrace();
			ret=false;
		}

		return ret;
	}
}
