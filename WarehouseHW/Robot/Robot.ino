#include <Servo.h>
#include <WiFi.h>
#include "RobotCtrl.h"
#include "RobotHostInterface.h"

#define ROBOT_LEFT_SERVOPIN 5
#define ROBOT_RIGHT_SERVOPIN 6
#define ROBOT_SERVER_PORT 501

RobotCtrl robotCtrl;
RobotHostInterface robotHostIf;
WiFiServer robotServer(ROBOT_SERVER_PORT);

struct HostCommand command;

int loopCount = 0;
int ledStatus = 0;

void setup()
{
	Serial.begin(9600);

	robotCtrl.init(ROBOT_LEFT_SERVOPIN, ROBOT_RIGHT_SERVOPIN);
	robotHostIf.connect(&robotServer);

	command.cmd = 0xF;
	command.arg = 0xF;
}

void loop()
{
#if 1
	byte rawCmd = 0xFF;
	WiFiClient client = robotServer.available();

	if (client) {
		Serial.print("Robot Client connected...");
		Serial.println("Waiting for a command......" );

		while (client.available() == 0 ) {
			delay( 100 );
		}

		rawCmd = (byte)client.read();
		Serial.print("Raw Command: ");
		Serial.print(rawCmd);

		command.cmd = rawCmd >> 3;
		command.arg = rawCmd & 0x07;

		Serial.print(", Cmd: ");
		Serial.print(command.cmd);
		Serial.print(", arg: ");
		Serial.println(command.arg);

		if (command.cmd == CMD_ARRIVAL || command.cmd == CMD_WATCHDOG) {
			if (command.cmd == CMD_ARRIVAL && robotHostIf.getArrival()) {
				rawCmd = CMD_ARRIVAL << 3;
				robotHostIf.setArrival(false);
			} else if (command.cmd == CMD_WATCHDOG) {
				rawCmd = 0xFF;
			} else {
				rawCmd = 0xFF;
			}
		}

		/* send acknowledgement */
		robotServer.write(rawCmd);

		delay(100);

		client.stop();
		Serial.println("Client Disconnected.\n");
	}
#else
	robotHostIf.rcvCommand(&command);
#endif

	switch (command.cmd) {
		case CMD_GO_FORWWRD:
			robotCtrl.goForward();
			command.arg--;
			break;
		case CMD_GO_BACKWARD:
			robotCtrl.goBackward();
			command.arg--;
			break;
		case CMD_TURN_LEFT:
			robotCtrl.turnLeft();
			command.arg--;
			break;
		case CMD_TURN_RIGHT:
			robotCtrl.turnRight();
			command.arg--;
			break;
		case CMD_GO_NEXT_STATION:
			robotCtrl.goNextStation();
			break;
		case CMD_NEAR_STATION:
			robotCtrl.nearStation();
			robotHostIf.setArrival(true);
			command.cmd = CMD_NONE;
			break;
		case CMD_STOP:
			robotCtrl.stop();
			command.cmd = CMD_NONE;
			break;
		case CMD_NONE:
			break;
		default:
			break;
	}

	if (command.cmd <= CMD_TURN_RIGHT && command.arg == 0)
		command.cmd = CMD_STOP;

	loopCount++;

	if ((loopCount % 3000) == 0) {
		  digitalWrite(13, ledStatus);
		  ledStatus = !ledStatus;
		  Serial.print(".");
	}
	// delay(10); // XXX:
}
