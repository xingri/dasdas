#include <Servo.h>
#include <WiFi.h>
#include "RobotCtrl.h"
#include "RobotHostInterface.h"

RobotCtrl robotCtrl;
RobotHostInterface robotHostIf(500, 501);

byte command = 0xF;
byte argument = 0xF;

void setup()
{
	Serial.begin(9600);

	robotCtrl.init(5, 6);
	robotHostIf.connect();
}

void loop()
{
	byte rawCmd = robotHostIf.rcvCommand();

	if (rawCmd != 0xFF) {
		command = (rawCmd >> 3 ) & 0x07;
		argument = rawCmd & 0x07;

		Serial.print("Cmd: ");
		Serial.print(command);
		Serial.print(", arg: ");
		Serial.println(argument);
	}

	switch (command) {
		case CMD_GO_FORWWRD:
			robotCtrl.goForward();
			argument--;
			break;
		case CMD_GO_BACKWARD:
			robotCtrl.goBackward();
			argument--;
			break;
		case CMD_TURN_LEFT:
			robotCtrl.turnLeft();
			argument--;
			break;
		case CMD_TURN_RIGHT:
			robotCtrl.turnRight();
			argument--;
			break;
		case CMD_GO_NEXT_STATION:
			robotCtrl.goNextStation();
			break;
		case CMD_NEAR_STATION:
			robotCtrl.nearStation();
			command = CMD_NONE;
			break;
		case CMD_STOP:
			robotCtrl.stop();
			command = CMD_NONE;
			break;
		case CMD_NONE:
			break;
		default:
			break;
	}

	if (command <= CMD_TURN_RIGHT && argument == 0)
		command = CMD_STOP;

	delay(10);
}
