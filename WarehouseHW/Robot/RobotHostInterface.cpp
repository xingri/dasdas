#include "RobotHostInterface.h"

RobotHostInterface::RobotHostInterface(int clientPort, int serverPort)
{
	robotServerPort = serverPort;
	hostServerPort = clientPort;
}

void RobotHostInterface::connect()
{
	char ssid[] = SSID;
	int status = WL_IDLE_STATUS;
	robotServer = new WiFiServer(robotServerPort);

	Serial.println("Attempting to connect to network...");
	Serial.print("SSID: ");
	Serial.println(ssid);

	// Attempt to connect to Wifi network.
	while ( status != WL_CONNECTED) {
		Serial.print("Attempting to connect to SSID: ");
		Serial.println(ssid);
		status = WiFi.begin(ssid);
	}

	// Print the basic connection and network information.
	printConnectionStatus();

	// Start the server and print a message to the terminial.
	robotServer->begin();
	Serial.println("The Robot Server is started.");

	Serial.println( "----------------------------------------\n" );
}

byte RobotHostInterface::rcvCommand()
{
	byte command = 0xFF;
	WiFiClient client = robotServer->available();

	if (client) {
		Serial.print("Robot Client connected...");
		Serial.println("Waiting for a command......" );

		while (client.available() == 0 ) {
			delay( 500 );
		}

		command = (byte)client.read();
		Serial.print("Command:: ");
		Serial.println(command);

		/* send acknowledgement */
		robotServer->write(command);

		client.stop();
		Serial.println("Client Disconnected.\n");
	}

	return command;
}

void RobotHostInterface::sendArrival()
{
	Serial.print("\nAttempting to connect to server...");

	IPAddress hostServerIP(128, 237, 114, 45); // XXX: TODO: shoud be dynamic

	if (hostClient.connect(hostServerIP, hostServerPort)) {
		Serial.println("connected");

		hostClient.println("Howdy there big PC...");

		Serial.print("Server Message: ");

		char c = ' ';
		while ( c!= '\n' )
		{
			if (hostClient.available())
			{
				c = hostClient.read();
				Serial.write(c);
			}
		}

		// That's it. We wait a second, then do it all again.
		Serial.println( "Done...");
		delay(1000);
	}
}

void RobotHostInterface::printConnectionStatus()
{
	// Print the basic connection and network information: Network, IP, and Subnet mask
	ip = WiFi.localIP();
	Serial.print("Connected to ");
	Serial.print(SSID);
	Serial.print(" IP Address:: ");
	Serial.println(ip);
	subnet = WiFi.subnetMask();
	Serial.print("Netmask: ");
	Serial.println(subnet);

	// Print our MAC address.
	WiFi.macAddress(mac);
	Serial.print("WiFi Shield MAC address: ");
	Serial.print(mac[5],HEX);
	Serial.print(":");
	Serial.print(mac[4],HEX);
	Serial.print(":");
	Serial.print(mac[3],HEX);
	Serial.print(":");
	Serial.print(mac[2],HEX);
	Serial.print(":");
	Serial.print(mac[1],HEX);
	Serial.print(":");
	Serial.println(mac[0],HEX);

	// Print the wireless signal strength:
	rssi = WiFi.RSSI();
	Serial.print("Signal strength (RSSI): ");
	Serial.print(rssi);
	Serial.println(" dBm");
}
