#include <Servo.h>
#include <WiFi.h>
#include <EEPROM.h>
#include "Watchdog.h"
#include "RobotCtrl.h"
#include "RobotHostInterface.h"

#define ROBOT_LEFT_SERVOPIN 5
#define ROBOT_RIGHT_SERVOPIN 6
#define ROBOT_SERVER_PORT 501

#define EEPROM_IDX_WATCHDOG 0

RobotCtrl robotCtrl;
RobotHostInterface robotHostIf;
WiFiServer robotServer(ROBOT_SERVER_PORT);

struct HostCommand command;
struct HostCommand prevCommand;

int loopCount = 0;

int robotStatus = 0;

int watchdogStauts = 0;

void cbForWdt()
{
	Serial.println("Watchdog!!");
	EEPROM.write(EEPROM_IDX_WATCHDOG, 1);
}

void setup()
{
	Serial.begin(9600);

	watchdogStauts = EEPROM.read(EEPROM_IDX_WATCHDOG);
	EEPROM.write(EEPROM_IDX_WATCHDOG, 0);
	Serial.print("### Watchdog Status: ");
	Serial.println(watchdogStauts);

	Watchdog::init(cbForWdt, 8);
	Watchdog::resetFlag = true;

	robotCtrl.init(ROBOT_LEFT_SERVOPIN, ROBOT_RIGHT_SERVOPIN);
	robotHostIf.connect(&robotServer);

	Watchdog::configure();

	command.cmd = 0xF;
	command.arg = 0xF;
}

void loop()
{
#if 1
	byte rawCmd = 0xFF;
	WiFiClient client = robotServer.available();

	if (client) {
		robotCtrl.stop();

		prevCommand = command;
		command.cmd = CMD_NONE;

		Serial.print("Robot Client connected...");

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
			} else if (command.cmd == CMD_WATCHDOG && watchdogStauts == 1) {
				rawCmd = CMD_WATCHDOG << 3;
				watchdogStauts = 0;
			} else {
				rawCmd = 0xFF;
			}
		}

		/* send acknowledgement */
		robotServer.write(rawCmd);

		delay(100);

		client.stop();

		Serial.print("Ping: ");
		Serial.println(loopCount);
		loopCount = 0;

		Serial.println("Client Disconnected.\n");
	}
#else
	robotHostIf.rcvCommand(&command);
#endif

	wdt_reset();

	if (command.cmd == CMD_PING)
		command = prevCommand;

	switch (command.cmd) {
		case CMD_GO_FORWWRD:
			robotCtrl.goForward();
			command.arg--;
			delay(10);
			break;
		case CMD_GO_BACKWARD:
			robotCtrl.goBackward();
			command.arg--;
			delay(10);
			break;
		case CMD_TURN_LEFT:
			robotCtrl.turnLeft();
			command.arg--;
			delay(10);
			break;
		case CMD_TURN_RIGHT:
			robotCtrl.turnRight();
			command.arg--;
			delay(10);
			break;
		case CMD_GO_NEXT_STATION:
			robotStatus = robotCtrl.goNextStation();
			loopCount += 1000;
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

	if ((command.cmd <= CMD_TURN_RIGHT && command.arg == 0)
		|| robotStatus != 0) {
		// Serial.println("Done: stop");
		command.cmd = CMD_STOP;
	}

	if (loopCount > 20000) {
		Serial.println("WARNING: WMS might shutdown or network failure happens");
		robotCtrl.stop();
		command.cmd = CMD_STOP;
		loopCount = 0;
	}

	// if ((loopCount % 1000) == 0)
	// 	Serial.println(loopCount);

	loopCount++;
}
