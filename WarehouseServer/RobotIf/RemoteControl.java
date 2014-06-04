/******************************************************************************************************************
* File: RemoteControl.java
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
*	Compiled in a command window as follows: javac RemoteControl.java
*	Execute the program from a command window as follows: java RemoteControl <Server IP Address>
*
* Parameters: 		Server IP Address
*
* Internal Methods: None
*
******************************************************************************************************************/

import java.io.*;
import java.net.*;

class RemoteControl
{
	public static void main(String argv[]) throws Exception
 	{
    	String Forward = "F";			// Forward command
    	String Backward = "B";			// Backward commad
    	String FullStop = "S";			// Stop command
		String JogRight = "R";			// Brief turn right command
		String JogLeft  = "L";			// Brief turn left command
		String Disconnect = "X";		// Disconnect from the server and exit app
		Socket clientSocket = null;		// The socket.
    	boolean done = false;			// Loop flag.
    	int	portNum = 501;				// Port number for server socket
    	Integer UserCommand	= -1;		// User selection from the menu
    	BufferedReader UserInput; 		// User keyboard input
    	BufferedWriter out = null;		// Socket output to server
 
		/*****************************************************************************
    	* This is the object for the buffered reader for user I/O
		*****************************************************************************/

		UserInput = new BufferedReader(new InputStreamReader(System.in));

		while (!done)
		{
	 		/*****************************************************************************
    		* First we check to make sure a server was specified on the command like
			*****************************************************************************/

  	  		if (argv.length == 0)
 	  		{
 	  			System.out.println ( "\nPlease specify an IP address on the command line.\n" );
  				System.exit(1);
   	  			
			} else {

   	  			System.out.println ( "\n\nUsing server " + argv[0] + " on port " + portNum + ".\n" );
	   	  			   	  			
			}
			
   	    	Thread.sleep(1000);

			/*****************************************************************************
   			* If we get here we are connected and we have a socket. Now we present the 
   			* users with options.
			*****************************************************************************/

			System.out.println ( "\nPlease select a command:\n" );
			System.out.println ( "1.	Stop" );
			System.out.println ( "2.	Forward" );
			System.out.println ( "3.	Backward" );
			System.out.println ( "4.	Jog Right" );
			System.out.println ( "5.	Jog Left" );
			System.out.println ( "0.	Exit\n" );			
			System.out.print ( ">>>> " );
	
			/*****************************************************************************
			* Here we read the input from the user. Note that it will only read integers 
			* and if you plan on creating a real remote control, this should be a bit 
			* more robust.
			*****************************************************************************/	

			UserCommand = UserCommand.valueOf(UserInput.readLine());
	
			try	
	   		{
				/*************************************************************************
				* Here we open a socket to the server
				*************************************************************************/

	   			clientSocket = new Socket(argv[0], portNum);
				out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

			/*****************************************************************************
			* Here we figure out what the command is and write the appropriate command
	  		* string to the server
			*****************************************************************************/

				switch (UserCommand)
				{
					case 0:
						out.write( Disconnect, 0, Disconnect.length());
						out.flush();
						done = true;
						break;

					case 1:
						out.write( FullStop, 0, FullStop.length());
						out.flush();
						break;
					
					case 2:
						out.write( Forward, 0, Forward.length());
						out.flush();
						break;

					case 3:
						out.write( Backward, 0, Backward.length());
						out.flush();
						break;

					case 4:
						out.write( JogRight, 0, JogRight.length());
						out.flush();
 						break;

					case 5:
						out.write( JogLeft, 0, JogLeft.length());
						out.flush();
						break;

					default:
						System.out.println( "Invalid Command...\n" );

				} // switch

  			} catch (Exception e) {
			
				System.err.println( "Socket Error::" + e);
				
			}
		
			Thread.sleep(1000);

			/*****************************************************************************
	   		* That's it! Close the stream and socket.
			*****************************************************************************/

			try 
			{
				out.close();
  				clientSocket.close();
	
			} catch (Exception e) {
	
				System.err.println( "Close error::" + e);
	
			} // try

		} // while
		
 	} // main
 	
} // class