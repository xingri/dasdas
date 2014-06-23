#include "RobotHostInterface.h"

void RobotHostInterface::connect(WiFiServer *_robotServer)
{
	char ssid[] = SSID;
	int status = WL_IDLE_STATUS;
	robotServer = _robotServer;

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

void RobotHostInterface::rcvCommand(struct HostCommand *command)
{
	byte rawCmd = 0xFF;
	WiFiClient client = robotServer->available();

	if (client) {
		Serial.print("Robot Client connected...");
		Serial.println("Waiting for a command......" );

		while (client.available() == 0 ) {
			delay( 100 );
		}

		rawCmd = (byte)client.read();
		Serial.print("Raw Command: ");
		Serial.print(rawCmd);

		command->cmd = rawCmd >> 3;
		command->arg = rawCmd & 0x07;

		Serial.print(", Cmd: ");
		Serial.print(command->cmd);
		Serial.print(", arg: ");
		Serial.println(command->arg);

		if (command->cmd == CMD_ARRIVAL || command->cmd == CMD_WATCHDOG) {
			if (command->cmd == CMD_ARRIVAL && getArrival()) {
				rawCmd = CMD_ARRIVAL << 3;
				setArrival(false);
			} else if (command->cmd == CMD_WATCHDOG) {
				rawCmd = 0xFF;
			} else {
				rawCmd = 0xFF;
			}
		}

		/* send acknowledgement */
		robotServer->write(rawCmd);

		delay(100);

		client.stop();
		Serial.println("Client Disconnected.\n");
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

void RobotHostInterface::setArrival(boolean newStatus)
{
	isArrived = newStatus;
}

boolean RobotHostInterface::getArrival()
{
	return isArrived;
}
