#include "Arduino.h"
#include <WiFi.h>
#include "RobotCtrl.h"

#ifndef RobotHostInterface_h
#define RobotHostInterface_h

#define CMD_GO_FORWWRD	0
#define CMD_GO_BACKWARD	1
#define CMD_TURN_LEFT	2
#define CMD_TURN_RIGHT	3
#define CMD_GO_NEXT_STATION 4
#define CMD_NEAR_STATION 5
#define CMD_STOP	6
#define CMD_PING	7
#define CMD_ARRIVAL	8
#define CMD_WATCHDOG	9
#define CMD_NONE	0xF

#define SSID "CMU"

struct HostCommand {
	byte cmd;
	byte arg;
};

class RobotHostInterface {
	private:
		int robotServerPort;
		WiFiServer *robotServer;

		IPAddress ip;
		IPAddress subnet;
		long rssi;
		byte mac[6];

		boolean isArrived;

	public:
		void connect(WiFiServer *_robotServer);
		void printConnectionStatus();

		void rcvCommand(struct HostCommand *command);
		void setArrival(boolean newStatus);
		boolean getArrival();
};

#endif
