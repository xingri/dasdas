package com.lge.spartan.warehouse.hardware;

import java.io.*;
import java.net.*;
import java.util.concurrent.Delayed;

public class WHCommandClient {
	private enum RobotCmd {
		GO_FWD((byte)(0<<4)),
		GO_BWD((byte)(1<<4)),
		TURN_L((byte)(2<<4)),
		TURN_R((byte)(3<<4)),
		GO_STATION((byte)(4<<4)),
		NEAR_STATION((byte)(5<<4)),
		STOP((byte)(6<<4));
		
		public final static int MAX_STATION = 4;
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

	public WHCommandClient(int portNum) {
		this.portNum = 501;
	}

	public WHCommandClient(String robotIP) {
		this.robotIP = robotIP;
	}

	public WHCommandClient(String robotIP, int portNum) {
		this.robotIP = robotIP;
		this.portNum = portNum;
	}

	public boolean goStation(int station) {
		byte cmd = RobotCmd.GO_STATION.getCmd();
		
		if (station > RobotCmd.MAX_STATION) {
			System.out.println("Station number must be under 4..");
			return false;
		}
		
		cmd |= (byte)station;
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
	
	private boolean sendCmd(byte cmd) {
		boolean ret = true;
		byte ack = 0;
		InputStream in = null;
		BufferedWriter out = null;

		System.out.println("SendCmd :" + cmd);
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
			System.out.println("Ack from robot: " + ack);
			if (ack != cmd) {
				System.out.println("Ack should be same with cmd sent");
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
