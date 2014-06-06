/*********************************************************************************************
 * File: CommandServer
 * Project: LG Exec Ed Program
 * Copyright: Copyright (c) 2014 Anthony J. Lattanze
 * Versions:
 *	1.0 May 2014.
 *
 * Description:
 *
 * This program runs on an Arduio processor with a WIFI shield. This program will act as a 
 * general server that runs on the Arbot. Commands are send via a client Java program 
 * (RemoteControl.java). This pair of applications serve to provide remote control of the Arbot
 * over WiFi. Note that this application acts as a server, the Java program (RemoteControl.java)
 * is the client. Note that fixed IP addresses are not possible for this lab, so you must plug
 * the Arbot into your PC to first get the IP address for the client, then you can connect to 
 * bot and control it. General flow, is that the client connects to the server (tbe Arbot), sends
 * a single command and then closes. The reason for this is that the Arduino WIFI shield socket
 * will timeout unless you are streaming data to it. The supported commands are forward, backward
 * jog right/left (slight turns), and stop. Other commands can easily be added to this and 
 * client applications can easily be extend.
 *
 * Compilation and Execution Instructions: Compiled using Arduino IDE 1.0.4
 *
 * Parameters: None
 *
 ************************************************************************************************/

#include <SPI.h>
#include <WiFi.h>

#define SERIAL_DEBUG	1
#if SERIAL_DEBUG
#define println_serial(fmt,...) Serial.println(fmt,##__VA_ARGS__);
#define print_serial(fmt,...) Serial.print(fmt,##__VA_ARGS__);
#else
#define println_serial(var)
#define print_serial(var)
#endif

#define PORTID 501

struct robot_cmd {
	char cmd;
	char arg;
};

char ssid[] = "CMU";           // The network SSID (name)
int status = WL_IDLE_STATUS;   // The status of the network connections
WiFiServer server(PORTID);     // The WIFI status,.. we are using port 500
char inChar;                   // This is a character sent from the client
IPAddress ip;                  // The IP address of the shield
IPAddress subnet;              // The subnet we are connected to
long rssi;                     // The WIFI shield signal strength
byte mac[6];                   // MAC address of the WIFI shield
boolean done;                  // Loop flag
boolean commandRead;           // Loop flag

#if 0
void go_forward(char);
void go_backward(char);
void turn_left(char);
void turn_right(char);
void stop(char);
void go_station(char);
void near_station(char);
#endif

void go_forward(char step) {
	println_serial(__func__);
}

void go_backward(char step) {
	println_serial(__func__);
}

void turn_left(char step) {
	println_serial(__func__);
}

void turn_right(char step) {
	println_serial(__func__);
}

void stop(char dummy) {
	println_serial(__func__);
}

void go_station(char station) {
	println_serial(__func__);
}

void near_station(char dummy) {
	println_serial(__func__);
}

void (*robot_action[])(char)= {
	&go_forward,
	&go_backward,
	&turn_left,
	&turn_right,
	&go_station,
	&near_station,
	&stop,
};


byte receive_cmd() {
	char command = 0;
	WiFiClient client = server.available();

	if (client) {
		print_serial("Client connected...");
		println_serial( "Waiting for a command......" );

		while ( client.available() == 0 ) {
			delay( 500 );
		}

		command = client.read();
		print_serial( "Command:: " );
		println_serial( command, HEX);

		/* send acknowledgement */
		server.write(command);

		client.stop();
		println_serial( "Client Disconnected.\n" );
	} else {
		command = 0xff;
	}

	return command;
}

struct robot_cmd *process_cmd(char raw_cmd) {
	/* On initial state, robot is stopped */
	static struct robot_cmd processed_cmd = {
		6, /* 6 : stop command */
		0, /* don't care of arguement */
	};

	processed_cmd.cmd = raw_cmd >> 4;
	processed_cmd.arg = raw_cmd & 0x0f;

	return &processed_cmd;
}

void do_cmd(char cmd, char arg) {
	void (*action)(char);

	action = robot_action[cmd];
	action(arg);
}

void setup() {
#if SERIAL_DEBUG
	// Initialize serial port. This is used for debug.
	Serial.begin(9600);
#endif

	// Attempt to connect to WIfI network indicated by SSID.
	while ( status != WL_CONNECTED) {
		print_serial("Attempting to connect to SSID: ");
		println_serial(ssid);
		status = WiFi.begin(ssid);
	}

	// Print connection information to the debug terminal
	printConnectionStatus();

	// Start the server and print a message to the terminial.
	server.begin();
	println_serial("The Command Server is started.");
	println_serial("--------------------------------------\n\n");

} // setup

void loop() {
	byte raw_cmd = 0;
	struct robot_cmd *real_cmd;

	raw_cmd = receive_cmd();

	/* raw_cmd is 0xff, there is not any rx data *
	 * refer to real_cmd for actual movement of robot
	 * real_cmd->cmd
	 * 		0 : go forward
	 *		1 : go backward
	 * 		2 : turn left
	 *		3 : turn right
	 *		4 : go station
	 *		5 : near station
	 *		6 : stop
	 * real_cmd->arg
	 * 		if cmd is 'go {forward|backward}'
	 * 			# of movement step
	 * 		else if cmd is 'turn {left|right}'
	 * 			# of jogging step
	 * 		else if cmd is 'go station'
	 * 			# of station
	 * 		else
	 * 			no meaning
	 */
	if (raw_cmd != 0xff) {
		real_cmd = process_cmd(raw_cmd);
		print_serial("Command :");
		println_serial(real_cmd->cmd, HEX);
		print_serial("Arg :");
		println_serial(real_cmd->arg, HEX);
	}

	do_cmd(real_cmd->cmd, real_cmd->arg);

} // loop

void printConnectionStatus() {
	// Print the basic connection and network information: Network, IP, and Subnet mask
	ip = WiFi.localIP();
	print_serial("Connected to ");
	print_serial(ssid);
	print_serial(" IP Address:: ");
	println_serial(ip);
	subnet = WiFi.subnetMask();
	print_serial("Netmask: ");
	println_serial(subnet);

	// Print our MAC address.
	WiFi.macAddress(mac);
	print_serial("WiFi Shield MAC address: ");
	print_serial(mac[5],HEX);
	print_serial(":");
	print_serial(mac[4],HEX);
	print_serial(":");
	print_serial(mac[3],HEX);
	print_serial(":");
	print_serial(mac[2],HEX);
	print_serial(":");
	print_serial(mac[1],HEX);
	print_serial(":");
	println_serial(mac[0],HEX);

	// Print the wireless signal strength:
	rssi = WiFi.RSSI();
	print_serial("Signal strength (RSSI): ");
	print_serial(rssi);
	println_serial(" dBm");
} // printConnectionStatus
