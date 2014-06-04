/******************************************************************************************************************
* File: RobotControl.java
* Course/Project: None
*
* Copyright: Copyright (c) 2013 Carnegie Mellon University
* Versions:
*	2.0 May	2014 - Initial Version
*
* Description:
*
* This class serves as an example for how to write a remote controller for an Arbot. There is nothing uniquely
* specific to the Arduino in this code, other than the application level protocol that is used between this
* application and the Arduino. The assumption is that the Arduino CommandServer application is running
* on the Arduino. When this application is started it attempts to connect to the ArBot server. Once connected
* this application takes user commands and sends them to the Arbot where they are processed into bot motion.
*
* Compilation and Execution Instructions:
*
*	Compiled in a command window as follows: javac RobotControl.java
*	Execute the program from a command window as follows: java RobotControl <Server IP Address>
*
* Parameters: 		Server IP Address
*
* Internal Methods: None
*
******************************************************************************************************************/

import java.io.*;
import java.net.*;

class RobotControl
{
   	String Forward = "F";			// Forward command
   	String Backward = "B";			// Backward commad
   	String FullStop = "S";			// Stop command
	String JogRight = "R";			// Brief turn right command
	String JogLeft  = "L";			// Brief turn left command
	String GoNext  = "G";			// Brief turn left command
	String Disconnect = "X";		// Disconnect from the server and exit app

	Socket clientSocket = null;		// The socket.
   	int	portNum = 501;				// Port number for server socket

   	BufferedReader UserInput; 		// User keyboard input
   	BufferedWriter out = null;		// Socket output to server

	public void sendGoNextCmd(String robotIP) {
		try {
			clientSocket = new Socket(robotIP, portNum);
			out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			out.write( GoNext, 0, GoNext.length());
			out.flush();
			out.close();
			clientSocket.close();

		} catch (Exception e) {
            System.err.println( "Handling GoNext Exception: " + e);
		}
	}

	public void sendStopCmd(String robotIP) {
		try {
			clientSocket = new Socket(robotIP, portNum);
			out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			out.write( FullStop, 0, FullStop.length());
			out.flush();
			out.close();
			clientSocket.close();

		} catch (Exception e) {
            System.err.println( "Handling Stop Exception: " + e);
		}
	}

	public void sendFwdCmd(String robotIP) {
		try {
			clientSocket = new Socket(robotIP, portNum);
			out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			out.write( Forward, 0, Forward.length());
			out.flush();
			out.close();
			clientSocket.close();

		} catch (Exception e) {
            System.err.println( "Handling Fwd Exception: " + e);
		}
	}

} // class
